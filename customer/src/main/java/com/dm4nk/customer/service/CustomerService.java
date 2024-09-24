package com.dm4nk.customer.service;

import com.dm4nk.aop.logger.Level;
import com.dm4nk.aop.logger.annotations.Loggable;
import com.dm4nk.clients.customer.AddBookRequest;
import com.dm4nk.clients.customer.CustomerCreationRequest;
import com.dm4nk.clients.customer.CustomerResponse;
import com.dm4nk.customer.db.model.Book;
import com.dm4nk.customer.db.model.Customer;
import com.dm4nk.customer.db.repository.BookRepository;
import com.dm4nk.customer.db.repository.CustomerRepository;
import com.dm4nk.customer.mapper.CustomerMapper;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.support.TransactionTemplate;

import java.util.List;

@Service
@Loggable(level = Level.INFO)
@AllArgsConstructor
public class CustomerService {
    private final CustomerRepository customerRepository;
    private final CustomerMapper customerMapper;
    private final BookRepository bookRepository;
    private final TransactionTemplate transactionTemplate;

    public ResponseEntity<List<CustomerResponse>> findAll() {
        return ResponseEntity.ok(
                customerMapper.toCustomerResponse(customerRepository.findAllCustomers()));
    }

    public ResponseEntity<CustomerResponse> create(CustomerCreationRequest request) {
        return ResponseEntity.ok(
                customerMapper.toCustomerResponse(customerRepository.save(customerMapper.toCustomer(request))));
    }

    public ResponseEntity<CustomerResponse> add(AddBookRequest request) {
        Customer customer = customerRepository.findById(request.getCustomerId()).orElseThrow();

        Book book = bookRepository.findById(request.getBookId()).orElseThrow();

        Customer savedCustomer = transactionTemplate.execute(t -> {
            book.setAvailable(false);
            customer.addBook(book);
            return customerRepository.save(customer);
        });

        return ResponseEntity.ok(customerMapper.toCustomerResponse(savedCustomer));
    }
}
