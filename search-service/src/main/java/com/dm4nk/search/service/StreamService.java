package com.dm4nk.search.service;

import com.dm4nk.search.domain.Customer;
import com.dm4nk.search.mapper.RecordMapper;
import com.dm4nk.search.repository.CustomerRepository;
import customer.public$.customer.Envelope;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class StreamService {
    private final CustomerRepository customerRepository;
    private final RecordMapper recordMapper;

    public void stream(Envelope message) {
        String id = getId(message);
        Customer customer = customerRepository.findById(id).orElse(Customer.builder().build());

        recordMapper.updateCustomer(customer, message.getAfter());

        customerRepository.save(customer);
    }

    private String getId(Envelope message) {
        return message.getAfter().getId().toString();
    }
}
