package com.dm4nk.book.mapper;

import com.dm4nk.book.db.model.Book;
import com.dm4nk.clients.book.BookRequest;
import com.dm4nk.clients.book.BookResponse;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface BookMapper {
    List<BookResponse> toBookResponse(List<Book> from);

    BookResponse toBookResponse(Book from);

    Book toBook(BookRequest from);
}
