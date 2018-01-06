package com.peterjurkovic.travelagency.conversation.sms;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import com.peterjurkovic.travelagency.common.model.Conversation;
import com.peterjurkovic.travelagency.common.model.ConversationMessage;
import com.peterjurkovic.travelagency.common.model.ConversationMessage.Type;
import com.peterjurkovic.travelagency.common.model.InboundSmsMessage;
import com.peterjurkovic.travelagency.common.model.Participant;
import com.peterjurkovic.travelagency.common.repository.ConversationMessageRepository;
import com.peterjurkovic.travelagency.common.repository.ConversationRepository;
import com.peterjurkovic.travelagency.common.repository.InboundSmsMessageRepository;


@Service
public class SmsConversationService {

    private final Logger log = LoggerFactory.getLogger(getClass());
    
    @Autowired
    private InboundSmsMessageRepository inboundSmsMessageRepository;
    @Autowired
    private SimpMessagingTemplate messagingTemplate;
    @Autowired
    private ConversationRepository conversationRepository;
    @Autowired
    private ConversationMessageRepository conversationMessageRepository;
        
    public void saveAndBroadcast(InboundSmsMessage smsMessage){
        log.info("Inbound message received {}", smsMessage);
        
        inboundSmsMessageRepository.save(smsMessage);
        
        
        Optional<Conversation> conversation = conversationRepository.findFirsByPhoneNumberOrderByDateDesc(smsMessage.getMsisdn());
        
        if(conversation.isPresent()){
            Optional<Participant> user = conversation.get().findParticipantByPhone(smsMessage.getMsisdn());
            
            if(user.isPresent()){
                ConversationMessage conversationMessage = createConversationMessage(smsMessage, conversation.get(), user.get());
                broadcastMessage(conversationMessage);
            }else{
                log.warn("No participant found associated with {} in {}", smsMessage.getMsisdn(), conversation.get().getId());
            }
                
        }else{
            log.warn("No conversation found associated with {}", smsMessage.getMsisdn());
        }
    }

    private ConversationMessage createConversationMessage(InboundSmsMessage smsMessage, Conversation conversation, Participant user) {
        ConversationMessage conversationMessage = new ConversationMessage(conversation, user);
        conversationMessage.setContent(smsMessage.getText());
        conversationMessage.setCreated(smsMessage.getTimestamp());
        conversationMessage.setType(Type.SMS);
        conversationMessageRepository.save(conversationMessage);
        return conversationMessage;
    }
    
    private void broadcastMessage(ConversationMessage message){
        String destination = "/topic/chat/" + message.getConversation().getId();
        messagingTemplate.convertAndSend(destination, message);
        log.info("Conversation sms message brodcasted: {}" , message);
    }
    
}
