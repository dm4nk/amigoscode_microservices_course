package com.operator.plan.listener;

import com.operator.clients.customer.ChangeTarifPlanRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class KafkaConsumerListener {

    @KafkaListener(topics = "tarifplan")
    public void consumeCustomerMessage(ChangeTarifPlanRequest message) {
        log.info("Consumed message: {}", message);

        // soma heavy tarif plan change logic
    }
}
