package com.dm4nk.clients.ban;

import java.time.OffsetDateTime;
import java.util.UUID;

public record BanRequest(UUID customerId, Integer duration, OffsetDateTime validTo) {
}
