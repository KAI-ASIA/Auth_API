package com.kaiasia.app.service.Auth_api.api.api_login;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.kaiasia.app.core.model.*;
import com.kaiasia.app.core.utils.GetErrorUtils;
import com.kaiasia.app.register.KaiMethod;
import com.kaiasia.app.register.KaiService;
import com.kaiasia.app.register.Register;
import com.kaiasia.app.service.Auth_api.config.ApiConfig;
import com.kaiasia.app.service.Auth_api.utils.ConvertApiHelper;
import com.kaiasia.app.service.Auth_api.utils.JsonAndObjectUtils;
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

    private static final JsonAndObjectUtils jsonAndObjectUtils = new JsonAndObjectUtils();

    @KaiMethod(name = "login",type = Register.VALIDATE)
    public ApiError validate(ApiRequest res){

        ApiError apiError = new ApiError(ApiError.OK_CODE,ApiError.OK_DESC);
        return apiError;
    }

    @KaiMethod(name = "login")
    public ApiResponse process(ApiRequest res) throws JsonProcessingException {
        String LOCATION = "hello ";
        ApiResponse apiResponse = new ApiResponse();
        ApiBody apiBody = new ApiBody();

        ApiRequest apiRequest = convertApiHelper.convertApi(res, ApiConfig.t24Utils,LOCATION);
        if(apiRequest == null){

            ApiError apiError = apiErrorUtils.getError("702",new String[]{ApiConfig.t24Utils});
            apiResponse.setError(apiError);
        }
        log.info("a = " + JsonAndObjectUtils.objectToJson(apiRequest));

        apiResponse.setBody(apiBody);
        return apiResponse;
    }



}


