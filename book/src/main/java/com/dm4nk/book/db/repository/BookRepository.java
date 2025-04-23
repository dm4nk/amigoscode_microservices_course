package com.dm4nk.book.db.repository;

import com.dm4nk.book.db.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface BookRepository extends JpaRepository<Book, UUID> {
    List<Book> findByCustomerIdIsNull();

    List<Book> findByCustomerId(UUID customerId);
}
