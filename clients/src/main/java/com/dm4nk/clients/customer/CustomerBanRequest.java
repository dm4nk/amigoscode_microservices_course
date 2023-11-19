package com.dm4nk.clients.customer;

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
public class CustomerBanRequest {
    private UUID id;
    private Integer duration;
    private OffsetDateTime validTo;
}
