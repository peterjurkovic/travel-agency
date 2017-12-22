package com.peterjurkovic.travelagency.client.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.thymeleaf.extras.springsecurity4.dialect.SpringSecurityDialect;
import org.thymeleaf.spring5.SpringTemplateEngine;
import org.thymeleaf.templateresolver.ITemplateResolver;

import com.peterjurkovic.travelagency.client.user.UserUtils;
import com.peterjurkovic.travelagency.client.verify.VerifyInterceptor;

@Configuration
@ComponentScan("com.peterjurkovic.travelagency.common")
public class WebConfiguration implements WebMvcConfigurer {

    private static final String[] CLASSPATH_RESOURCE_LOCATIONS = {
            "classpath:/META-INF/resources/", "classpath:/resources/",
            "classpath:/static/", "classpath:/public/" };

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
        registry.addInterceptor( verifyInterceptorBean() )
            .excludePathPatterns("/static/**", "/webjars/**", "/verify");
     
    }
    
    @Bean
    public VerifyInterceptor verifyInterceptorBean(){
        return new VerifyInterceptor(userUtilsBean());
    }
    
    @Bean
    public UserUtils userUtilsBean(){
        return new UserUtils();
    }
}
