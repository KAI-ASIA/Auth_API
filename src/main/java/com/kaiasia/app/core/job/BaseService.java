package com.kaiasia.app.core.job;

import java.util.Map;

import com.kaiasia.app.core.model.ApiRequest;
import com.kaiasia.app.core.utils.ApiConstant;

public class BaseService {
    public Map<String, Object> getEnquiry(ApiRequest request){
        return (Map<String, Object>) request.getBody().get(ApiConstant.COMMAND.ENQUIRY);
    }

    public Map<String, Object> getTransaction(ApiRequest request){
        return (Map<String, Object>) request.getBody().get(ApiConstant.COMMAND.TRANSACTION);
    }

}
