package com.kaiasia.app.service.Auth_api.api.api_login;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.kaiasia.app.core.model.*;
import com.kaiasia.app.core.utils.GetErrorUtils;
import com.kaiasia.app.register.KaiMethod;
import com.kaiasia.app.register.KaiService;
import com.kaiasia.app.register.Register;
import com.kaiasia.app.service.Auth_api.config.ApiConfig;
import com.kaiasia.app.service.Auth_api.config.ApiProperties;
import com.kaiasia.app.service.Auth_api.utils.CallApiHelper;
import com.kaiasia.app.service.Auth_api.utils.ConvertApiHelper;
import com.kaiasia.app.service.Auth_api.utils.JsonAndObjectUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;

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

        // lấy body response từ t24Api trả về response auth_login
        LinkedHashMap enquiryResponse  = (LinkedHashMap) apiT24.getBody().get("enquiry");
        apiBody.put("enquiry",enquiryResponse);



        apiResponse.setBody(apiBody);



        return apiResponse;
    }



}


