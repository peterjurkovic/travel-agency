package com.peterjurkovic.travelagency.conversation.event;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;

@Component
public class UserMessageSender {

    @Autowired
    private SimpMessagingTemplate messagingTemplate;
    
   
    
}
