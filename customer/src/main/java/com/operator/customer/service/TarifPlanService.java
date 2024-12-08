package com.operator.customer.service;

import com.operator.clients.customer.ChangeTarifPlanRequest;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class TarifPlanService {
    private KafkaTemplate<String, ChangeTarifPlanRequest> kafkaTemplate;

    public ResponseEntity<Void> changePlan(ChangeTarifPlanRequest tarifPlanRequest) {
        kafkaTemplate.send("tarifplan", tarifPlanRequest);

        return ResponseEntity.ok().build();
    }
}
