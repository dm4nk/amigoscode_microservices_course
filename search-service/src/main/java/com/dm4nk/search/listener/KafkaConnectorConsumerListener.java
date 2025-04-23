package com.dm4nk.search.listener;

import com.dm4nk.search.service.StreamService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;

@Component
@RequiredArgsConstructor
@Slf4j
public class KafkaConnectorConsumerListener {
    private final StreamService streamService;
    private final KafkaTemplate<String, com.dm4nk.search.avro.Customer> customerKafkaTemplate;
    private final KafkaTemplate<String, com.dm4nk.search.avro.Book> bookKafkaTemplate;

    @KafkaListener(topics = "customer.public.customer", batch = "true")
    public void consumeCustomerMessage(List<com.dm4nk.search.avro.Customer> messages) {
        log.info("Consumed message: {}", messages);
        try {
            streamService.streamCustomer(messages);
        } catch (Exception e) {
            for (com.dm4nk.search.avro.Customer value : messages) {
                try {
                    streamService.streamCustomer(Collections.singletonList(value));
                } catch (Exception e1) {
                    log.error(e1.getMessage());
                    customerKafkaTemplate.send("customer.public.customer.dlt", value);
                }
            }
        }
    }

    @KafkaListener(topics = "book.public.book")
    public void consumeBookMessage(com.dm4nk.search.avro.Book message) {
        log.info("Consumed message: {}", message);
        try {
            streamService.streamBook(message);
        } catch (Exception e1) {
            log.error(e1.getMessage());
            bookKafkaTemplate.send("book.public.book.dlt", message);
        }
    }
}
