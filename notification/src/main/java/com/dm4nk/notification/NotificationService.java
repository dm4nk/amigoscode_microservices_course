package com.dm4nk.notification;

import com.dm4nk.aop.logger.Level;
import com.dm4nk.aop.logger.annotations.Loggable;
import com.dm4nk.clients.notification.NotificationRequest;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Slf4j
@Service
@AllArgsConstructor
@Loggable(level = Level.INFO)
public class NotificationService {
    private final NotificationRepository notificationRepository;

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
