package com.kaiasia.app.service.Auth_api.api.login;

import java.util.Date;

import com.kaiasia.app.core.utils.ApiConstant;
import com.kaiasia.app.service.Auth_api.dto.LoginResponse;
import com.kaiasia.app.service.Auth_api.model.AuthSessionRequest;
import com.kaiasia.app.service.Auth_api.utils.ApiUtils;
import ms.apiclient.model.ApiBody;
import ms.apiclient.model.ApiError;
import ms.apiclient.model.ApiRequest;
import ms.apiclient.model.ApiResponse;
import ms.apiclient.t24util.T24LoginResponse;
import ms.apiclient.t24util.T24Request;
import ms.apiclient.t24util.T24UtilClient;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kaiasia.app.core.job.BaseService;
import com.kaiasia.app.core.job.Enquiry;
import com.kaiasia.app.core.utils.GetErrorUtils;
import com.kaiasia.app.register.KaiMethod;
import com.kaiasia.app.register.KaiService;
import com.kaiasia.app.register.Register;
import com.kaiasia.app.service.Auth_api.config.ApiConfig;
import com.kaiasia.app.service.Auth_api.dao.SessionIdDAO;
import com.kaiasia.app.service.Auth_api.utils.ConvertApiHelper;
import com.kaiasia.app.service.Auth_api.utils.SessionUtil;

import lombok.extern.slf4j.Slf4j;

@KaiService
@Slf4j
public class LoginService  extends BaseService{
	
    @Autowired
    private  GetErrorUtils apiErrorUtils;

    @Autowired
    private T24UtilClient t24UtilClient;

    @Autowired
    private ConvertApiHelper convertApiHelper;

    @Autowired
    private ApiConfig apiConfig;

    @Autowired
    private SessionUtil sessionUtil;

    @Autowired
    private SessionIdDAO sessionIdDAO;

    
    @Autowired
    private ObjectMapper objectMapper;

    @KaiMethod(name = "login",type = Register.VALIDATE)
    public ApiError validate(ApiRequest req) throws Exception {
        String LOCATION = "";


        Enquiry enquiry = objectMapper.convertValue(getEnquiry(req), Enquiry.class);
        if(StringUtils.isBlank(enquiry.getUsername())){
            return apiErrorUtils.getError("706", new String[]{"#userName"});
        }
        if(StringUtils.isBlank(enquiry.getPassword())){
            return apiErrorUtils.getError("706", new String[]{"#password"});
        }

        return new ApiError(ApiError.OK_CODE, ApiError.OK_DESC);

    }

    
     
    
    @KaiMethod(name = "login")
    public ApiResponse process(ApiRequest req) throws Exception {
    	Enquiry enquiry = objectMapper.convertValue(getEnquiry(req), Enquiry.class);
        String LOCATION = req.getHeader().getChannel() + "-" + enquiry.getUsername() + "-" + enquiry.getLoginTime();
        Long a = System.currentTimeMillis();
        log.info(LOCATION + "#BEGIN");

        ApiResponse apiResponse = new ApiResponse();

        T24Request t24Req = new T24Request();
        t24Req.setAuthenType("KAI.API.AUTHEN.GET.LOGIN");
        t24Req.setUsername(enquiry.getUsername());
        t24Req.setPassword(enquiry.getPassword());

        T24LoginResponse loginResponse =  t24UtilClient.login(LOCATION, t24Req, ApiUtils.buildApiHeader(req.getHeader()));

        if(!ApiError.OK_CODE.equals(loginResponse.getError().getCode())){
            ApiError apiError = new ApiError(loginResponse.getError().getCode(), loginResponse.getError().getDesc());
            apiResponse.setError(apiError);
            log.info(LOCATION + "#END#Duration:" + (System.currentTimeMillis() - a));
            return apiResponse;
        }
        // tạo sessionId
       try {
           String customerId = loginResponse.getCustomerID();
           Date startTime = new Date();
           Date endTime = new Date(startTime.getTime() + SessionUtil.timeoutSession * 1000);
           String sessionID = sessionUtil.createCustomerSessionId(customerId);
           AuthSessionRequest sessionRequest = AuthSessionRequest.builder()
                   .sessionId(sessionID)
                   .startTime(startTime)
                   .endTime(endTime)
                   .channel(req.getHeader().getChannel())
                   .phone(loginResponse.getPhone())
                   .customerId(customerId)
                   .companyCode(loginResponse.getCompanyCode())
                   .location(req.getHeader().getLocation())
                   .username(loginResponse.getUsername())
                   .build();

           int result = sessionIdDAO.insertSessionId(sessionRequest);
//           int result = 1;

           if(result == 0){
               ApiError apiError = apiErrorUtils.getError("800");
               apiResponse.setError(apiError);
               log.info(LOCATION + "#END#Duration:" + (System.currentTimeMillis() - a));
               return apiResponse;
           }

           LoginResponse response = new LoginResponse();
           response.setTransId(sessionID);
           response.setResponseCode("00");
           response.setSessionId(sessionID);
           response.setPackageUser(loginResponse.getPackageUser());
           response.setPhone(loginResponse.getPhone());
           response.setCustomerID(loginResponse.getCustomerID());
           response.setCustomerName(loginResponse.getCustomerName());
           response.setUsername(loginResponse.getUsername());
           ApiBody apiBody = new ApiBody();
           apiBody.put(ApiConstant.COMMAND.ENQUIRY, response);
           apiResponse.setBody(apiBody);
       }catch (Exception e){
           log.error("{}:{}",LOCATION,e.getMessage());
            ApiError apiError = apiErrorUtils.getError(ApiConstant.ErrorCode.INTERNAL_SERVER_ERROR, new String[] {e.getMessage()});
            apiResponse.setError(apiError);
       }
        log.info(LOCATION + "#END#Duration:" + (System.currentTimeMillis() - a));
        return apiResponse;
    }



}


