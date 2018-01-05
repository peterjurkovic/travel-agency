package com.peterjurkovic.travelagency.common.tools;

import org.springframework.beans.factory.annotation.Value;


public class CommonProperties {

    @Value("${conversation.url}")
    private String conversationUrl;

    public String getConversationUrl() {
        return conversationUrl;
    }

    public void setConversationUrl(String conversationUrl) {
        this.conversationUrl = conversationUrl;
    }
    
    
}
