package com.peterjurkovic.travelagency.conversation.config;

import java.util.Map;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties("conversation")
public class ConversationProperties {

    private Map<String, String> number;

    public Map<String, String> getNumber() {
        return number;
    }

    public void setNumber(Map<String, String> number) {
        this.number = number;
    }
    
    
    public String getNumberFor(String number){
        if(number.startsWith("44")){
            return this.number.get("UK");
        }
        return this.number.get("SW");
    }
    

}
