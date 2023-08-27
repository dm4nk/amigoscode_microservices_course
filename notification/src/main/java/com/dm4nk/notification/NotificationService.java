package com.dm4nk.notification;

import com.dm4nk.clients.notification.NotificationRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Slf4j
@Service
public record NotificationService(
        NotificationRepository notificationRepository
) {
    public void send(NotificationRequest notificationRequest) {
        log.info("Sending notification: {}", notificationRequest);
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
