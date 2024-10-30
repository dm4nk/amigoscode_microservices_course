package com.dm4nk.search.repository;

import com.dm4nk.search.domain.Customer;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

public interface CustomerRepository extends ElasticsearchRepository<Customer, String> {
}
