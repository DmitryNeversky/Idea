package org.dneversky.notifier.listener;

import org.dneversky.notifier.model.Notification;
import org.dneversky.notifier.service.NotificationService;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@EnableRabbit
@Component
public class NotificationListener {

    @Autowired
    private NotificationService notificationService;

    @RabbitListener(queues = "notificationQueue")
    public void listenOne(Notification notification) {
        notificationService.sendNotification(notification);
    }
}
