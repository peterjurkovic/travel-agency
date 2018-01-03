package com.peterjurkovic.travelagency.conversation.event;

import com.peterjurkovic.travelagency.common.model.Conversation;
import com.peterjurkovic.travelagency.common.model.Participant;
import com.peterjurkovic.travelagency.conversation.model.CreateMessage;

public class MessageBuilder {

    private String participantId;
    private String conversationId;
    private String content;
    
    enum MessageType{
        PHONE_NUMBER_REQUEST(
                "Hi there! Please provider your phone number in international "+
                " format (e.g. +447702132132) if you lose your connection we will text you.");
        
        private final String content;

        private MessageType(String content) {
            this.content = content;
        }

        public String getContent() {
            return content;
        }
        
    }
    
    public static MessageBuilder message(MessageType messageType){ 
        return new MessageBuilder().withContent(messageType.content);
    }
    
    
    public MessageBuilder withContent(String content){
        this.content = content;
        return this;
    }
    
    public MessageBuilder withConversation(Conversation conversation){
        this.conversationId = conversation.getId();
        return this;
    }
    
    public MessageBuilder withAuthor(Participant participant){
        this.participantId = participant.getId();
        return this;
    }
    
    public CreateMessage build(){
        return new CreateMessage(this.content, this.conversationId, this.participantId);
    }
}

