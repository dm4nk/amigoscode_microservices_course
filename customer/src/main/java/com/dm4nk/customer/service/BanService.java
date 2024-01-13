package com.dm4nk.customer.service;

import com.dm4nk.aop.logger.Level;
import com.dm4nk.aop.logger.annotations.Loggable;
import com.dm4nk.clients.ban.BanCheckResponse;
import com.dm4nk.clients.ban.BanClient;
import com.dm4nk.clients.ban.BanRequest;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;
import java.util.UUID;

@Service
@Loggable(level = Level.INFO)
@AllArgsConstructor
public class BanService {
    private final BanClient banClient;

    public void banCustomer(UUID customerId, Integer duration, OffsetDateTime validTo) {
        banClient.ban(BanRequest.builder()
                .customerId(customerId)
                .duration(duration)
                .validTo(validTo)
                .build());
    }

    public boolean isBanned(UUID customerId) {
        BanCheckResponse response = banClient.isBanned(customerId);
        return response.isBanned();
    }

}
