package com.peterjurkovic.travelagency.conversation.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.peterjurkovic.travelagency.common.model.Conversation;
import com.peterjurkovic.travelagency.common.model.ConversationMessage;

public class CreateMessage {


    private final String content;
    private final String conversationId;
    private final String participantId;

    
    @JsonCreator
    public CreateMessage(
            @JsonProperty("content") String content, 
            @JsonProperty("conversationId")String conversationId, 
            @JsonProperty("participantId") String participantId) {
        
        this.content = content;
        this.conversationId = conversationId;
        this.participantId = participantId;
    }

    public String getConversationId() {
        return conversationId;
    }

//    public void setConversationId(String conversationId) {
//        this.conversationId = conversationId;
//    }

    public String getContent() {
        return content;
    }

//    public void setContent(String content) {
//        this.content = content;
//    }
    
    public String getParticipantId() {
        return participantId;
    }

//    public void setParticipantId(String participantId) {
//        this.participantId = participantId;
//    }

    @Override
    public String toString() {
        return "ConversationMessage [content=" + content + ", conversationId=" + conversationId + "]";
    }
    
    public ConversationMessage toConversationMessage(Conversation conversation){
        ConversationMessage conversationMessage = new ConversationMessage(conversation);
        conversationMessage.setContent(this.content);
        conversationMessage.setParticipantId(this.participantId);
        return conversationMessage;
    }
    

}
