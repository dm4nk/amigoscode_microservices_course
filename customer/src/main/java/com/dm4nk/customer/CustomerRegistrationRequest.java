package com.dm4nk.customer;

public record CustomerRegistrationRequest(
        String firstName,
        String lastName,
        String email) {
}
