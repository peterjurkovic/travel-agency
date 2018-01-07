package com.peterjurkovic.travelagency.conversation.event;

import static java.util.Arrays.asList;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;

import com.peterjurkovic.travelagency.common.model.Conversation;
import com.peterjurkovic.travelagency.common.model.ConversationMessage;
import com.peterjurkovic.travelagency.common.repository.ConversationRepository;
import com.peterjurkovic.travelagency.conversation.event.MessageBuilder.MessageType;
import com.peterjurkovic.travelagency.conversation.model.CreateMessage;
import com.peterjurkovic.travelagency.conversation.service.ConversationService;

@Component
public class NameRequestBotAnswer implements BotAnswer {

    private static final Set<String> YES_ALLIASSES = new HashSet<>(asList("yes", "yep")); 
    
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
            
            if(canAskForName(conversation)){
                log.debug("Name extraction attempt");
                if(conversation.getNameRequests() > 2){
                    return false;
                }
     
                if(conversation.getNameRequests() == 0){
                    if(message.getContent().length() < 4){
                        submitInvalidName(message, conversation, MessageType.NEAME_INVALID);
                    }else{
                        submitSuccessAnswer(message, conversation, message.getContent());
                    }
                }else{
                    if(answerIsYes(message)){
                        List<ConversationMessage> lastMessages = conversationService.getLastMessages(conversation);
                        if(lastMessages.size() > 2){
                            String name = lastMessages.get(2).getContent();
                            submitSuccessAnswer(message, conversation, name);
                        }
                    }else{
                        submitInvalidName(message, conversation, MessageType.NEAME_FAILED);
                    }
                }
                
                return true;
             
               
                
            }
        }
        return false;
    }

    private boolean canAskForName(Conversation conversation) {
        return conversation.getNameRequests() < 3 && 
                conversation.isAnonymous() && 
                conversation.agentHasNotJoined() && 
                conversation.hasPhoneNumberAssigned();
    }
    
    
    private void submitSuccessAnswer(ConversationMessage message, Conversation conversation, String name) {
        log.info("Name extracted {}", name);
        if(conversation.getUser().isPresent()){
            conversation.getUser().get().setName(name);
            
            save(conversation);
            
            CreateMessage answer = MessageBuilder.message(MessageType.NEAME_SAVED)
                           .withAuthor(conversation.getBot().get())
                           .withConversation(conversation)
                           .build();
            
            submitAnswer(answer);
        }
        
    }
    
    private void submitInvalidName(ConversationMessage message, Conversation conversation, MessageType type) {
        log.info("Failed to extract number form {}", message.getContent());
        CreateMessage answer = MessageBuilder.message(type)
                .withAuthor(conversation.getBot().get())
                .withConversation(conversation)
                .build();
        
        save(conversation);
        
        submitAnswer(answer);
    }

    private void save(Conversation conversation) {
        conversation.incrementNameInquires();
        conversationRepository.save(conversation);
    }
    
    private void submitAnswer(CreateMessage answer ){
        String destination = "/topic/chat/" + answer.getConversationId();
        
        ConversationMessage conversationMessage = conversationService.create(answer);
        messagingTemplate.convertAndSend(destination, conversationMessage);
        log.info("Phone number answer sent: {}" , conversationMessage);
    }
    
    private boolean answerIsYes(ConversationMessage message){
        String answer = message.getContent().trim().toLowerCase();
        for(String alias : YES_ALLIASSES){
            if(answer.contains( alias )){
                return true;
            }
        }
        return false;
    }
}
