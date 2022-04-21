package org.dneversky.notifier.service;

import org.dneversky.notifier.entity.Notification;

public interface NotificationService {
    void sendNotification(Notification notification);
}
