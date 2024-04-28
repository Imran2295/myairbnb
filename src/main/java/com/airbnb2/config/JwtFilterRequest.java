package com.airbnb2.config;

import com.airbnb2.entity.PropertyUserEntity;
import com.airbnb2.repository.PropertyUserRepository;
import com.airbnb2.security.JwtService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Optional;

@Component
public class JwtFilterRequest extends OncePerRequestFilter {

    private JwtService jwtService;
    private PropertyUserRepository userRepository;

    public JwtFilterRequest(JwtService jwtService, PropertyUserRepository userRepository) {
        this.jwtService = jwtService;
        this.userRepository = userRepository;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String token = request.getHeader("Authorization");
        if(token != null && token.startsWith("Bearer ")){
            String properToken = token.substring(8, token.length() - 1);

            String username = jwtService.decodeToken(properToken);
            Optional<PropertyUserEntity> user = userRepository.findByUsername(username);
            if(user.isPresent()){
                PropertyUserEntity propertyUser = user.get();

                UsernamePasswordAuthenticationToken authenticationToken =
                        new UsernamePasswordAuthenticationToken(propertyUser , null , Collections.singleton(new SimpleGrantedAuthority(propertyUser.getRole())));
                authenticationToken.setDetails(new WebAuthenticationDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authenticationToken);
            }
        }
        filterChain.doFilter(request , response);
    }
}
