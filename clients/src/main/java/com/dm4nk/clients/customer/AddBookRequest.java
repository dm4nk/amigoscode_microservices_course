package com.dm4nk.clients.customer;

import lombok.Builder;
import lombok.Value;

import java.util.UUID;

@Value
@Builder
public class AddBookRequest {
    UUID bookId;
    UUID customerId;
}
