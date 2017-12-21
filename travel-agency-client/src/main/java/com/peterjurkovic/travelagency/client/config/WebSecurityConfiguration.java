package com.peterjurkovic.travelagency.client.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.peterjurkovic.travelagency.client.user.MongoUserDetialsService;
import com.peterjurkovic.travelagency.common.repository.UserRepository;

@Configuration
@EnableWebSecurity
public class WebSecurityConfiguration extends WebSecurityConfigurerAdapter{

    @Autowired
    private UserRepository userRepository;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable()
            .authorizeRequests()
                .antMatchers("/", "/home", "/trip/*", "/sign-up", "/verify").permitAll()
                .anyRequest().authenticated()
                .and()
            .formLogin()
                .loginPage("/login.html")
                .loginProcessingUrl("/login")
                .permitAll()
                .and()
            .logout()
                .permitAll();
    }
    
    @Override
    public void configure(WebSecurity web) throws Exception {
         web.ignoring().antMatchers("/static/**", "/webjars/**");
    }
    
    @Bean
    @Primary
    public UserDetailsService userDetailServiceBean(){
        return new MongoUserDetialsService(userRepository);
    }
    
    @Bean
    public PasswordEncoder passwordEncodeBean() {
        return new BCryptPasswordEncoder();
    }
    
    
}
