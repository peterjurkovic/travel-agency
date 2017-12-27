package com.peterjurkovic.travelagency.conversation.model;

public class ConversationMessage {

    public enum Type {
        USER, AGENT, BOT
    }

    private String type;
    private String nickname;
    private String content;
    private String conversationId;

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

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "ConversationMessage [type=" + type + ", nickname=" + nickname + ", content=" + content + ", conversationId=" + conversationId + "]";
    }
    
    

}
