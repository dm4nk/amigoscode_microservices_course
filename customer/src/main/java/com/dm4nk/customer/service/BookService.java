package com.dm4nk.customer.service;

import com.dm4nk.aop.logger.Level;
import com.dm4nk.aop.logger.annotations.Loggable;
import com.dm4nk.clients.customer.BookCreationRequest;
import com.dm4nk.clients.customer.BookResponse;
import com.dm4nk.customer.db.model.Book;
import com.dm4nk.customer.db.repository.BookRepository;
import com.dm4nk.customer.mapper.BookMapper;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Loggable(level = Level.INFO)
@AllArgsConstructor
public class BookService {
    private final BookRepository bookRepository;
    private final BookMapper bookMapper;

    public ResponseEntity<List<BookResponse>> findAvailableBooks() {
        return ResponseEntity.ok(bookMapper.toBookResponse(bookRepository.findBooksByAvailableIsTrue()));
    }

    public ResponseEntity<BookResponse> create(BookCreationRequest request) {
        Book book = bookMapper.toBook(request);
        book.setAvailable(true);
        return ResponseEntity.ok(bookMapper.toBookResponse(bookRepository.save(book)));
    }
}
