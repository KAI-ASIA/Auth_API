package com.kaiasia.app.service.Auth_api.dto;


import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SessionResponse {
    private String responseCode;
    private String sessionId;
    private String username;


}
