package com.dm4nk.search.listener;

import com.dm4nk.search.service.StreamService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
@Slf4j
public class KafkaConnectorConsumerListener {
    private final StreamService streamService;

    @KafkaListener(topics = "customer.public.customer", batch = "true")
    public void consumeCustomerMessage(List<ConsumerRecord<String, customer.public$.customer.Envelope>> messages) {
        log.info("Consumed message: {}", messages);
        final var values = messages.stream().map(ConsumerRecord::value).toList();

        streamService.streamCustomer(values);

    }

    @KafkaListener(topics = "book.public.book", batch = "true")
    public void consumeBookMessage(List<ConsumerRecord<String, book.public$.book.Envelope>> messages) {
        log.info("Consumed message: {}", messages);
        final var values = messages.stream().map(ConsumerRecord::value).toList();

        streamService.streamBook(values);
    }
}
