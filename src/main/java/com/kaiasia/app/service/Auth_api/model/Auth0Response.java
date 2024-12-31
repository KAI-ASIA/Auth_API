package com.kaiasia.app.service.Auth_api.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
public class Auth0Response {
    private String packageUser;
    private String phone;
    private String customerID;
    private String companyCode;
    private String username;

}
