package com.dm4nk.customer.mapper;

import com.dm4nk.clients.customer.BookCreationRequest;
import com.dm4nk.clients.customer.BookResponse;
import com.dm4nk.customer.db.model.Book;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface BookMapper {
    List<BookResponse> toBookResponse(List<Book> from);

    BookResponse toBookResponse(Book from);

    Book toBook(BookCreationRequest from);
}
