package org.dneversky.notifier.service.impl;

import org.dneversky.notifier.entity.Notification;
import org.dneversky.notifier.repository.NotificationRepository;
import org.dneversky.notifier.service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

@Service
public class NotificationServiceImpl implements NotificationService {

    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    @Autowired
    private NotificationRepository notificationRepository;

    public void sendNotification(Notification notification) {
        notification = notificationRepository.save(notification);
        messagingTemplate.convertAndSend("/topic/user/" + notification.getRecipientId() + "/notifications", notification);
    }
}
