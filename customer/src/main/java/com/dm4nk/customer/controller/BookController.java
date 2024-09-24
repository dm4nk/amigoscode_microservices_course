package com.dm4nk.customer.controller;

import com.dm4nk.clients.customer.BookCreationRequest;
import com.dm4nk.clients.customer.BookResponse;
import com.dm4nk.customer.service.BookService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/book")
public class BookController {
    private final BookService bookService;

    @GetMapping
    public ResponseEntity<List<BookResponse>> findAvailableBook() {
        return bookService.findAvailableBooks();
    }

    @PostMapping
    public ResponseEntity<BookResponse> createBook(BookCreationRequest request) {
        return bookService.create(request);
    }
}
