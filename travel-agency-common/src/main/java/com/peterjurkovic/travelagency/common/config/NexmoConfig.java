package com.peterjurkovic.travelagency.common.config;


import com.nexmo.client.auth.JWTAuthMethod;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import com.nexmo.client.NexmoClient;
import com.nexmo.client.auth.AuthMethod;
import com.nexmo.client.auth.TokenAuthMethod;
import com.peterjurkovic.travelagency.common.tools.CommonProperties;

import java.io.File;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;

import javax.annotation.PostConstruct;

@Component
@Configuration
public class NexmoConfig {

    private final Logger log = LoggerFactory.getLogger(getClass());
    
    @Value("${nexmo.apiKey}")
    private String apiKey;
    
    @Value("${nexmo.apiSecret}")
    private String apiSecret;

    @Value("${nexmo.applicationId}")
    private String applicationId;

    @Value("${nexmo.jwtPrivateKey}")
    private String pathPrivateKey;
    
    @Bean
    @Scope("singleton")
    public NexmoClient nexmoClientBean() throws InvalidKeySpecException, NoSuchAlgorithmException, InvalidKeyException, IOException {
        AuthMethod tokenAuthMethod = new TokenAuthMethod(this.apiKey, this.apiSecret);
        AuthMethod jwtAuthMethod = new JWTAuthMethod(this.applicationId, new File(this.pathPrivateKey).toPath());
        return new NexmoClient(tokenAuthMethod, jwtAuthMethod);
    }
    
    
    @Bean("commonProperties")
    public CommonProperties commonProperties(){
        return new CommonProperties(); 
    }
    
    @PostConstruct
    private void print(){
        log.info(this.toString());
    }


    @Override
    public String toString() {
        return "NexmoConfig [apiKey=" + apiKey + ", applicationId=" + applicationId + "]";
    }
    
    
}
