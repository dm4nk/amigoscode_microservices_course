package com.dm4nk.search.service;

import com.dm4nk.search.domain.Customer;
import com.dm4nk.search.mapper.RecordMapper;
import com.dm4nk.search.repository.CustomerRepository;
import customer.public$.customer.Envelope;
import lombok.AllArgsConstructor;
import org.springframework.data.elasticsearch.BulkFailureException;
import org.springframework.data.elasticsearch.VersionConflictException;
import org.springframework.data.elasticsearch.client.elc.NativeQueryBuilder;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.mapping.IndexCoordinates;
import org.springframework.data.elasticsearch.core.query.ScriptType;
import org.springframework.data.elasticsearch.core.query.UpdateQuery;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Service
@AllArgsConstructor
public class StreamService {
    private final CustomerRepository customerRepository;
    private final RecordMapper recordMapper;
    private final ElasticsearchOperations operations;

    @Retryable(maxAttempts = 5, backoff = @Backoff(delay = 500), retryFor = {BulkFailureException.class, VersionConflictException.class})
    public void streamCustomer(List<customer.public$.customer.Envelope> messages) {

        final var updatedCustomers = messages.stream()
                .map(this::updateCustomer)
                .toList();

        customerRepository.saveAll(updatedCustomers);
    }

    private Customer updateCustomer(Envelope message) {
        final var id = getId(message);
        Customer customer = customerRepository.findById(id).orElse(Customer.builder().build());

        recordMapper.updateCustomer(customer, message.getAfter());

        return customer;
    }

    private String getId(customer.public$.customer.Envelope message) {
        return message.getAfter().getId();
    }

    @Retryable(maxAttempts = 5, backoff = @Backoff(delay = 500), retryFor = {BulkFailureException.class, VersionConflictException.class})
    public void streamBook(List<book.public$.book.Envelope> messages) {

        final var queries = messages.stream()
                .filter(message -> Objects.nonNull(message.getAfter().getCustomerId()))
                .map(StreamService::createUpdateQuery)
                .toList();

        for (var query : queries) {
            operations.updateByQuery(query, IndexCoordinates.of("searchservice-customer"));
        }
    }

    private static UpdateQuery createUpdateQuery(book.public$.book.Envelope message) {
        Map<String, Object> params = new HashMap<>();
        Map<String, String> book = new HashMap<>();
        book.put("name", message.getAfter().getName());
        book.put("author", message.getAfter().getAuthor());
        params.put("book", book);

        final var query = new NativeQueryBuilder()
                .withQuery(q -> q.term(t -> t.field("id").value(message.getAfter().getCustomerId())))
                .build();

        return UpdateQuery.builder(query)
                .withLang("painless")
                .withScript("add-book")
                .withScriptType(ScriptType.STORED)
                .withParams(params)
                .withIndex("searchservice-customer")
                .build();
    }
}
