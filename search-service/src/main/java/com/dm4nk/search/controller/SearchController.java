package com.dm4nk.search.controller;

import com.dm4nk.clients.search.CustomerResponse;
import com.dm4nk.clients.search.FullTextSearchRequest;
import com.dm4nk.search.service.SearchService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/api/v1/search")
public class SearchController {
    private final SearchService searchService;

    @PostMapping("/customer/search")
    public ResponseEntity<List<CustomerResponse>> findCustomers(@RequestBody FullTextSearchRequest fullTextSearchRequest) {
        return searchService.findCustomers(fullTextSearchRequest);
    }
}
