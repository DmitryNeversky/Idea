package org.dneversky.idea.service;

import org.dneversky.idea.entity.Notification;

import java.time.LocalDateTime;

public interface NotificationService {

    Notification create(String title, String message, LocalDateTime createdDate);
}
