package com.peterjurkovic.travelagency.client.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;


@EnableMongoRepositories(basePackages = "com.peterjurkovic.travelagency")
@Configuration
public class SpringDataConfiguration {

    
}
