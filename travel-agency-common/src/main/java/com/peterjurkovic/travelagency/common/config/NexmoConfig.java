package com.peterjurkovic.travelagency.common.config;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

import com.nexmo.client.NexmoClient;
import com.nexmo.client.auth.AuthMethod;
import com.nexmo.client.auth.TokenAuthMethod;
import com.peterjurkovic.travelagency.common.tools.CommonProperties;

@Component
@Configuration
public class NexmoConfig {

    @Value("${nexmo.apiKey}")
    private String apiKey;
    
    @Value("${nexmo.apiSecret}")
    private String apiSecret;
    
    
    @Bean
    public NexmoClient nexmoClientBean(){
        AuthMethod auth = new TokenAuthMethod(this.apiKey, this.apiSecret);
        return new NexmoClient(auth);
    }
    
    
    @Bean("commonProperties")
    public CommonProperties commonProperties(){
        return new CommonProperties(); 
    }
    
}
