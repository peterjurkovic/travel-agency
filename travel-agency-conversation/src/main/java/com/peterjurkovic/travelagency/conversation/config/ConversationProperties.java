package com.peterjurkovic.travelagency.conversation.config;
 
import org.springframework.boot.context.properties.ConfigurationProperties;


@ConfigurationProperties("conversation")
public class ConversationProperties {

    private String number;

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }
    
    
}
