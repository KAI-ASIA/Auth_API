package com.kaiasia.app.service.Auth_api.api.api_login;

import com.kaiasia.app.core.model.*;
import com.kaiasia.app.core.utils.GetErrorUtils;
import com.kaiasia.app.register.KaiMethod;
import com.kaiasia.app.register.KaiService;
import com.kaiasia.app.register.Register;
import com.kaiasia.app.service.Auth_api.utils.ConvertApiHelper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashMap;
import java.util.Map;

@KaiService
@Slf4j
public class LoginService {
    @Autowired
    GetErrorUtils apiErrorUtils;

    @Autowired
    ConvertApiHelper convertApiHelper;


    @KaiMethod(name = "LoginService",type = Register.VALIDATE)
    public ApiError validate(ApiRequest res){

        ApiError apiError = new ApiError(ApiError.OK_CODE,ApiError.OK_DESC);
        return apiError;
    }

    @KaiMethod(name = "LoginService")
    public ApiResponse process(ApiRequest res){
        String location = "";
        ApiResponse apiResponse = new ApiResponse();

        ApiBody apiBody = new ApiBody();
        try {
            Map<String, Object> bodyEnq = new HashMap<>();

        } catch (Exception e){

            log.error(location , e);
            ApiError apiError = apiErrorUtils.getError("999", new String[]{e.getMessage()});

        }
        return apiResponse;
    }



}


