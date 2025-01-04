package com.kaiasia.app.service.Auth_api.api.api_login;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.kaiasia.app.core.model.*;
import com.kaiasia.app.core.utils.GetErrorUtils;
import com.kaiasia.app.register.KaiMethod;
import com.kaiasia.app.register.KaiService;
import com.kaiasia.app.register.Register;
import com.kaiasia.app.service.Auth_api.config.ApiConfig;
import com.kaiasia.app.service.Auth_api.config.ApiProperties;
import com.kaiasia.app.service.Auth_api.dao.SessionIdDAO;
import com.kaiasia.app.service.Auth_api.model.AuthSessionRequest;
import com.kaiasia.app.service.Auth_api.utils.*;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.LinkedHashMap;

@KaiService
@Slf4j
public class LoginService {
    @Autowired
    GetErrorUtils apiErrorUtils;

    @Autowired
    CallApiHelper<ApiResponse> callApiHelper;

    @Autowired
    ConvertApiHelper convertApiHelper;

    @Autowired
    ApiConfig apiConfig;

    @Autowired
    SessionUtil sessionUtil;

    @Autowired
    SessionIdDAO sessionIdDAO;

    private  JsonAndObjectUtils jsonAndObjectUtils = new JsonAndObjectUtils();

    @KaiMethod(name = "login",type = Register.VALIDATE)
    public ApiError validate(ApiRequest req){
        String LOCATION = "";
        ApiError err = new ApiError();
        err.setCode(ApiError.OK_CODE);

        LinkedHashMap enquiry = (LinkedHashMap)req.getBody().get("enquiry");
        if(enquiry == null){
            err = apiErrorUtils.getError("703");
        }
        String[] requiredFields = new String[]{"username","password"};

        for(String requiredField : requiredFields){
            if(!enquiry.containsKey(requiredField) || StringUtils.isEmpty((String) enquiry.get(requiredField))){
                err = apiErrorUtils.getError("706",new String[]{"#" + requiredField});
            }
        }

        return err;
    }

    @KaiMethod(name = "login")
    public ApiResponse process(ApiRequest res) throws JsonProcessingException {
        String LOCATION = "";
        Long a = System.currentTimeMillis();

        ApiResponse apiResponse = new ApiResponse();
        apiResponse.setHeader(res.getHeader());
        ApiBody apiBody = new ApiBody();


        ApiProperties apiProperties = apiConfig.getApi(ApiConfig.t24Utils);

        ApiRequest apiT24Req = convertApiHelper.convertApi(res,apiProperties,LOCATION);
        if(apiT24Req == null){

            ApiError apiError = apiErrorUtils.getError("702",new String[]{ApiConfig.t24Utils});
            apiResponse.setError(apiError);
        }

        String t24apiSTring = jsonAndObjectUtils.objectToJson(apiT24Req);

        // login bằng api t24
        ApiResponse apiT24  = callApiHelper.call(apiProperties.getUrl(), HttpMethod.POST,t24apiSTring, ApiResponse.class,null);
        log.info("Response: " + apiT24);
        if(apiT24Req == null){
            ApiError apiError = apiErrorUtils.getError("999",new String[]{ApiConfig.t24Utils});
            apiResponse.setError(apiError);
            System.out.println(apiResponse);
            return apiResponse;
        }
        // lấy body response từ t24Api trả về response auth_login
        LinkedHashMap enquiryResponse  = (LinkedHashMap) apiT24.getBody().get("enquiry");
        apiBody.put("enquiry",enquiryResponse);

        apiResponse.setBody(apiBody);


        // tạo sessionId
       try {
           String customerId = (String)enquiryResponse.get("customerID");
           LOCATION = customerId + "#"+a;
           Date startTime = new Date();
           Date endTime = new Date(startTime.getTime() + SessionUtil.timeoutSession * 1000);
           String sessionID = sessionUtil.createCustomerSessionId(customerId);
           AuthSessionRequest sessionRequest = AuthSessionRequest.builder()
                   .sessionId(sessionID)
                   .startTime(startTime)
                   .endTime(endTime)
                   .channel(apiResponse.getHeader().getChannel())
                   .phone((String) enquiryResponse.get("phone"))
                   .customerId(customerId)
                   .companyCode((String) enquiryResponse.get("companyCode"))
                   .location(LOCATION)
                   .username((String) enquiryResponse.get("username"))
                   .build();

           int result = sessionIdDAO.insertSessionId(sessionRequest);

           if(result == 0){
               ApiError apiError = apiErrorUtils.getError("800");
               apiResponse.setError(apiError);
               return apiResponse;
           }
           log.info("{}{}",LOCATION,"#apiLogin");



       }catch (Exception e){
           log.error("{}:{}",LOCATION,e.getMessage());

       }






        return apiResponse;
    }



}


