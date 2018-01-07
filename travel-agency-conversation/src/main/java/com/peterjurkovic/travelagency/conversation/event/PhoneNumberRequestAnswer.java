package com.peterjurkovic.travelagency.conversation.event;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;

import com.peterjurkovic.travelagency.common.model.Conversation;
import com.peterjurkovic.travelagency.common.model.ConversationMessage;
import com.peterjurkovic.travelagency.common.repository.ConversationRepository;
import com.peterjurkovic.travelagency.common.utils.PhoneUtils;
import com.peterjurkovic.travelagency.conversation.event.MessageBuilder.MessageType;
import com.peterjurkovic.travelagency.conversation.model.CreateMessage;
import com.peterjurkovic.travelagency.conversation.service.ConversationService;

@Component
public class PhoneNumberRequestAnswer implements BotAnswer{
    
    private final Logger log = LoggerFactory.getLogger(getClass());

    @Autowired 
    private SimpMessagingTemplate messagingTemplate;
    
    @Autowired
    private ConversationRepository conversationRepository;
    
    @Autowired
    private ConversationService conversationService;
    
    @Override
    public int getOrder() {
        return 1;
    }

    @Override
    public boolean tryAnswer(ConversationMessage message) {
        if(message.isUserMessage()){
            Conversation conversation = message.getConversation();
            
            if(conversation.getNumberRequests() > 2){
                return false;
            }
            
            if(!conversation.hasPhoneNumberAssigned() && conversation.agentHasNotJoined()){
                log.debug("Phone number extraction attempt");

                conversation.incrementNumberInquires();
                Optional<String> number = PhoneUtils.extractPhoneNumber(message.getContent());
                
                if(number.isPresent()){
                    submitSuccessAnswer(message, conversation, number);
                }else{
                    if(conversation.getNumberRequests() == 3){
                        submitWaitForAgentAnswer(message, conversation);
                    }else{    
                        submitInvalidPhoneNumber(message, conversation);
                    }
                }
                return true;  
            }
        }
        return false;
    }

    private void submitInvalidPhoneNumber(ConversationMessage message, Conversation conversation) {
        log.info("Failed to extract number form {}", message.getContent());
        CreateMessage answer = MessageBuilder.message(MessageType.PHONE_NUMBER_INVALID)
                .withAuthor(conversation.getBot().get())
                .withConversation(conversation)
                .build();
        conversationRepository.save(conversation);
        submitAnswer(answer);
    }
    
    private void submitWaitForAgentAnswer(ConversationMessage message, Conversation conversation) {
        log.info("Unable to read phone number {}", message.getContent());
        CreateMessage answer = MessageBuilder.message(MessageType.PHONE_NUMBER_REQUEST_FAILED)
                .withAuthor(conversation.getBot().get())
                .withConversation(conversation)
                .build();
        conversationRepository.save(conversation);
        submitAnswer(answer);
    }

    private void submitSuccessAnswer(ConversationMessage message, Conversation conversation, Optional<String> number) {
        log.info("Number extracted {}", number.get());
        conversation.setUserPhoneNumber(number.get());
        
        conversationRepository.save(conversation);
        
        CreateMessage answer = MessageBuilder.message(MessageType.PHONE_NUMBER_SAVED)
                       .withAuthor(conversation.getBot().get())
                       .withConversation(conversation)
                       .replace("phoneNumber", number.get())
                       .build();
        
        submitAnswer(answer);
    }

    private void submitAnswer(CreateMessage answer ){
        String destination = "/topic/chat/" + answer.getConversationId();
        
        ConversationMessage conversationMessage = conversationService.create(answer);
        messagingTemplate.convertAndSend(destination, conversationMessage);
        log.info("Phone number answer sent: {}" , conversationMessage);
        
    }
    
}
