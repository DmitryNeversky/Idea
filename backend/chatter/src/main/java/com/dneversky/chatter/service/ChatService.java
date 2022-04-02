package com.dneversky.chatter.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

@Service
public class ChatService {

    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    public void sendMessage(String topicSuffix) {
        messagingTemplate.convertAndSend("/topic/" + topicSuffix, "Hello, WebSocket!");
    }
}
