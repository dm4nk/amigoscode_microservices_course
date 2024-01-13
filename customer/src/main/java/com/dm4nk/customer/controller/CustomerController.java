package com.dm4nk.customer.controller;

import com.dm4nk.clients.customer.CustomerMutationRequest;
import com.dm4nk.clients.customer.CustomerResponse;
import com.dm4nk.clients.note.NoteRequest;
import com.dm4nk.clients.note.NoteResponse;
import com.dm4nk.customer.service.CustomerService;
import com.dm4nk.customer.service.NoteService;
import lombok.AllArgsConstructor;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.MutationMapping;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.stereotype.Controller;

import java.util.List;
import java.util.UUID;


@Controller
@AllArgsConstructor
@SuppressWarnings("unused")
public class CustomerController {
    private final CustomerService customerService;
    private final NoteService noteService;

    @QueryMapping
    public CustomerResponse getCustomerById(@Argument UUID id) {
        return customerService.getCustomer(id);
    }

    @QueryMapping
    public List<CustomerResponse> getCustomers() {
        return customerService.getCustomers();
    }


    @QueryMapping
    public NoteResponse getNote(UUID id) {
        return noteService.getNote(id);
    }

    @MutationMapping
    public CustomerResponse mutateCustomer(@Argument CustomerMutationRequest customerInput) {
        return customerService.mutateCustomer(customerInput);
    }

    @MutationMapping
    public CustomerResponse registerCustomer(@Argument String firstName, @Argument String lastName, @Argument String email) {
        return customerService.registerCustomer(firstName, lastName, email);
    }

    @MutationMapping
    public NoteResponse mutateNote(NoteRequest noteInput) {
        return noteService.updateNote(noteInput);
    }
}
