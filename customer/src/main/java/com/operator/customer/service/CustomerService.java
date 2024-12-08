package com.operator.customer.service;

import com.operator.clients.customer.CustomerRequest;
import com.operator.clients.customer.CustomerResponse;
import com.operator.customer.db.model.Customer;
import com.operator.customer.db.repository.CustomerRepository;
import com.operator.customer.mapper.CustomerMapper;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@AllArgsConstructor
public class CustomerService {
    private final CustomerRepository customerRepository;
    private final CustomerMapper customerMapper;

    public ResponseEntity<List<CustomerResponse>> findAll() {
        return ResponseEntity.ok(
                customerMapper.toCustomerResponse(customerRepository.findAll()));
    }

    public ResponseEntity<List<CustomerResponse>> findAllByName(String name) {
        return ResponseEntity.ok(
                customerMapper.toCustomerResponse(customerRepository.findByLastNameIgnoreCaseOrFirstNameIgnoreCase(name, name)));
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
