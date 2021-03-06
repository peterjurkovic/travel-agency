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
                "Hi there! Please provide us with your phone number in the international "+
                "format (e.g. +447702132132) and if you disconnect this chat, we will text you."+
                " Type the phone number directly to this chat!"),
        
        PHONE_NUMBER_SAVED(
                "Cool, thanks! Btw what's your name?"),
        
        PHONE_NUMBER_INVALID("Sorry, but it looks like it is not a valid phone number"),
        
        PHONE_NUMBER_REQUEST_FAILED("Hmm, to me it sill looks like like invalid number. Please wait for an agent."),
        
        NEAME_INVALID("Are you sure this is your name?"),
        NEAME_SAVED("Ok, thanks, please wait until any of avaiable agents join this conversation"),
        NEAME_FAILED("Ok, thanks, please wait until any of avaiable agents join this conversation");
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
    
    public MessageBuilder replace(String variableName, String replacement){
        this.content = this.content.replaceAll("\\{"+variableName+"\\}", replacement);
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

