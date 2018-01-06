package com.peterjurkovic.travelagency.common.tools;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;


/**
 * @author peter
 *
 */
public class CommonProperties {

    private final static Logger log = LoggerFactory.getLogger(CommonProperties.class);
    
    @Value("${conversation.url}")
    private String conversationUrl;
 
    public String getConversationUrl() {
        return conversationUrl;
    }

    public void setConversationUrl(String conversationUrl) {
        this.conversationUrl = conversationUrl;
    }

    @Override
    public String toString() {
        return "[conversationUrl=" + conversationUrl + "]";
    }

    @PostConstruct
    private void printProperties(){
        log.info(this.toString());
    }

    
}
