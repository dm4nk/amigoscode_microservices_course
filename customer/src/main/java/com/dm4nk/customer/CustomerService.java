package com.dm4nk.customer;

import com.dm4nk.amqp.RabbitMQMessageProducer;
import com.dm4nk.clients.fraud.FraudCheckResponse;
import com.dm4nk.clients.fraud.FraudClient;
import com.dm4nk.clients.notification.NotificationClient;
import com.dm4nk.clients.notification.NotificationRequest;
import org.springframework.stereotype.Service;

@Service
public record CustomerService(
        CustomerRepository customerRepository,
        FraudClient fraudClient,
        NotificationClient notificationClient,
        RabbitMQMessageProducer rabbitMQMessageProducer
) {
    public void registerCustomer(CustomerRegistrationRequest customerRegistrationRequest) {
        Customer customer = Customer.builder()
                .firstName(customerRegistrationRequest.firstName())
                .lastName(customerRegistrationRequest.lastName())
                .email(customerRegistrationRequest.email())
                .build();

        //todo add validation
        customerRepository.saveAndFlush(customer);

        FraudCheckResponse fraudCheckResponse = fraudClient.isFraudster(customer.getId());

        if (fraudCheckResponse.isFraudster()) {
            throw new IllegalStateException("fraudster");
        }

        NotificationRequest notificationRequest = new NotificationRequest(
                customer.getId(),
                customer.getEmail(),
                String.format("Welcome, %s", customer.getFirstName())
        );

        rabbitMQMessageProducer.publish(
                "internal.exchange",
                "internal.notification.routing-key",
                notificationRequest
        );
    }
}
