package com.dm4nk.search.domain;

import lombok.Builder;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.util.List;

@Document(indexName = "searchservice-customer", versionType = Document.VersionType.EXTERNAL)
@Data
@Builder
public class Customer {

    @Id
    @Field(type = FieldType.Keyword)
    private String id;

    @Field(type = FieldType.Keyword)
    private String firstName;

    @Field(type = FieldType.Keyword)
    private String lastName;

    @Field(type = FieldType.Keyword)
    private String email;

    @Field(type = FieldType.Nested, includeInParent = true)
    private List<Book> books;
}
