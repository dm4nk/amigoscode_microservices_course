package com.dm4nk.clients.book;

import lombok.Builder;
import lombok.Data;

import java.time.Instant;
import java.util.UUID;

@Data
@Builder
public class BookResponse {
    private UUID id;
    private String title;
    private String author;
    private String customerId;
    private Instant createdDate;
    private Instant lastModifiedDate;
}
