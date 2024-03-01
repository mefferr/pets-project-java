package com.example.project.config;

import lombok.Getter;
import org.springframework.context.annotation.Configuration;

import java.time.Duration;

@Getter
@Configuration
public class TokenConfig {
    private Duration expiresAfter;
    private String issuer;
    private String secret;

    public TokenConfig setExpiresAfter(Duration expiresAfter) {
        this.expiresAfter = expiresAfter;
        return this;
    }

    public TokenConfig setIssuer(String issuer) {
        this.issuer = issuer;
        return this;
    }

    public TokenConfig setSecret(String secret) {
        this.secret = secret;
        return this;
    }
}
