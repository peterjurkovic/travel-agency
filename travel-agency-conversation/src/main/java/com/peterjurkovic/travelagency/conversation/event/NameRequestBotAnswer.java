package com.peterjurkovic.travelagency.conversation.event;

import java.util.List;
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
import com.peterjurkovic.travelagency.conversation.service.ConversationService;

//@Component
public class NameRequestBotAnswer implements BotAnswer {

    private final Logger log = LoggerFactory.getLogger(getClass());

    @Autowired 
    private SimpMessagingTemplate messagingTemplate;
    
    @Autowired
    private ConversationRepository conversationRepository;
    
    @Autowired
    private ConversationService conversationService;
    
    @Override
    public int getOrder() {
        return 2;
    }

    @Override
    public boolean tryAnswer(ConversationMessage message) {
        if(message.isUserMessage()){
            Conversation conversation = message.getConversation();
            if(conversation.isAnonymous() && conversation.agentHasNotJoined()){
//                log.debug("Phone number extraction attempt");
//                if(conversation.getNumberRequests() > 4){
//                    return false;
//                }
//                
//                    List<ConversationMessage> messages = conversationService.getLastMessages(conversation);
//                    if(! messages.isEmpty() || messages.get(0).isBotMessage()){
//                    conversation.incrementNumberInquires();
//                    Optional<String> number = PhoneUtils.extractPhoneNumber(message.getContent());
//                    
//                    if(number.isPresent()){
//                        submitSuccessAnswer(message, conversation, number);
//                    }else{
//                        submitInvalidPhoneNumber(message, conversation);
//                    }
//                    
//                    return true;
//                }
//                    
//                if(conversation.getNumberRequests() == 3){
//                    submitWaitForAgentAnswer(message, conversation);
//                }    
            }
        }
        return false;
    }

}
