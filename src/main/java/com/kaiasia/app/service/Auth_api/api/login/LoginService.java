package com.kaiasia.app.service.Auth_api.api.login;

import java.util.Map;

import com.kaiasia.app.core.model.*;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kaiasia.app.core.job.BaseService;
import com.kaiasia.app.core.job.Enquiry;
import com.kaiasia.app.core.utils.GetErrorUtils;
import com.kaiasia.app.register.KaiMethod;
import com.kaiasia.app.register.KaiService;
import com.kaiasia.app.register.Register;
import com.kaiasia.app.service.Auth_api.dao.SessionIdDAO;
import com.kaiasia.app.service.Auth_api.utils.LoginResult;
import com.kaiasia.app.service.Auth_api.utils.SessionUtil;
import com.kaiasia.app.service.Auth_api.utils.T24UtilClient;

import lombok.extern.slf4j.Slf4j;

@KaiService
@Slf4j
public class LoginService  extends BaseService{
	
    @Autowired
    private  GetErrorUtils apiErrorUtils;

    @Autowired
    private T24UtilClient  t24UtilClient;

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
            return apiErrorUtils.getError("703", new String[]{"#userName"});
        }
        if(StringUtils.isBlank(enquiry.getPassword())){
            return apiErrorUtils.getError("703", new String[]{"#password"});
        }

        return new ApiError(ApiError.OK_CODE, ApiError.OK_DESC);

    }

    
     
    
    @KaiMethod(name = "login")
    public ApiResponse process(ApiRequest req) throws Exception {
    	Enquiry enquiry = objectMapper.convertValue(getEnquiry(req), Enquiry.class);
        String LOCATION = req.getHeader().getChannel() + "-" + enquiry.getUsername() + "-" + enquiry.getLoginTime();
        Long a = System.currentTimeMillis();

        ApiResponse apiResponse = new ApiResponse();
        ApiBody apiBody = new ApiBody();

//        Map<String, Object> bodyEnq = new HashMap<>();
//
//        apiResponse.setBody(apiBody);
//        return apiResponse;

        T24Request t24Req = new T24Request();
        t24Req.setAuthenType("KAI.API.AUTHEN.GET.LOGIN");
        t24Req.setUsername(enquiry.getUsername());
        t24Req.setPassword(enquiry.getPassword());
        Map<String, Object> t24map = objectMapper.convertValue(t24Req, Map.class);
        ApiRequest reqLogin = buildENQUIRY(t24map, req.getHeader());
        ApiBody apiBody1 = new ApiBody();

        String jsonString = "{"
                + "\"transId\": \"AuthenAPI-HA2289-3333\","
                + "\"responseCode\": \"00\","
                + "\"sessionId\": \"158963500-20161118110507-1479441907619\","
                + "\"packageUser\": \"SUPPER\","
                + "\"phone\": \"0986011399\","
                + "\"customerID\": \"1589635\","
                + "\"customerName\": \"VU VAN TUAN\","
                + "\"companyCode\": \"VN0010002\","
                + "\"username\": \"158963500\""
                + "}";
        apiBody1.put("enquiry",jsonString);

//        LoginResult responseT24  = t24UtilClient.callLogin(LOCATION, reqLogin);
        LoginResult responseT24 = LoginResult.builder()
                .apiHeader(req.getHeader())
                .body(apiBody1)
                .build();
        System.out.println("a : "+ responseT24.getBody());
//        Map<String, Object> bodyres = objectMapper.convertValue(responseT24.getBody(), Map.class);
//        apiBody.putAll(bodyres);
        apiResponse.setBody(responseT24.getBody());

        System.out.println("response : " + apiResponse);
        log.debug("T24 Login Response: {}", responseT24);


        return apiResponse;
        // tạo sessionId
//       try {
//           String customerId = (String)enquiryResponse.get("customerID");
//           LOCATION = customerId + "#"+a;
//           Date startTime = new Date();
//           Date endTime = new Date(startTime.getTime() + SessionUtil.timeoutSession * 1000);
//           String sessionID = sessionUtil.createCustomerSessionId(customerId);
//           AuthSessionRequest sessionRequest = AuthSessionRequest.builder()
//                   .sessionId(sessionID)
//                   .startTime(startTime)
//                   .endTime(endTime)
//                   .channel(apiResponse.getHeader().getChannel())
//                   .phone((String) enquiryResponse.get("phone"))
//                   .customerId(customerId)
//                   .companyCode((String) enquiryResponse.get("companyCode"))
//                   .location(LOCATION)
//                   .username((String) enquiryResponse.get("username"))
//                   .build();
//
//           int result = sessionIdDAO.insertSessionId(sessionRequest);
//
//           if(result == 0){
//               ApiError apiError = apiErrorUtils.getError("800");
//               apiResponse.setError(apiError);
//               return apiResponse;
//           }
//           log.info("{}{}",LOCATION,"#apiLogin");
//
//
//
//       }catch (Exception e){
//           log.error("{}:{}",LOCATION,e.getMessage());
//
//       }







    }



}


