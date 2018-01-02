package com.peterjurkovic.travelagency.conversation.model;

import com.peterjurkovic.travelagency.common.model.Conversation;
import com.peterjurkovic.travelagency.common.model.ConversationMessage;

public class CreateMessage {


    private String user;
    private String content;
    private String conversationId;
    private String participantId;

    public String getConversationId() {
        return conversationId;
    }

    public void setConversationId(String conversationId) {
        this.conversationId = conversationId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
    

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }
    
    public String getParticipantId() {
        return participantId;
    }

    public void setParticipantId(String participantId) {
        this.participantId = participantId;
    }

    @Override
    public String toString() {
        return "ConversationMessage [user=" + user + ", content=" + content + ", conversationId=" + conversationId + "]";
    }
    
    public ConversationMessage toConversationMessage(Conversation conversation){
        ConversationMessage conversationMessage = new ConversationMessage(conversation);
        conversationMessage.setContent(this.content);
        conversationMessage.setParticipantId(this.participantId);
        return conversationMessage;
    }
    

}
