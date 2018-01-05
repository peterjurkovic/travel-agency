package com.peterjurkovic.travelagency.admin.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.thymeleaf.extras.springsecurity4.dialect.SpringSecurityDialect;
import org.thymeleaf.spring5.SpringTemplateEngine;
import org.thymeleaf.templateresolver.ITemplateResolver;


@Configuration
@ComponentScan("com.peterjurkovic.travelagency.common")
public class WebConfiguration implements WebMvcConfigurer {

    private static final String[] CLASSPATH_RESOURCE_LOCATIONS = {
            "classpath:/META-INF/resources/", "classpath:/resources/",
            "classpath:/static/", "classpath:/public/" };
    
    private final Logger log = LoggerFactory.getLogger(getClass());

    private boolean twoFaEnabeld;
    
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/static/**")
        .addResourceLocations(CLASSPATH_RESOURCE_LOCATIONS);
    }
    
    @Bean
    public SpringTemplateEngine templateEngine(ITemplateResolver templateResolver, SpringSecurityDialect sec) {
        final SpringTemplateEngine templateEngine = new SpringTemplateEngine();
        templateEngine.setTemplateResolver(templateResolver);
        templateEngine.addDialect(sec); 
        return templateEngine;
    }
    
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        if(twoFaEnabeld){
            log.info("2FA is ENABLED");
//            registry.addInterceptor( verifyInterceptorBean() )
//                .excludePathPatterns("/static/**", "/webjars/**", "/verify");
        }else{
            log.info("2FA is DISABLED");
        }
     
    }
    
//    @Bean
//    public VerifyInterceptor verifyInterceptorBean(){
//        return new VerifyInterceptor(userUtilsBean());
//    }
//    
//    @Bean
//    public UserUtils userUtilsBean(){
//        return new UserUtils();
//    }
}
