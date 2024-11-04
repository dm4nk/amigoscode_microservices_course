package com.dm4nk.customer.service;

import com.dm4nk.aop.logger.Level;
import com.dm4nk.aop.logger.annotations.Loggable;
import com.dm4nk.clients.customer.CustomerCreationRequest;
import com.dm4nk.clients.customer.CustomerResponse;
import com.dm4nk.customer.db.repository.CustomerRepository;
import com.dm4nk.customer.mapper.CustomerMapper;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

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

    public ResponseEntity<CustomerResponse> create(CustomerCreationRequest request) {
        return ResponseEntity.ok(
                customerMapper.toCustomerResponse(customerRepository.save(customerMapper.toCustomer(request))));
    }
}
