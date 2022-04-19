package org.dneversky.notifier.service;

import org.dneversky.notifier.model.Notification;

public interface NotificationSender {
    void sendNotification(Notification notification);
}
