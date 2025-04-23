package com.dm4nk.search.service;

import com.dm4nk.clients.search.CustomerResponse;
import com.dm4nk.clients.search.FullTextSearchRequest;
import com.dm4nk.search.domain.Customer;
import com.dm4nk.search.mapper.CustomerMapper;
import lombok.AllArgsConstructor;
import org.springframework.data.elasticsearch.client.elc.NativeQuery;
import org.springframework.data.elasticsearch.client.elc.NativeQueryBuilder;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.data.elasticsearch.core.SearchHit;
import org.springframework.data.elasticsearch.core.SearchHitSupport;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.SearchPage;
import org.springframework.data.util.Streamable;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@AllArgsConstructor
public class SearchService {
    private final ElasticsearchOperations esOperations;
    private final CustomerMapper customerMapper;

    public ResponseEntity<List<CustomerResponse>> findCustomers(FullTextSearchRequest fullTextSearchRequest) {
        String text = fullTextSearchRequest.getText();

        NativeQueryBuilder nativeQueryBuilder = new NativeQueryBuilder();
        NativeQuery query = nativeQueryBuilder.withQuery(q -> q
                        .multiMatch(m -> m
                                .fields("id", "firstName", "lastName", "email", "books.name", "books.author")
                                .query(text)
                        )
                )
                .build();

        SearchHits<Customer> hits = esOperations.search(query, Customer.class);
        SearchPage<Customer> searchPage = SearchHitSupport.searchPageFor(hits, null);
        List<CustomerResponse> users = Optional.of(searchPage.getSearchHits()).stream()
                .flatMap(Streamable::stream)
                .filter(Objects::nonNull)
                .map(SearchHit::getContent)
                .map(customerMapper::toResponse)
                .toList();

        return ResponseEntity.ok(users);
    }
}
