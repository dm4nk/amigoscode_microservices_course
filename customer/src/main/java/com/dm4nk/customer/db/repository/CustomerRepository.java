package com.dm4nk.customer.db.repository;

import com.dm4nk.customer.db.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface CustomerRepository extends JpaRepository<Customer, UUID> {
}
