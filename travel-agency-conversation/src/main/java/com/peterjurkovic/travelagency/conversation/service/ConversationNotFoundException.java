package com.peterjurkovic.travelagency.conversation.service;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.peterjurkovic.travelagency.conversation.exceptions.ConversationException;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class ConversationNotFoundException extends ConversationException{

    /**
     * 
     */
    private static final long serialVersionUID = 1727592012739688769L;

    public ConversationNotFoundException(String message){
        super(message);
    }
}
