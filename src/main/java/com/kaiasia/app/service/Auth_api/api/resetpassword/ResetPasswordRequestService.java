package com.kaiasia.app.service.Auth_api.api.resetpassword;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.kaiasia.app.core.utils.GetErrorUtils;
import com.kaiasia.app.register.KaiMethod;
import com.kaiasia.app.register.KaiService;
import com.kaiasia.app.register.Register;
import com.kaiasia.app.service.Auth_api.model.Auth5Request;
import lombok.extern.slf4j.Slf4j;
import ms.apiclient.model.*;
import ms.apiclient.t24util.T24CustomerInfoResponse;
import ms.apiclient.t24util.T24Request;
import ms.apiclient.t24util.T24UserInfoResponse;
import ms.apiclient.t24util.T24UtilClient;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.Validate;
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

    @KaiMethod(name = "resetPassword" , type = Register.VALIDATE)
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

    @KaiMethod(name = "resetPassword")
    public ApiResponse process(ApiRequest req) throws Exception{
        ApiResponse apiResponse = new ApiResponse();
        ApiBody body = new ApiBody();
        ApiHeader header = req.getHeader();
        apiResponse.setHeader(header);

        Auth5Request auth5Request = objectMapper.convertValue(body,Auth5Request.class);

        String chanel = header.getChannel();
        long time = System.currentTimeMillis();
        String location = time + "-" + chanel + "-" + auth5Request.getUsername();

        log.info(location + "#BEGIN GET INFO");
        T24CustomerInfoResponse infoResponse = t24UtilClient.getCustomerInfo(
                location,
                T24Request
                        .builder()
                        .username(auth5Request.getUsername())
                        .build(),
                req.getHeader()
        );



        return  apiResponse;
    }
}
