package com.airbnb2.security;

import com.airbnb2.entity.PropertyUserEntity;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.Date;

@Component
public class JwtService {

    @Value("${jwt.algorithm.key}")
    private String algorithmKey;
    @Value("${jwt.expiry.duration}")
    private int expiry;
    @Value("${jwt.issuer.name}")
    private String issuer;
    private Algorithm algorithm;

    private final static String USER_NAME="username";

    @PostConstruct
    public void postConstruct(){
        algorithm = Algorithm.HMAC256(algorithmKey);
    }

    public String generateJwtToken(PropertyUserEntity propertyUser){
        return JWT.create()
                .withClaim(USER_NAME , propertyUser.getUsername())
                .withExpiresAt(new Date(System.currentTimeMillis()+expiry))
                .withIssuer(issuer)
                .sign(algorithm);
    }

    public String decodeToken(String properToken) {
        DecodedJWT decodedJWT = JWT.require(algorithm)
                .withIssuer(issuer)
                .build().verify(properToken);

        return decodedJWT.getClaim(USER_NAME).asString();
    }
}
