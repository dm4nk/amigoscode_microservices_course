package com.dm4nk.clients.customer;

import java.time.OffsetDateTime;
import java.util.UUID;

public record CustomerMutationRequest(
        UUID id,
        String firstName,
        String lastName,
        String email,
        Boolean ban,
        Integer banForDays,
        OffsetDateTime banUntil) {
}
