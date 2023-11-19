package com.dm4nk.customer.util;

import com.dm4nk.clients.customer.CustomerResponse;
import com.dm4nk.customer.db.model.Customer;

public final class Mapper {
    public static CustomerResponse mapCustomerToCustomerResponse(Customer customer, boolean isBanned) {
        return CustomerResponse.builder()
                .id(customer.getId())
                .firstName(customer.getFirstName())
                .lastName(customer.getEmail())
                .email(customer.getEmail())
                .createdAt(customer.getCreatedAt())
                .isBanned(isBanned)
                .build();
    }

    public static CustomerResponse mapCustomerToCustomerResponse(Customer customer) {
        return mapCustomerToCustomerResponse(customer, false);
    }
}
