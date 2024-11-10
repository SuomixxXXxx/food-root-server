package org.chiches.foodrootservir.services.impl;

import org.chiches.foodrootservir.misc.StompDestination;
import org.chiches.foodrootservir.services.NotificationService;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

@Service
public class NotificationServiceImpl implements NotificationService {
    private final SimpMessagingTemplate simpMessagingTemplate;

    public NotificationServiceImpl(SimpMessagingTemplate simpMessagingTemplate) {
        this.simpMessagingTemplate = simpMessagingTemplate;
    }


    @Override
    public void sentToAll(StompDestination destination, Object message) {
        simpMessagingTemplate.convertAndSend(destination.getDestination(), message);
    }

    @Override
    public void sentToUser(StompDestination destination, Object message, String username) {
        simpMessagingTemplate.convertAndSendToUser(username, destination.getDestination(), message);
    }
}
