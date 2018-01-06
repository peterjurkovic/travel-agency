package com.peterjurkovic.travelagency.conversation.sms;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import com.nexmo.client.NexmoClient;
import com.nexmo.client.sms.SmsSubmissionResult;
import com.nexmo.client.sms.messages.Message;
import com.nexmo.client.sms.messages.TextMessage;
import com.peterjurkovic.travelagency.conversation.config.ConversationProperties;

@Component
public class SmsSender {

    private final Logger log = LoggerFactory.getLogger(getClass());
    
    @Autowired
    private ConversationProperties properties;
    
    @Autowired
    private NexmoClient client;
    
    
    @Async
    public void sendMessage(String content, String numberTo){
        log.info("Sending message \"{}\" to ", content, numberTo);
        Message message = new TextMessage(properties.getNumberFor(numberTo), numberTo, content);
        try {
            SmsSubmissionResult[] results = client.getSmsClient().submitMessage(message);
            
            if(log.isDebugEnabled()){
                for(SmsSubmissionResult smsResult : results){
                    log.debug("RESULT: {} " + smsResult);
                }
            }
            log.info("Message sent to {}", numberTo);
        } catch (Exception e) {
            log.error("Failed to send "+content+" to number " + numberTo, e );
        }
    }
}
