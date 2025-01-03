package com.kaiasia.app.service.Auth_api.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class Auth2Request {
    @NotBlank(message = "sessionId type is required")
    private String sessionId;

    @NotBlank(message = "username type is required")
    private String username;

    @NotBlank(message = "gmail type is required")
    private String gmail;

    @NotBlank(message = "transTime type is required")
    private String transTime;

    @NotBlank(message = "transId type is required")
    private String transId;

    @NotBlank(message = "tempId type is required")
    private String tempId;

    private String content;
}
