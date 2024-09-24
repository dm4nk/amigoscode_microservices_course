package com.dm4nk.clients.customer;

import lombok.Builder;
import lombok.Data;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

@Data
@Builder
public class CustomerResponse {
    private UUID id;
    private String firstName;
    private String lastName;
    private String email;
    private Instant createdDate;
    private Instant lastModifiedDate;
    private List<BookResponse> books;
}
