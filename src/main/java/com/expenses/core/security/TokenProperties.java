package com.expenses.core.security;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "security.token")
@Getter
@Setter
public class TokenProperties {
    private String secret;
    private long expirationTimeMs;
}
