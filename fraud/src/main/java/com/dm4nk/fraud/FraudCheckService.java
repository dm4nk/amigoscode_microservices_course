package com.dm4nk.fraud;

import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public record FraudCheckService(
        FraudCheckHistoryRepository fraudCheckHistoryRepository
) {
    public Boolean isFraudulentCustomer(Integer customerId) {
        Boolean result = false;

        fraudCheckHistoryRepository.save(
                FraudCheckHistory.builder()
                        .customerId(customerId)
                        .isFraudster(result)
                        .createdAt(LocalDateTime.now())
                        .build()
        );
        return result;
    }
}
