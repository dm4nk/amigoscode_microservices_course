package com.dm4nk.search.dto;

import com.dm4nk.search.domain.Book;
import lombok.Data;

import java.util.List;

@Data
public class CustomerDTO {
    private String id;
    private String firstName;
    private String lastName;
    private String email;
    private List<Book> books;
}
