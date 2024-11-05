package com.dm4nk.clients.customer;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class CustomerRequest {
    String firstName;
    String lastName;
    String email;
}
