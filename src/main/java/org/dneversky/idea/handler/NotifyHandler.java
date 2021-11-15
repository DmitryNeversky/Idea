//package org.dneversky.idea.handler;
//
//import lombok.extern.slf4j.Slf4j;
//import org.dneversky.idea.entity.Notification;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.data.rest.core.annotation.HandleAfterCreate;
//import org.springframework.data.rest.core.annotation.RepositoryEventHandler;
//import org.springframework.messaging.simp.SimpMessagingTemplate;
//import org.springframework.stereotype.Component;
//
//@Component
//@RepositoryEventHandler
//@Slf4j
//public class NotifyHandler {
//
//    private final SimpMessagingTemplate simpMessagingTemplate;
//
//    @Autowired
//    public NotifyHandler(SimpMessagingTemplate simpMessagingTemplate) {
//        this.simpMessagingTemplate = simpMessagingTemplate;
//    }
//
//    @HandleAfterCreate
//    public void handleIdeaChangedStatus(Notification notification){
//        this.simpMessagingTemplate.convertAndSend("/usr/new", notification);
//        log.info(">> Sending Message to WS: ws://todo/new - " + notification);
//    }
//}
