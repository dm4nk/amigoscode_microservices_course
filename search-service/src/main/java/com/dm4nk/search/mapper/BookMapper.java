package com.dm4nk.search.mapper;

import book.public$.book.Value;
import com.dm4nk.search.config.MapStructConfig;
import com.dm4nk.search.domain.Book;
import org.mapstruct.Mapper;

@Mapper(config = MapStructConfig.class)
public interface BookMapper {
    Book toValue(Value from);

    default String map(CharSequence value) {
        return value.toString();
    }
}
