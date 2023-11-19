package com.dm4nk.clients.notification;

import java.util.UUID;

public record NotificationRequest(
        UUID toCustomerId,
        String toCustomerEmail,
        String message
) {
}
