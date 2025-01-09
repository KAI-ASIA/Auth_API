package com.kaiasia.app.service.Auth_api.api.resetpassword;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.kaiasia.app.core.job.Enquiry;
import com.kaiasia.app.core.model.ApiError;
import com.kaiasia.app.core.model.ApiRequest;
import com.kaiasia.app.core.utils.GetErrorUtils;
import com.kaiasia.app.register.KaiMethod;
import com.kaiasia.app.register.KaiService;
import lombok.extern.slf4j.Slf4j;
import ms.apiclient.t24util.T24UtilClient;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashMap;

@KaiService
@Slf4j
public class ResetPasswordRequestService {

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private GetErrorUtils apiErrorUtils;

    @Autowired
    private T24UtilClient t24UtilClient;

    @KaiMethod
    public ApiError validate(ApiRequest req) throws Exception {

        HashMap enquiry = (HashMap) req.getBody().get("enquiry");
        String channel = req.getHeader().getChannel();
        String username = (String) enquiry.get("username");
        String transId = (String) enquiry.get("transId");
        long time = System.currentTimeMillis();
        String location = channel +"-"+ username +"-"+ transId +"-"+ time;
        
        if (req.getBody() == null) {
            log.info("#BODY NULL" + location);
            return apiErrorUtils.getError("804", new String[]{"Missing request body!"});
        }
        if (enquiry == null) {
            log.info("#ENQUIRY NULL" + location);
            return apiErrorUtils.getError("804", new String[]{"Missing enquiry part!"});
        }

        if(StringUtils.isBlank(username)){
            log.info("#FIELD NAME NULL" + location);
            return apiErrorUtils.getError("804", new String[]{"Missing field name !"});
        }
        if(StringUtils.isBlank(transId)){
            log.info("#FIELD TRANSID NULL" + location);
            return apiErrorUtils.getError("804", new String[]{"Missing field transId !"});
        }

        return new ApiError(ApiError.OK_CODE, ApiError.OK_DESC);
    }
}
