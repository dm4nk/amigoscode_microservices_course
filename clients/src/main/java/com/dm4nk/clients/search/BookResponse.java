package com.dm4nk.clients.search;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class BookResponse {
    private String name;
    private String author;
}
