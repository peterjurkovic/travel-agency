package com.peterjurkovic.travelagency.conversation.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Description;

import com.peterjurkovic.travelagency.conversation.event.ParticipantListener;
import com.peterjurkovic.travelagency.conversation.repository.ParticipantRepository;

@Configuration
@ComponentScan("com.peterjurkovic.travelagency.common")
public class ConversationConfig {

    @Bean
    @Description("Tracks user presence (join / leave) and broacasts it to all connected users")
    public ParticipantListener participantListenerBean(){
        return new ParticipantListener();
    }
    
    @Bean
    @Description("Stores active subscription")
    public ParticipantRepository participantRepositoryBean(){
        return new ParticipantRepository();
    }
    
    @Bean
    public ConversationProperties conversationProperties(){
        return new ConversationProperties();
    }
}
