package com.onlineSeller.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .authorizeRequests()
            .antMatchers("/status","/saveUser","/doLogin","/saveProfile").permitAll() // Open endpoints
            .anyRequest().authenticated() // Secure all other endpoints
            .and()
            .csrf().disable();  // Disable CSRF protection if not needed for your use case

        return http.build();
    }
}

  
