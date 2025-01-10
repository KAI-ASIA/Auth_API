package com.kaiasia.app.core.job;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Setter;

import lombok.Getter;

@JsonIgnoreProperties(ignoreUnknown = true)
@Getter
@Setter
public class Enquiry {
    private String username;
    private String authenType;
    private String password;
    private String loginTime;
    private String accountId;

    private String sessionId;

}
