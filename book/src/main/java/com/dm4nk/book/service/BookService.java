package com.dm4nk.book.service;

import com.dm4nk.book.db.model.Book;
import com.dm4nk.book.db.repository.BookRepository;
import com.dm4nk.book.mapper.BookMapper;
import com.dm4nk.clients.book.BookRequest;
import com.dm4nk.clients.book.BookResponse;
import com.dm4nk.clients.book.LinkBookRequest;
import com.dm4nk.clients.book.UnlinkBookRequest;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Slf4j
@Service
@AllArgsConstructor
public class BookService {
    private final BookRepository bookRepository;
    private final BookMapper bookMapper;

    public ResponseEntity<List<BookResponse>> findAllAvailableBooks() {
        return ResponseEntity.ok(bookMapper.toBookResponse(bookRepository.findByCustomerIdIsNull()));
    }

    public ResponseEntity<List<BookResponse>> findLinkedBooks(String id) {
        return ResponseEntity.ok(bookMapper.toBookResponse(bookRepository.findByCustomerId(UUID.fromString(id))));
    }

    public ResponseEntity<BookResponse> createBook(BookRequest bookRequest) {
        Book book = bookMapper.toBook(bookRequest);
        Book saved = bookRepository.save(book);
        return ResponseEntity.ok(bookMapper.toBookResponse(saved));
    }

    @Transactional
    public ResponseEntity<BookResponse> linkBook(LinkBookRequest linkRequest) {
        Book book = bookRepository.getReferenceById(linkRequest.getBookId());
        book.setCustomerId(linkRequest.getCustomerId());
        Book saved = bookRepository.save(book);
        return ResponseEntity.ok(bookMapper.toBookResponse(saved));
    }

    @Transactional
    public ResponseEntity<BookResponse> unlinkBook(UnlinkBookRequest linkRequest) {
        Book book = bookRepository.getReferenceById(linkRequest.getBookId());
        book.setCustomerId(null);
        Book saved = bookRepository.save(book);
        return ResponseEntity.ok(bookMapper.toBookResponse(saved));
    }
}
