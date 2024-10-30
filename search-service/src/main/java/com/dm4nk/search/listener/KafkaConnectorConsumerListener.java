package com.dm4nk.search.listener;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.avro.generic.GenericRecord;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
@Slf4j
public class KafkaConnectorConsumerListener {

    @KafkaListener(topics = "customer.public.customer", batch = "true")
    public void consumeMessage(List<ConsumerRecord<String, GenericRecord>> message) {
        log.info("Consumed message: {}", message);
    }
}
