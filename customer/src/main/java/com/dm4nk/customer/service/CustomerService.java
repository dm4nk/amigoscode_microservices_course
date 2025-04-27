package com.dm4nk.customer.service;

import com.dm4nk.clients.customer.CustomerRequest;
import com.dm4nk.clients.customer.CustomerResponse;
import com.dm4nk.customer.db.model.Customer;
import com.dm4nk.customer.db.repository.CustomerRepository;
import com.dm4nk.customer.mapper.CustomerMapper;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.UUID;

@Slf4j
@Service
@AllArgsConstructor
public class CustomerService {
    private final CustomerRepository customerRepository;
    private final CustomerMapper customerMapper;

    public ResponseEntity<List<CustomerResponse>> findAll() {
        log.info("Find all customers");
        return ResponseEntity.ok(
                customerMapper.toCustomerResponse(customerRepository.findAll()));
    }

    public ResponseEntity<CustomerResponse> create(CustomerRequest request) {
        log.info("Create customer: {}", request);
        return ResponseEntity.ok(
                customerMapper.toCustomerResponse(customerRepository.save(customerMapper.toCustomer(request))));
    }

    public ResponseEntity<CustomerResponse> update(UUID id, CustomerRequest request) {
        log.info("Update customer: {}", request);
        Customer customer = customerRepository.findById(id).orElseThrow();

        customerMapper.updateCustomer(customer, request);

        return ResponseEntity.ok(customerMapper.toCustomerResponse(customerRepository.save(customer)));
    }

    public ResponseEntity<List<CustomerResponse>> findById(UUID id) {
        log.info("Find customer by id {}", id);
        return ResponseEntity.ok(customerRepository.findById(id)
                .stream()
                .map(Collections::singletonList)
                .map(customerMapper::toCustomerResponse)
                .findAny()
                .orElseThrow());
    }
}
