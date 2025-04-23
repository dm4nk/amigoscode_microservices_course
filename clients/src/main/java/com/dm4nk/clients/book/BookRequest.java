package com.dm4nk.clients.book;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class BookRequest {
    String title;
    String author;
}
