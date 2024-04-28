package com.airbnb2.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.intercept.AuthorizationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private JwtFilterRequest jwtFilterRequest;

    public SecurityConfig(JwtFilterRequest jwtFilterRequest) {
        this.jwtFilterRequest = jwtFilterRequest;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        http.csrf().disable().cors().disable();
        http.addFilterBefore(jwtFilterRequest , AuthorizationFilter.class);
        http.authorizeHttpRequests()
                .requestMatchers("/api/v2/users/sign-up", "/api/v2/users/sign-in" , "/api/v2/property/**").permitAll()
                .requestMatchers("/api/v2/users/session","/api/v2/favorite/all-favorite-properties","/api/v2/images/**" , "/s3bucket/**").hasRole("ADMIN")
                .requestMatchers("/api/v2/users/profile").hasAnyRole("ADMIN" , "USER")
                .anyRequest().authenticated();

        return http.build();
    }

}
