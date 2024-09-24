package com.dm4nk.customer.controller;

import com.dm4nk.clients.customer.AddBookRequest;
import com.dm4nk.clients.customer.CustomerCreationRequest;
import com.dm4nk.clients.customer.CustomerResponse;
import com.dm4nk.customer.service.CustomerService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/customer")
public class CustomerController {
    private final CustomerService customerService;

    @GetMapping
    public ResponseEntity<List<CustomerResponse>> findCustomers() {
        return customerService.findAll();
    }

    @PostMapping
    public ResponseEntity<CustomerResponse> createCustomer(@RequestBody CustomerCreationRequest request) {
        return customerService.create(request);
    }

    @PutMapping("/add")
    public ResponseEntity<CustomerResponse> addBook(@RequestBody AddBookRequest request) {
        return customerService.add(request);
    }
}
