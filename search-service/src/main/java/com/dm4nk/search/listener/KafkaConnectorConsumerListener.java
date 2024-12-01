package com.dm4nk.search.listener;

import com.dm4nk.search.service.StreamService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
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
    private final KafkaTemplate<String, customer.public$.customer.Envelope> kafkaTemplate;

    // todo add dlt with batch
    //
    @KafkaListener(topics = "customer.public.customer", batch = "true")
    public void consumeCustomerMessage(List<customer.public$.customer.Envelope> messages) {
        log.info("Consumed message: {}", messages);

        try {
            streamService.streamCustomer(messages);
        } catch (Exception e) {
            for (customer.public$.customer.Envelope value : messages) {
                try {
                    streamService.streamCustomer(Collections.singletonList(value));
                } catch (Exception e1) {
                    log.error(e1.getMessage());
                    kafkaTemplate.send("customer.public.customer.dlt", value);
                }
            }
        }
    }

    @KafkaListener(topics = "book.public.book", batch = "true")
    public void consumeBookMessage(List<ConsumerRecord<String, book.public$.book.Envelope>> messages) {
        log.info("Consumed message: {}", messages);
        final var values = messages.stream().map(ConsumerRecord::value).toList();

        streamService.streamBook(values);
    }
}
