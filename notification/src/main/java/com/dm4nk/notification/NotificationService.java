package com.dm4nk.notification;

import com.dm4nk.clients.notification.NotificationRequest;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public record NotificationService(
        NotificationRepository notificationRepository
) {
    public void send(NotificationRequest notificationRequest) {
        notificationRepository.save(
                Notification.builder()
                        .toCustomerId(notificationRequest.toCustomerId())
                        .toCustomerEmail(notificationRequest.toCustomerEmail())
                        .sender("Dmitrii")
                        .sentAt(LocalDateTime.now())
                        .message(notificationRequest.message())
                        .build()
        );
    }
}
