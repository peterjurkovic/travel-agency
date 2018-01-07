package com.peterjurkovic.travelagency.client.config;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author Nicola Giacchetta
 */
@Component
@EnableConfigurationProperties
@ConfigurationProperties(prefix="voice")
public class VoiceConfiguration {
    
    private final Logger log = LoggerFactory.getLogger(getClass());

    @Value("${voice.answerUrl}")
    private String answerUrl;

    @Value("${voice.agentNumber}")
    private String agentNumber;

    public String getAnswerUrl() {
        return answerUrl;
    }

    public String getAgentNumber() {
        return agentNumber;
    }
    
    public void setAnswerUrl(String answerUrl) {
        this.answerUrl = answerUrl;
    }

    public void setAgentNumber(String agentNumber) {
        this.agentNumber = agentNumber;
    }
    
    @PostConstruct
    private void printProperties(){
        log.info(this.toString());
    }

    @Override
    public String toString() {
        return "VoiceConfiguration [answerUrl=" + answerUrl + ", agentNumber=" + agentNumber + "]";
    }

    
    
}
