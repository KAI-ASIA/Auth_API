package com.kaiasia.app.service.Auth_api.dto;

import lombok.Data;

@Data
public class LoginResponse {

    private String transId;
    private String sessionId;
    private String responseCode;
    private String packageUser;
    private String phone;
    private String customerID;
    private String customerName;
    private String companyCode;
    private String username;

}
