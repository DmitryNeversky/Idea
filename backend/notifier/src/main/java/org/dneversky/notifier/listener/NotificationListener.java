package org.dneversky.notifier.listener;

import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@EnableRabbit
@Component
public class NotificationListener {

    @RabbitListener(queues = "notificationQueue")
    public void listenOne(String message) {
        System.out.println(message);
    }

    @RabbitListener(queues = "notificationQueue")
    public void listenTwo(String message) {
        System.out.println(message);
    }
}
