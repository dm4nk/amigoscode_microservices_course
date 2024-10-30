package com.dm4nk.search.service;

import com.dm4nk.search.repository.CustomerRepository;
import lombok.AllArgsConstructor;
import org.apache.avro.generic.GenericRecord;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class StreamService {
    private final CustomerRepository customerRepository;

    public void stream(GenericRecord message) {

    }

}
