package com.peterjurkovic.travelagency.conversation.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Description;

import com.peterjurkovic.travelagency.conversation.event.WebsocketSessionListener;
import com.peterjurkovic.travelagency.conversation.event.WebsocketSessionRepository;

@Configuration
@ComponentScan("com.peterjurkovic.travelagency.common")
public class ConversationConfig {

    @Bean
    @Description("Tracks user presence (join / leave) and broacasts it to all connected users")
    public WebsocketSessionListener participantListenerBean(){
        return new WebsocketSessionListener();
    }
    
    @Bean
    @Description("Stores active subscription")
    public WebsocketSessionRepository participantRepositoryBean(){
        return new WebsocketSessionRepository();
    }
    
    @Bean
    public ConversationProperties conversationProperties(){
        return new ConversationProperties();
    }
}
