package com.kaiasia.app.service.Auth_api.config;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ApiProperties {
    private String apiName;
    private String url;
    private long timeout;
    private String apiKey;
    private String authenType;

}