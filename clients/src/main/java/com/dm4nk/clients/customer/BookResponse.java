package com.dm4nk.clients.customer;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class BookResponse {
    private String id;
    private String name;
    private String author;
    private Boolean available;
}
