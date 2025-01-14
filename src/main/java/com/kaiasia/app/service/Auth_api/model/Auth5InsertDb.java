package com.kaiasia.app.service.Auth_api.model;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Auth5InsertDb {
    private String validateCode;
    private String transId;
    private String username;
    private String channel;
    private LocalDateTime startTime;
    private String location;
    private LocalDateTime endTime;
    private String status;
    private String sessionId;
    private LocalDateTime confirmTime;
    private String transInfo;
    private String transTime;
}
