package com.dm4nk.search.service;

import co.elastic.clients.elasticsearch.ElasticsearchClient;
import com.dm4nk.search.domain.Customer;
import com.dm4nk.search.exceptions.AccountCreationException;
import com.dm4nk.search.mapper.RecordMapper;
import com.dm4nk.search.repository.CustomerRepository;
import com.google.common.collect.Sets;
import lombok.AllArgsConstructor;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.collections4.ListUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.data.elasticsearch.BulkFailureException;
import org.springframework.data.elasticsearch.VersionConflictException;
import org.springframework.data.elasticsearch.core.query.SeqNoPrimaryTerm;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class StreamService {
    private final CustomerRepository customerRepository;
    private final RecordMapper recordMapper;
    private final ElasticsearchClient elasticsearchClient;

    @Retryable(maxAttempts = 5, backoff = @Backoff(delay = 100), retryFor = {BulkFailureException.class, VersionConflictException.class, AccountCreationException.class})
    public void streamCustomer(List<com.dm4nk.search.avro.Customer> messages) {
        final var customerIdsFromMessages = getCustomerIdsFromMessages(messages);

        final var existingCustomers = findCustomersForUpdate(customerIdsFromMessages);
        final var existingCustomerIds = extractExistingCustomerIds(existingCustomers);
        final var absentCustomerIds = Sets.difference(customerIdsFromMessages, existingCustomerIds);
        final var customersForUpdate = getCustomersForUpdate(absentCustomerIds, existingCustomers);

        final var updatedCustomers = customersForUpdate.stream()
                .map(customer -> this.updateCustomer(customer, messages))
                .toList();

        customerRepository.saveAll(updatedCustomers);
    }

    private List<Customer> getCustomersForUpdate(Set<String> absentCustomerIds, List<Customer> existingCustomers) {
        if (CollectionUtils.isEmpty(absentCustomerIds)) {
            return existingCustomers;
        } else {
            final var createdCustomers = absentCustomerIds.stream().map(this::createCustomer).toList();
            return ListUtils.union(existingCustomers, createdCustomers);
        }
    }

    private static Set<String> extractExistingCustomerIds(List<Customer> customersForUpdate) {
        return customersForUpdate.stream()
                .map(Customer::getId)
                .collect(Collectors.toUnmodifiableSet());
    }

    private List<Customer> findCustomersForUpdate(Set<String> customerIdsFromMessages) {
        return customerRepository.findAllByIdIn(customerIdsFromMessages);
    }

    private Set<String> getCustomerIdsFromMessages(List<com.dm4nk.search.avro.Customer> messages) {
        return messages.stream()
                .map(this::getId)
                .collect(Collectors.toUnmodifiableSet());
    }

    private Customer updateCustomer(Customer customer, List<com.dm4nk.search.avro.Customer> messages) {
        final var relevantMessages = messages.stream()
                .filter(message -> StringUtils.equals(getId(message), customer.getId()))
                .toList();

        relevantMessages.forEach(message -> recordMapper.updateCustomer(customer, message.getAfter()));

        return customer;
    }

    private String getId(com.dm4nk.search.avro.Customer message) {
        return message.getAfter().getId().toString();
    }

    private Customer createCustomer(String id) {
        try {
            final var createResponse = elasticsearchClient.create(b -> b
                    .id(id)
                    .document(Customer.builder().id(id).build())
                    .index("searchservice-customer")
            );

            final var seqNoPrimaryTerm = new SeqNoPrimaryTerm(createResponse.seqNo(), createResponse.primaryTerm());
            return Customer.builder().id(id)
                    .seqNoPrimaryTerm(seqNoPrimaryTerm)
                    .build();
        } catch (IOException e) {
            if (e.getMessage().contains("version_conflict_engine_exception")) {
                throw new AccountCreationException();
            }
            throw new RuntimeException(e);
        }
    }
}
