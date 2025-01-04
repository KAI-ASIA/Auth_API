package com.kaiasia.app.service;

import java.util.Date;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kaiasia.app.core.job.BaseService;
import com.kaiasia.app.core.job.Enquiry;
import com.kaiasia.app.core.model.ApiBody;
import com.kaiasia.app.core.model.ApiError;
import com.kaiasia.app.core.model.ApiRequest;
import com.kaiasia.app.core.model.ApiResponse;
import com.kaiasia.app.core.model.T24Request;
import com.kaiasia.app.core.utils.GetErrorUtils;
import com.kaiasia.app.register.KaiMethod;
import com.kaiasia.app.register.KaiService;
import com.kaiasia.app.register.Register;
import com.kaiasia.app.service.Auth_api.config.ApiConfig;
import com.kaiasia.app.service.Auth_api.config.ApiProperties;
import com.kaiasia.app.service.Auth_api.dao.SessionIdDAO;
import com.kaiasia.app.service.Auth_api.model.AuthSessionRequest;
import com.kaiasia.app.service.Auth_api.utils.CallApiHelper;
import com.kaiasia.app.service.Auth_api.utils.ConvertApiHelper;
import com.kaiasia.app.service.Auth_api.utils.LoginResult;
import com.kaiasia.app.service.Auth_api.utils.SessionUtil;

import lombok.extern.slf4j.Slf4j;

@KaiService
@Slf4j
public class LoginService  extends BaseService{
	
    @Autowired
    private  GetErrorUtils apiErrorUtils;

    @Autowired
    private CallApiHelper  callApiHelper;

    @Autowired
    private ConvertApiHelper convertApiHelper;

    @Autowired
    private ApiConfig apiConfig;

    @Autowired
    private SessionUtil sessionUtil;

    @Autowired
    private SessionIdDAO sessionIdDAO;

//    private  JsonAndObjectUtils jsonAndObjectUtils = new JsonAndObjectUtils();
    
    @Autowired
    private ObjectMapper objectMapper;

    @KaiMethod(name = "login",type = Register.VALIDATE)
    public ApiError validate(ApiRequest req) throws Exception {
        String LOCATION = "";
//        ApiError err = new ApiError();
//        err.setCode(ApiError.OK_CODE);

//        LinkedHashMap enquiry = (LinkedHashMap)req.getBody().get("enquiry");
//        if(enquiry == null){
//            err = apiErrorUtils.getError("703");
//        }
//        String[] requiredFields = new String[]{"username","password"};
//
//        for(String requiredField : requiredFields){
//            if(!enquiry.containsKey(requiredField) || StringUtils.isEmpty((String) enquiry.get(requiredField))){
//                err = apiErrorUtils.getError("706",new String[]{"#" + requiredField});
//            }
//        }

        Enquiry enquiry = objectMapper.convertValue(getEnquiry(req), Enquiry.class);
        if(StringUtils.isBlank(enquiry.getUserName())){
            return apiErrorUtils.getError("703", new String[]{"userName"});
        }
        /*
         * todo required fields
         */
        return new ApiError(ApiError.OK_CODE, ApiError.OK_DESC);
//        return err;
    }

    
     
    
    @KaiMethod(name = "login")
    public ApiResponse process(ApiRequest req) throws Exception {
    	Enquiry enquiry = objectMapper.convertValue(getEnquiry(req), Enquiry.class);
        String LOCATION = req.getHeader().getChannel() + "-" + enquiry.getUserName() + "-" + enquiry.getLoginTime();
        Long a = System.currentTimeMillis();

        ApiResponse apiResponse = new ApiResponse();
        apiResponse.setHeader(req.getHeader());
        ApiBody apiBody = new ApiBody();


        ApiProperties apiProperties = apiConfig.getApi(ApiConfig.t24Utils);

//        ApiRequest apiT24Req = convertApiHelper.convertApi(req,apiProperties,LOCATION);
//        if(apiT24Req == null){
//
//            ApiError apiError = apiErrorUtils.getError("702",new String[]{ApiConfig.t24Utils});
//            apiResponse.setError(apiError);
//        }

        
//        String t24apiSTring = jsonAndObjectUtils.objectToJson(apiT24Req);

        // login bằng api t24
        
//        <authenType>login</authenType>
//        <username>158963500</username>
//        <password>H65!xfPc1</password>

        T24Request t24Req = new T24Request();
        t24Req.setAuthenType("login");
        t24Req.setUsername(enquiry.getUserName());
        //todo setPasword
        
        ApiRequest reqLogin = buildENQUIRY(t24Req, req.getApiHeader());
        LoginResult responseT24  = callApiHelper.commonRest(LOCATION, reqLogin, LoginResult.class);
        if(!ApiError.OK_CODE.equals(responseT24.getError().getCode()){
        	 apiResponse.setError(responseT24.getError());
             System.out.println(apiResponse);
             return apiResponse;
        }
        //apiReq
//        log.info("Response: " + apiT24);
//        if(apiT24Req == null){
//            ApiError apiError = apiErrorUtils.getError("999",new String[]{ApiConfig.t24Utils});
//            apiResponse.setError(apiError);
//            System.out.println(apiResponse);
//            return apiResponse;
//        }
        // lấy body response từ t24Api trả về response auth_login
//        LinkedHashMap enquiryResponse  = (LinkedHashMap) apiT24.getBody().get("enquiry");
        apiBody.put("enquiry",responseT24);

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


