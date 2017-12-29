package com.peterjurkovic.travelagency.client.config;

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

    @Value("${voice.answerUrl}")
    private String answerUrl;

    public String getAnswerUrl() {
        return answerUrl;
    }
}
