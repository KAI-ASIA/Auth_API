package com.kaiasia.app.service.Auth_api.api.api_getOTP;

import com.kaiasia.app.core.model.ApiBody;
import com.kaiasia.app.core.model.ApiError;
import com.kaiasia.app.core.model.ApiRequest;
import com.kaiasia.app.core.model.ApiResponse;
import com.kaiasia.app.core.utils.ApiConstant;
import com.kaiasia.app.core.utils.GetErrorUtils;
import com.kaiasia.app.register.KaiMethod;
import com.kaiasia.app.register.KaiService;
import com.kaiasia.app.register.Register;
import com.kaiasia.app.service.Auth_api.dao.IAuthOTPDao;
import com.kaiasia.app.service.Auth_api.dto.GetOTPResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashMap;

import static com.kaiasia.app.service.Auth_api.utils.ServiceUltil.takeRespose;

@KaiService
@Slf4j
public class GetOTPService {

    @Autowired
    GetErrorUtils apiErrorUtils;

    @Autowired
    private IAuthOTPDao authOTPService;
    private ApiError apiError;

    @KaiMethod(name = "getOTP", type = Register.VALIDATE)
    public ApiError validate(ApiRequest req) {

        ApiBody apiBody = req.getBody();
        Object value = apiBody.get("enquiry");
        HashMap enquiry = (HashMap) apiBody.get("enquiry");
        if (enquiry == null) {
            return apiErrorUtils.getError("804", new String[]{"Missing enquiry part!"});
        }
        String sessionId = (String) enquiry.get("sessionId");
        String username = (String) enquiry.get("username");
        String gmail = (String) enquiry.get("gmail");
        String transTime = (String) enquiry.get("transTime");
        String transId = (String) enquiry.get("transId");
        String tempId = (String) enquiry.get("tempId");

        if (sessionId == null || sessionId.trim().isEmpty()) {
            return apiErrorUtils.getError("706", new String[]{"sessionId"});
        }

        if (username == null || username.trim().isEmpty()) {
            return apiErrorUtils.getError("706", new String[]{"username"});
        }

        if (gmail == null || gmail.trim().isEmpty()) {
            return apiErrorUtils.getError("706", new String[]{"gmail"});
        }

        if (transTime == null || transTime.trim().isEmpty()) {
            return apiErrorUtils.getError("706", new String[]{"transTime"});
        }

        if (transId == null || transId.trim().isEmpty()) {
            return apiErrorUtils.getError("706", new String[]{"transId"});
        }

        if (tempId == null || tempId.trim().isEmpty()) {
            return apiErrorUtils.getError("706", new String[]{"tempId"});
        }

        return new ApiError(ApiError.OK_CODE, ApiError.OK_DESC);
    }

    @KaiMethod(name = "getOTP")
    public ApiResponse process(ApiRequest req) {
        HashMap enquiry = (HashMap) req.getBody().get("enquiry");
        Long a = System.currentTimeMillis();
        String LOCATION = "GetOTP" + enquiry.get("sessionId");

        log.info(LOCATION + "#BEGIN");

        ApiResponse apiResponse = new ApiResponse();



        try {
            String generateOTP = authOTPService.generateAndSaveOTP(enquiry);

            boolean isOTPValid = authOTPService.compareOTPAndCheckExpiration(enquiry);

            if (!isOTPValid) {
                ApiError apiError = apiErrorUtils.getError("605", new String[]{"Wrong OTP!"});
                apiResponse.setError(apiError);
                log.info(LOCATION + "#END#Duration:" + (System.currentTimeMillis() - a));
                return apiResponse;
            }
            GetOTPResponse response = new GetOTPResponse();
            response.setResponseCode("00");
            response.setTransId(enquiry.get("transId").toString());
            ApiBody apiBody = new ApiBody();
            apiBody.put(ApiConstant.COMMAND.ENQUIRY, response);
            apiResponse.setBody(apiBody);

        } catch (Exception e) {
            log.error("{}:{}",LOCATION,e.getMessage());
            ApiError apiError = apiErrorUtils.getError(ApiConstant.ErrorCode.INTERNAL_SERVER_ERROR, new String[] {e.getMessage()});
            apiResponse.setError(apiError);
        }
        log.info(LOCATION + "#END#Duration:" + (System.currentTimeMillis() - a));
        return apiResponse;

    }
}
