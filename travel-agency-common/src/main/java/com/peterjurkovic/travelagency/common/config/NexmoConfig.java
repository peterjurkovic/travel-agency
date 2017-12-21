package com.peterjurkovic.travelagency.common.config;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.nexmo.client.NexmoClient;
import com.nexmo.client.auth.AuthMethod;
import com.nexmo.client.auth.TokenAuthMethod;

@Configuration
public class NexmoConfig {

    @Value("${nexmo.api-key}")
    private String apiKey;
    
    @Value("${nexmo.api-secret}")
    private String apiSecret;
    
    
    @Bean
    public NexmoClient nexmoClientBean(){
        AuthMethod auth = new TokenAuthMethod(this.apiKey, this.apiSecret);
        return new NexmoClient(auth);
    }
}
