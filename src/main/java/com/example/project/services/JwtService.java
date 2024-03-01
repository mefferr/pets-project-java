package com.example.project.services;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.sql.Date;
import java.time.Duration;
import java.util.UUID;
import java.util.logging.Logger;

@Component
public final class JwtService {
    private final Algorithm algorithm;
    private final JWTVerifier jwtVerifier;
    private final Duration expiresDuration;
    private final String issuer;

    @Autowired
    public JwtService(
            @Value("${auth.token.secret}") String secret,
            @Value("${auth.token.issuer}") String issuer,
            @Value("${auth.token.expires-after}") Duration expiresDuration
    ) {
        this.issuer = issuer;
        this.algorithm = Algorithm.HMAC256(secret);
        this.expiresDuration = expiresDuration;
        this.jwtVerifier = JWT.require(algorithm).withIssuer(issuer).build();
    }

    public String tokenize(String claimName, String value) {
        return JWT.create()
                .withIssuer(issuer)
                .withSubject("auth_token")
                .withClaim(claimName, value)
                .withIssuedAt(new Date(System.currentTimeMillis()))
                .withExpiresAt(new Date(System.currentTimeMillis() + expiresDuration.toMillis()))
                .withJWTId(UUID.randomUUID().toString())
                .sign(algorithm);
    }

    public DecodedJWT decode(String jwt) {
        try {
            return jwtVerifier.verify(jwt);
        } catch (Throwable throwable) {
            return null;
        }
    }
}

