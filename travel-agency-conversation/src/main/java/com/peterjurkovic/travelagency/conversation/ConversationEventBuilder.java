package com.peterjurkovic.travelagency.conversation;

import java.util.HashMap;
import java.util.Map;

import com.peterjurkovic.travelagency.conversation.ConversationEvent.Type;


    public class ConversationEventBuilder {
        private ConversationEvent.Type type;
        private PayloadBuilder payloadBuilder = new PayloadBuilder();
        
        private ConversationEventBuilder(Type type){
            this.type = type;
        }

        public ConversationEventBuilder withType(ConversationEvent.Type type) {
            this.type = type;
            return this;
        }
        
        public static ConversationEventBuilder type(ConversationEvent.Type type) {
            return new ConversationEventBuilder(type);
        }
        
        public ConversationEvent build() {
            ConversationEvent event =  new ConversationEvent(type);
            // fix me event.setPayload(new Payload(properties));
            return event;
        }

        public PayloadBuilder withPayload() {
            return payloadBuilder;
        }

        private ConversationEvent buildEvent(ConversationEvent.Payload payload) {
            return new ConversationEvent(type, payload);
        }

        protected class PayloadBuilder {

            private Map<String, Object> properties = new HashMap<>();

            public PayloadBuilder nickname(String alias) {
                return this;
            }

          

            public PayloadBuilder property(String property, Object value) {
                properties.put(property, value);
                return this;
            }


            public ConversationEvent build() {
                return buildEvent(new ConversationEvent.Payload(properties));
            }
        }
    }


