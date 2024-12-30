package com.kaiasia.app.service.Auth_api.config;

import com.kaiasia.app.service.Auth_api.utils.ConvertApiHelper;
import lombok.Getter;
import lombok.Setter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import java.util.Objects;

@Setter
@Getter
@Component
public class ApiConfig {
    @Autowired
    Environment env;

    private static final Logger log = LoggerFactory.getLogger(ApiConfig.class);

    public static String t24Utils = "t24Api";

    public ApiProperties getApi(String api_name) {
        String apiKey = env.getProperty("api."+api_name + ".apiKey");
        String apiName = env.getProperty("api."+api_name + ".apiName");
        String url = env.getProperty("api."+api_name + ".url");
        String timeoutString = env.getProperty("api."+api_name + ".timeout");

        // Kiểm tra giá trị null trước khi tiếp tục
        if (apiKey == null || apiName == null || url == null || timeoutString == null) {
            log.error("Missing configuration for API: {}", api_name);
            return null;
        }

        long timeout;
        try {
            timeout = Long.parseLong(timeoutString);
        } catch (NumberFormatException e) {
            log.error("Invalid timeout value for API: {}", api_name, e);
            return null;
        }

        return ApiProperties.builder()
                .apiKey(apiKey)
                .apiName(apiName)
                .url(url)
                .timeout(timeout)
                .build();
    }
}
