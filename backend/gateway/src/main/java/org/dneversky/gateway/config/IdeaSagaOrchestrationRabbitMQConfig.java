package org.dneversky.gateway.config;

import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class IdeaSagaOrchestrationRabbitMQConfig {

    @Bean
    public Queue createIdeaQueueReceiver() {

    }
}
