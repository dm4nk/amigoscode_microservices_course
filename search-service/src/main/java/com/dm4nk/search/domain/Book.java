package com.dm4nk.search.domain;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

@Data
@Builder
public class Book {
    @Field(type = FieldType.Keyword)
    private String name;

    @Field(type = FieldType.Keyword)
    private String author;

    @Field(type = FieldType.Keyword)
    private Boolean available;
}
