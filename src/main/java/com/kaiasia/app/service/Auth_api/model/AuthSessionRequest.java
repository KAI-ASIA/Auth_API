package com.kaiasia.app.service.Auth_api.model;


import lombok.*;

import java.time.LocalDateTime;
import java.util.Date;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AuthSessionRequest {
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
