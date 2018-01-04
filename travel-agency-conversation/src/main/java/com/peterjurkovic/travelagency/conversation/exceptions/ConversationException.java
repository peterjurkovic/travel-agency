package com.peterjurkovic.travelagency.conversation.exceptions;

public class ConversationException extends RuntimeException{

    private static final long serialVersionUID = -4367456101860160046L;

    public ConversationException(){}
    
    public ConversationException(String message){
        super(message);
    }
}
