package com.dm4nk.search.listener;

import com.dm4nk.search.service.StreamService;
import customer.public$.customer.Envelope;
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
    public void consumeMessage(List<ConsumerRecord<String, Envelope>> messages) {
        log.info("Consumed message: {}", messages);

        for (ConsumerRecord<String, Envelope> message : messages) {
            streamService.stream(message.value());
        }
    }
}
