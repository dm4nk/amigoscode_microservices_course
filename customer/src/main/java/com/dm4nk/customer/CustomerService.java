package com.dm4nk.customer;

import com.dm4nk.amqp.RabbitMQMessageProducer;
import com.dm4nk.aop.logger.Level;
import com.dm4nk.aop.logger.annotations.Loggable;
import com.dm4nk.clients.ban.BanCheckResponse;
import com.dm4nk.clients.ban.BanClient;
import com.dm4nk.clients.notification.NotificationClient;
import com.dm4nk.clients.notification.NotificationRequest;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@Loggable(level = Level.INFO)
@AllArgsConstructor
public class CustomerService {
    private final CustomerRepository customerRepository;
    private final BanClient banClient;
    private final NotificationClient notificationClient;
    private final RabbitMQMessageProducer rabbitMQMessageProducer;

    public void registerCustomer(CustomerRegistrationRequest customerRegistrationRequest) {
        Customer customer = Customer.builder()
                .firstName(customerRegistrationRequest.firstName())
                .lastName(customerRegistrationRequest.lastName())
                .email(customerRegistrationRequest.email())
                .build();

        //todo add validation
        customerRepository.saveAndFlush(customer);

        BanCheckResponse banCheckResponse = banClient.isBanned(customer.getId());

        if (banCheckResponse.isBanned()) {
            throw new IllegalStateException("banned");
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
