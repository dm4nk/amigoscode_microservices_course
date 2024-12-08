package com.operator.customer.db.repository;

import com.operator.customer.db.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface CustomerRepository extends JpaRepository<Customer, UUID> {
    List<Customer> findByLastNameIgnoreCaseOrFirstNameIgnoreCase(String lastName, String firstName);
}
