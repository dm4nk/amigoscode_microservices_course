package com.dm4nk.customer.service;

import com.dm4nk.aop.logger.Level;
import com.dm4nk.aop.logger.annotations.Loggable;
import com.dm4nk.clients.customer.CustomerRequest;
import com.dm4nk.clients.customer.CustomerResponse;
import com.dm4nk.customer.db.model.Customer;
import com.dm4nk.customer.db.repository.CustomerRepository;
import com.dm4nk.customer.mapper.CustomerMapper;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@Loggable(level = Level.INFO)
@AllArgsConstructor
public class CustomerService {
    private final CustomerRepository customerRepository;
    private final CustomerMapper customerMapper;

    public ResponseEntity<List<CustomerResponse>> findAll() {
        return ResponseEntity.ok(
                customerMapper.toCustomerResponse(customerRepository.findAll()));
    }

    public ResponseEntity<CustomerResponse> create(CustomerRequest request) {
        return ResponseEntity.ok(
                customerMapper.toCustomerResponse(customerRepository.save(customerMapper.toCustomer(request))));
    }

    public ResponseEntity<CustomerResponse> update(UUID id, CustomerRequest request) {
        Customer customer = customerRepository.findById(id).orElseThrow();

        customerMapper.updateCustomer(customer, request);

        return ResponseEntity.ok(customerMapper.toCustomerResponse(customerRepository.save(customer)));
    }
}
