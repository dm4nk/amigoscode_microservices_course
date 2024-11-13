package com.dm4nk.clients.search;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class CustomerResponse {
    private String id;
    private String firstName;
    private String lastName;
    private String email;
    private List<BookResponse> books;
}
