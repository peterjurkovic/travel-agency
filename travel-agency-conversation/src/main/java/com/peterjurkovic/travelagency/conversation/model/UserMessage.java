package com.peterjurkovic.travelagency.conversation.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.peterjurkovic.travelagency.common.model.Conversation;
import com.peterjurkovic.travelagency.common.model.ConversationMessage;

public class UserMessage {

    private String messageId;

    public UserMessage(){}
    
    public UserMessage(ConversationMessage conversation) {
        this.messageId = conversation.getId();
    }

    public String getMessageId() {
        return messageId;
    }

    public void setMessageId(String messageId) {
        this.messageId = messageId;
    }
    
    
}
