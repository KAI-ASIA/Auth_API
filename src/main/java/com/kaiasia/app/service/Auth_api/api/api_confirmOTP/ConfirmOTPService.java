package com.kaiasia.app.service.Auth_api.api.api_confirmOTP;

import com.kaiasia.app.core.model.ApiBody;
import com.kaiasia.app.core.model.ApiError;
import com.kaiasia.app.core.model.ApiRequest;
import com.kaiasia.app.core.model.ApiResponse;
import com.kaiasia.app.core.utils.GetErrorUtils;
import com.kaiasia.app.register.KaiMethod;
import com.kaiasia.app.register.KaiService;
import com.kaiasia.app.register.Register;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashMap;
import java.util.Map;

@KaiService
@Slf4j
public class ConfirmOTPService {

    @Autowired
    GetErrorUtils apiErrorUtils;


    @KaiMethod(name = "confirmOTP", type = Register.VALIDATE)
    public ApiError validate(ApiRequest req) {
        if (req.getBody() == null) {
            return apiErrorUtils.getError("404", new String[]{"Missing request body!"});
        }
        HashMap enquiry = (HashMap) req.getBody().get("enquiry");
        if (enquiry == null) {
            return apiErrorUtils.getError("404", new String[]{"Missing enquiry part!"});
        }
        System.out.println("Bat dau in");
        System.out.println(enquiry);

        String[] requiredFields = new String[]{
                "sessionId", "username", "otp",
                "transTime", "transId"
        };

        for (String requiredField : requiredFields) {
            if (!enquiry.containsKey(requiredField)
                    || StringUtils.isEmpty((String) enquiry.get(requiredField))) {
                return apiErrorUtils.getError("404", new String[]{requiredFields + " is required!"});
            }
        }
        return new ApiError(ApiError.OK_CODE, ApiError.OK_DESC);
    }

    @KaiMethod(name = "confirmOTP")
    public ApiResponse process(ApiRequest req) {
        ApiResponse apiResponse = new ApiResponse();
        ApiBody apiBody = new ApiBody();
        try {
            Map<String, Object> bodyEnq = new HashMap<>();

            apiResponse.setBody(apiBody);
            return apiResponse;
        } catch (Exception e) {
            log.error("Exception", e);
            ApiError apiError = apiErrorUtils.getError("999", new String[]{e.getMessage()});
            apiResponse.setError(apiError);
            return apiResponse;
        }
    }
}