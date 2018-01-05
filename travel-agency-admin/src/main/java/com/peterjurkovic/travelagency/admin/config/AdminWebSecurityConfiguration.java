package com.peterjurkovic.travelagency.admin.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.peterjurkovic.travelagency.admin.user.MongoAdminUserDetialsService;
import com.peterjurkovic.travelagency.common.repository.AdminUserRepository;

@Configuration
@ComponentScan("com.peterjurkovic.travelagency.common")
@EnableWebSecurity
public class AdminWebSecurityConfiguration extends WebSecurityConfigurerAdapter{

    @Autowired
    private AdminUserRepository adminUserRepository;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable()
            .authorizeRequests()
//                .antMatchers("/login**").permitAll()
                .anyRequest().authenticated()
                .and()
            .formLogin()
                .loginPage("/login")
                .failureUrl("/login?error=1")
                .loginProcessingUrl("/login.html")
                .defaultSuccessUrl("/")
                .permitAll()
                .and()
            .logout()
                .logoutUrl( "/logout" )
                .logoutSuccessUrl("/login?logout=1")
                .permitAll();
    }
    
    @Override
    public void configure(WebSecurity web) throws Exception {
         web.ignoring().antMatchers("/static/**", "/webjars/**");
    }
    
    @Bean
    @Primary
    public UserDetailsService userDetailServiceBean(){
        return new MongoAdminUserDetialsService(adminUserRepository);
    }
    
    @Bean
    public PasswordEncoder passwordEncodeBean() {
        return new BCryptPasswordEncoder();
    }
    
    
}
