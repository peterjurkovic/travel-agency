package com.peterjurkovic.travelagency.conversation;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;

import com.peterjurkovic.travelagency.common.utils.JsonUtils;

public class ConversationEventTest {

    @Test
    public void testPayloadToJson(){
        ConversationEvent.Payload payload = new ConversationEvent.Payload();
        payload.setProperties("foo", "bar");
            
        String json = JsonUtils.toJson(payload);
        
        assertThat(json).isEqualTo("{\"foo\":\"bar\"}");
    }
    
    @Test
    public void testPayloadFromJson(){
        String payloadString = "{\"foo\":\"bar\"}";
        
        ConversationEvent.Payload payload = JsonUtils.fromJson(payloadString, ConversationEvent.Payload.class);
        
        assertThat(payload.getProperties()).containsEntry("foo", "bar");
    }
}
