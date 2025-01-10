package com.kaiasia.app.service.Auth_api.dto;

import lombok.Data;

@Data
public class GetOTPResponse {

    private String responseCode;
    private String transId;
}
