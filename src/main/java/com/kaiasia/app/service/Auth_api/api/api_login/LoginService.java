package com.kaiasia.app.service.Auth_api.api.api_login;

import com.kaiasia.app.core.model.ApiError;
import com.kaiasia.app.core.model.ApiRequest;
import com.kaiasia.app.core.utils.GetErrorUtils;
import com.kaiasia.app.register.KaiMethod;
import com.kaiasia.app.register.KaiService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

@KaiService
@Slf4j
public class LoginService {
    @Autowired
    GetErrorUtils apiErrorUtils;

    @KaiMethod(name = "LoginService")
    public ApiError validate(ApiRequest res){
        ApiError apiError = new ApiError();



        return apiError;
    }

}
