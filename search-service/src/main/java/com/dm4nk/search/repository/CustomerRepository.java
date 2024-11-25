package com.dm4nk.search.repository;

import com.dm4nk.search.domain.Customer;
import com.google.common.collect.ImmutableList;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import java.util.List;
import java.util.Set;

public interface CustomerRepository extends ElasticsearchRepository<Customer, String> {

    default List<Customer> findAllByIdIn(Set<String> ids) {
        return ImmutableList.copyOf(this.findAllById(ids));
    }
}
