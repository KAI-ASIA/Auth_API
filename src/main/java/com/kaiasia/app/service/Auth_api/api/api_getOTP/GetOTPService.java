package com.kaiasia.app.service.Auth_api.api.api_getOTP;

import com.kaiasia.app.core.model.ApiBody;
import com.kaiasia.app.core.model.ApiError;
import com.kaiasia.app.core.model.ApiRequest;
import com.kaiasia.app.core.model.ApiResponse;
import com.kaiasia.app.core.utils.GetErrorUtils;
import com.kaiasia.app.register.KaiMethod;
import com.kaiasia.app.register.KaiService;
import com.kaiasia.app.register.Register;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashMap;
import java.util.Map;

@KaiService
@Slf4j
public class GetOTPService {

    @Autowired
    GetErrorUtils apiErrorUtils;

    @KaiMethod(name = "getOTP", type = Register.VALIDATE)
    public ApiError validate(ApiRequest req){

        ApiBody apiBody = req.getBody();
        Object value = apiBody.get("enquiry");
        Map<String, Object> enquiry = (Map<String, Object>) apiBody.get("enquiry");
        if (enquiry == null){
            return apiErrorUtils.getError("804", new String[]{"Missing enquiry part!"});
        }
        String sessionId = (String) enquiry.get("sessionId");
        String username = (String) enquiry.get("username");
        String gmail = (String) enquiry.get("gmail");
        String transTime = (String) enquiry.get("transTime");
        String transId = (String) enquiry.get("transId");
        String tempId = (String) enquiry.get("tempId");

        if (sessionId == null || sessionId.trim().isEmpty()){
            return apiErrorUtils.getError("706", new String[]{"sessionId"});
        }

        if (username == null || username.trim().isEmpty()){
            return apiErrorUtils.getError("706", new String[]{"username"});
        }

        if (gmail == null || gmail.trim().isEmpty()){
            return apiErrorUtils.getError("706", new String[]{"gmail"});
        }

        if (transTime == null || transTime.trim().isEmpty()){
            return apiErrorUtils.getError("706", new String[]{"transTime"});
        }

        if (transId == null || transId.trim().isEmpty()){
            return apiErrorUtils.getError("706", new String[]{"transId"});
        }

        if (tempId == null || tempId.trim().isEmpty()){
            return apiErrorUtils.getError("706", new String[]{"tempId"});
        }

        return new ApiError(ApiError.OK_CODE, ApiError.OK_DESC);
    }

    @KaiMethod(name = "getOTP")
    public ApiResponse process(ApiRequest req){
        ApiResponse apiResponse = new ApiResponse();
        ApiBody apiBody = new ApiBody();
        try {
            Map<String, Object> bodyEnq = new HashMap<>();

            apiResponse.setBody(apiBody);
            return apiResponse;
        } catch (Exception e){
            log.error("Exception", e);
            ApiError apiError = apiErrorUtils.getError("999", new String[]{e.getMessage()});
            apiResponse.setError(apiError);
            return apiResponse;
        }
    }
}
