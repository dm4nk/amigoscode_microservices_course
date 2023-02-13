package com.dm4nk.customer;

import org.springframework.stereotype.Service;

@Service
public record CustomerService(

) {
    public void registerCustomer(CustomerRegistrationRequest customerRegistrationRequest) {
        Customer customer = Customer.builder()
                .firstName(customerRegistrationRequest.firstName())
                .lastName(customerRegistrationRequest.lastName())
                .email(customerRegistrationRequest.email())
                .build();
        //todo dmpr add validation
    }
}
