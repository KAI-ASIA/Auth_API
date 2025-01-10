package com.kaiasia.app.service.Auth_api.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "kafka")
@Getter
@Setter
public class KafkaLoadConfig {
    private String bootsTrapServer;
    private String pollSize;
    private String clientId;
    private String usernameACL;
    private String pwACL;
    private String instance;
    private boolean authentication;
}
