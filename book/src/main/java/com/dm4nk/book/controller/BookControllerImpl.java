package com.dm4nk.book.controller;

import com.dm4nk.book.service.BookService;
import com.dm4nk.clients.book.BookClient;
import com.dm4nk.clients.book.BookRequest;
import com.dm4nk.clients.book.BookResponse;
import com.dm4nk.clients.book.LinkBookRequest;
import com.dm4nk.clients.book.UnlinkBookRequest;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/book")
public class BookControllerImpl implements BookClient {
    private BookService bookService;

    @GetMapping
    @Override
    public ResponseEntity<List<BookResponse>> findBooks() {
        return bookService.findAllAvailableBooks();
    }

    @GetMapping("/{id}")
    @Override
    public ResponseEntity<List<BookResponse>> findLinkedBooks(@PathVariable("id") String id) {
        return bookService.findLinkedBooks(id);
    }

    @PostMapping
    @Override
    public ResponseEntity<BookResponse> createBook(@RequestBody BookRequest bookRequest) {
        return bookService.createBook(bookRequest);
    }

    @PutMapping("/link")
    @Override
    public ResponseEntity<BookResponse> linkBook(@RequestBody LinkBookRequest linkRequest) {
        return bookService.linkBook(linkRequest);
    }

    @PutMapping("/unlink")
    @Override
    public ResponseEntity<BookResponse> unlinkBook(@RequestBody UnlinkBookRequest linkRequest) {
        return bookService.unlinkBook(linkRequest);
    }
}
