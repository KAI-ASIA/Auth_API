package com.kaiasia.app.core.job;

import java.util.Map;

import com.kaiasia.app.core.model.ApiBody;
import com.kaiasia.app.core.model.ApiHeader;
import com.kaiasia.app.core.model.ApiRequest;
import com.kaiasia.app.core.model.ApiResponse;
import com.kaiasia.app.core.utils.ApiConstant;

public class BaseService {
    public Map<String, Object> getEnquiry(ApiRequest request){
        return (Map<String, Object>) request.getBody().get(ApiConstant.COMMAND.ENQUIRY);
    }

    public Map<String, Object> getTransaction(ApiRequest request){
        return (Map<String, Object>) request.getBody().get(ApiConstant.COMMAND.TRANSACTION);
    }
    
    public static Map<String, Object> getEnquiry(ApiResponse response){
        return (Map<String, Object>) request.getBody().get(ApiConstant.COMMAND.ENQUIRY);
    }
    
    
    public static <T> ApiRequest buildENQUIRY(T enquiryInput, ApiHeader header){
        ApiRequest apiReq = new ApiRequest();
        apiReq.setHeader(header); 
        ApiBody apiBody = new ApiBody();
        apiBody.put("COMMAND", "GET_ENQUIRY");
        apiBody.put("ENQUIRY", enquiryInput);
        apiReq.setBody(apiBody);
        return apiReq;
    }

}
