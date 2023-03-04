package com.dm4nk.customer;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public record CustomerService(
        CustomerRepository customerRepository,
        RestTemplate restTemplate
) {
    public void registerCustomer(CustomerRegistrationRequest customerRegistrationRequest) {
        Customer customer = Customer.builder()
                .firstName(customerRegistrationRequest.firstName())
                .lastName(customerRegistrationRequest.lastName())
                .email(customerRegistrationRequest.email())
                .build();
        //todo dmpr add validation
        customerRepository.saveAndFlush(customer);

        //todo dmpr check if fraudster
        FraudCheckResponse fraudCheckResponse = restTemplate.getForObject("http://localhost:8081/api/vi/fraud-check/{customerId}", FraudCheckResponse.class, customer.getId());

        if (fraudCheckResponse.isFraudster()) {
            throw new IllegalStateException("fraudster");
        }

        //todo dmpr send notification
    }
}
