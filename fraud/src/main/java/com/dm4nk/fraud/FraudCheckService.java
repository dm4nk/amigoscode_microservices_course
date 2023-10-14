package com.dm4nk.fraud;

import com.dm4nk.aop.logger.Level;
import com.dm4nk.aop.logger.annotations.Loggable;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@Loggable(level = Level.INFO)
@AllArgsConstructor
public class FraudCheckService {
    private final FraudCheckHistoryRepository fraudCheckHistoryRepository;

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
