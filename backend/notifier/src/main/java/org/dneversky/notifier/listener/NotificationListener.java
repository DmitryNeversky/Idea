package org.dneversky.notifier.listener;

import org.dneversky.notifier.entity.Notification;
import org.dneversky.notifier.service.impl.NotificationServiceImpl;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@EnableRabbit
@Component
public class NotificationListener {

    @Autowired
    private NotificationServiceImpl notificationServiceImpl;

    @RabbitListener(queues = "notificationQueue")
    public void listenOne(Notification notification) {
        notificationServiceImpl.sendNotification(notification);
    }
}
