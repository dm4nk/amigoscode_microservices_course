package com.dm4nk.customer.db.repository;

import com.dm4nk.customer.db.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.UUID;

public interface CustomerRepository extends JpaRepository<Customer, UUID> {

    @Query(value = "select customer from Customer customer left join fetch customer.books")
    List<Customer> findAllCustomers();
}
