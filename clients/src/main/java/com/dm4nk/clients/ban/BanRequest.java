package com.dm4nk.clients.ban;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.OffsetDateTime;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BanRequest {
    private UUID customerId;
    private Integer duration;
    private OffsetDateTime validTo;
}
