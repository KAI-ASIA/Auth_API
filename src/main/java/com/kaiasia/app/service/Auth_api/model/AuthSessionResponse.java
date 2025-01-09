package com.kaiasia.app.service.Auth_api.model;

import lombok.*;

import java.util.Date;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AuthSessionResponse {
    private String username;
    private Date startTime;
    private Date endTime;
    private String sessionId;
    private String channel;
    private String location;
    private String phone;
    private String email;
    private String companyCode;
    private String customerId;
}
