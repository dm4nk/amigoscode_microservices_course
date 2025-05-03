package com.dm4nk.clients.book;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

@FeignClient(
        name = "book",
        url = "${clients.book.url}",
        path = "${clients.book.path}"
)
public interface BookClient {
    @GetMapping
    ResponseEntity<List<BookResponse>> findBooks();

    @GetMapping("/{id}")
    ResponseEntity<List<BookResponse>> findLinkedBooks(@PathVariable("id") String id);

    @PostMapping
    ResponseEntity<BookResponse> createBook(@RequestBody BookRequest bookRequest);

    @PutMapping("/link")
    ResponseEntity<BookResponse> linkBook(@RequestBody LinkBookRequest linkRequest);

    @PutMapping("/unlink")
    ResponseEntity<BookResponse> unlinkBook(@RequestBody UnlinkBookRequest linkRequest);
}
