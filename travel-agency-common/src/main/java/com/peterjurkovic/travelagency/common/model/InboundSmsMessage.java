package com.peterjurkovic.travelagency.common.model;

import java.io.IOException;
import java.time.Instant;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationContext;
import com.fasterxml.jackson.databind.JsonDeserializer;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.peterjurkovic.travelagency.common.tools.DateUtils;

@JsonIgnoreProperties(ignoreUnknown = true)
public class InboundSmsMessage {

    private String id;
    private String messageId;
    private String to;
    private String text;
    private String msisdn;

    public String getId() {
        return id;
    }
    public void setId(String id) {
        this.id = id;
    }

    @JsonProperty("message-timestamp")
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    private Instant timestamp;
    
    
    public String getMessageId() {
        return messageId;
    }
    public void setMessageId(String messageId) {
        this.messageId = messageId;
    }
    public String getTo() {
        return to;
    }
    public void setTo(String to) {
        this.to = to;
    }
    public String getText() {
        return text;
    }
    public void setText(String text) {
        this.text = text;
    }
    public String getMsisdn() {
        return msisdn;
    }
    public void setMsisdn(String msisdn) {
        this.msisdn = msisdn;
    }
    public Instant getTimestamp() {
        return timestamp;
    }
    public void setTimestamp(Instant timestamp) {
        this.timestamp = timestamp;
    }
    @Override
    public String toString() {
        return "[messageId=" + messageId + ", to=" + to + ", text=" + text + ", msisdn=" + msisdn + ", timestamp=" + timestamp + "]";
    }
    
    private static class LocalDateTimeDeserializer extends JsonDeserializer<Instant> {
        @Override
        public Instant deserialize(JsonParser parser, DeserializationContext arg1) throws IOException, JsonProcessingException {
            return DateUtils.parseDateInstant(parser.getText());
        }
    }
}
