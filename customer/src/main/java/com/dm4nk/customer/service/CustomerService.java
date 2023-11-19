package com.dm4nk.customer.service;

import com.dm4nk.amqp.RabbitMQMessageProducer;
import com.dm4nk.aop.logger.Level;
import com.dm4nk.aop.logger.annotations.Loggable;
import com.dm4nk.clients.customer.CustomerBanRequest;
import com.dm4nk.clients.customer.CustomerMutationRequest;
import com.dm4nk.clients.customer.CustomerResponse;
import com.dm4nk.clients.notification.NotificationRequest;
import com.dm4nk.customer.db.model.Customer;
import com.dm4nk.customer.db.repository.CustomerRepository;
import com.dm4nk.customer.util.Mapper;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;

@Service
@Loggable(level = Level.INFO)
@AllArgsConstructor
public class CustomerService {
    private final CustomerRepository customerRepository;
    private final BanService banService;
    private final RabbitMQMessageProducer rabbitMQMessageProducer;

    public CustomerResponse registerCustomer(String firstName, String lastName, String email) {
        Customer customer = Customer.builder()
                .firstName(firstName)
                .lastName(lastName)
                .email(email)
                .build();

        customerRepository.saveAndFlush(customer);

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

        return Mapper.mapCustomerToCustomerResponse(customer);
    }

    public CustomerResponse mutateCustomer(CustomerMutationRequest customerInput) {
        if (!customerRepository.existsById(customerInput.id())) {
            throw new NoSuchElementException(String.format("No such customer with id: %s", customerInput.id()));
        }

        Customer customer = Customer.builder()
                .id(customerInput.id())
                .firstName(customerInput.firstName())
                .lastName(customerInput.lastName())
                .email(customerInput.email())
                .build();

        customerRepository.saveAndFlush(customer);

        if (customerInput.ban() != null && customerInput.ban()) {
            CustomerBanRequest customerBanRequest = CustomerBanRequest.builder()
                    .id(customerInput.id())
                    .duration(customerInput.banForDays())
                    .validTo(customerInput.banUntil())
                    .build();
            banCustomer(customerBanRequest);
        }

        NotificationRequest notificationRequest = new NotificationRequest(
                customer.getId(),
                customer.getEmail(),
                String.format("Customer %s has been updated", customer.getFirstName())
        );

        rabbitMQMessageProducer.publish(
                "internal.exchange",
                "internal.notification.routing-key",
                notificationRequest
        );

        return Mapper.mapCustomerToCustomerResponse(customer);
    }

    public CustomerResponse getCustomer(UUID customerId) {
        Customer customer = customerRepository.getReferenceById(customerId);
        boolean isBanned = banService.isBanned(customerId);
        return Mapper.mapCustomerToCustomerResponse(customer, isBanned);
    }

    public List<CustomerResponse> getCustomers() {
        return customerRepository.findAll()
                .stream()
                .map(customer -> Mapper.mapCustomerToCustomerResponse(customer, banService.isBanned(customer.getId())))
                .toList();
    }

    public void banCustomer(CustomerBanRequest customerBanRequest) {
        banService.banCustomer(
                customerBanRequest.getId(),
                customerBanRequest.getDuration(),
                customerBanRequest.getValidTo()
        );
    }
}
