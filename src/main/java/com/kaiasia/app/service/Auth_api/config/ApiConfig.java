package com.kaiasia.app.service.Auth_api.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

@Setter
@Getter
@Component
public class ApiConfig {
    @Autowired
    Environment env;

    private String t24Utils = "t24Api";
    public ApiProperties getApi(String api_name){
        return ApiProperties.builder()
                .apiKey(env.getProperty(api_name +".apiKey"))
                .apiName(env.getProperty(api_name + ".apiName"))
                .url(env.getProperty(api_name + ".url"))
                .timeout(Long.parseLong(env.getProperty(api_name + ".timeout")))
                .build();
    }
}
