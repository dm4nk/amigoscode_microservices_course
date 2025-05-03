package com.dm4nk.customer.controller;

import com.dm4nk.clients.book.BookResponse;
import com.dm4nk.clients.book.LinkBookRequest;
import com.dm4nk.clients.book.UnlinkBookRequest;
import com.dm4nk.clients.customer.CustomerRequest;
import com.dm4nk.clients.customer.CustomerResponse;
import com.dm4nk.customer.aop.AuditEvent;
import com.dm4nk.customer.service.CustomerService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/customer")
public class CustomerController {
    private final CustomerService customerService;

    @GetMapping
    @AuditEvent("findCustomers")
    public ResponseEntity<List<CustomerResponse>> findCustomers() {
        return customerService.findAll();
    }

    @GetMapping("/{id}")
    @AuditEvent("findCustomerById")
    public ResponseEntity<List<CustomerResponse>> findCustomerById(@PathVariable("id") UUID id) {
        return customerService.findById(id);
    }

    @PostMapping
    @AuditEvent("createCustomer")
    public ResponseEntity<CustomerResponse> createCustomer(@RequestBody CustomerRequest request) {
        return customerService.create(request);
    }

    @PatchMapping("/{id}")
    @AuditEvent("updateCustomer")
    public ResponseEntity<CustomerResponse> updateCustomer(@RequestBody CustomerRequest request, @PathVariable("id") UUID id) {
        return customerService.update(id, request);
    }

    @PutMapping("/link")
    @AuditEvent("linkBook")
    public ResponseEntity<BookResponse> linkBook(@RequestBody LinkBookRequest linkRequest) {
        return customerService.linkBook(linkRequest);
    }

    @PutMapping("/unlink")
    @AuditEvent("unlinkBook")
    public ResponseEntity<BookResponse> unlinkBook(@RequestBody UnlinkBookRequest linkRequest) {
        return customerService.unlinkBook(linkRequest);
    }
}
