package com.dm4nk.customer;

import com.dm4nk.clients.fraud.FraudCheckResponse;
import com.dm4nk.clients.fraud.FraudClient;
import com.dm4nk.clients.notification.NotificationClient;
import com.dm4nk.clients.notification.NotificationRequest;
import org.springframework.stereotype.Service;

@Service
public record CustomerService(
        CustomerRepository customerRepository,
        FraudClient fraudClient,
        NotificationClient notificationClient
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

        FraudCheckResponse fraudCheckResponse = fraudClient.isFraudster(customer.getId());

        if (fraudCheckResponse.isFraudster()) {
            throw new IllegalStateException("fraudster");
        }

        notificationClient.sendNotification(
                new NotificationRequest(
                        customer.getId(),
                        customer.getEmail(),
                        String.format("Welcome, %s", customer.getFirstName())
                )
        );
    }
}
