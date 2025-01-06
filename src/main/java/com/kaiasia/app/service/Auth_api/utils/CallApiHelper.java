package com.kaiasia.app.service.Auth_api.utils;

import java.util.Map;

import com.kaiasia.app.core.job.Enquiry;
import com.kaiasia.app.core.model.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.kaiasia.app.core.job.BaseService;

import lombok.extern.slf4j.Slf4j;

@Data
@Component
@Slf4j
public class CallApiHelper {
	
	
	private String url;
	private String apiName;
	private String apiKey;
	private int apiTimeout;
	
	
	@Autowired
    private KaiRestTemplate kaiRestTemplate;
    
    
	 	
	

	 public <T> T commonRest(String location, ApiRequest apiReq, Class<T> classResult) {
	        ApiError apiError = new ApiError();
	        long a = System.currentTimeMillis();
	        try {
	            ApiHeader headerApi = this.rebuildApiHeader(apiReq.getHeader());
	            ApiRequest apiReqRebuild = new ApiRequest();
	            apiReqRebuild.setHeader(headerApi);

	            apiReqRebuild.setBody(apiReq.getBody());
	            ApiResponse response = kaiRestTemplate.callApi(apiReqRebuild, url, apiTimeout);
				System.out.println(response);
	            if (response != null && response.getError() != null) {
	                apiError = response.getError();
	                ModelMapper mapper = new ModelMapper();
	                return mapper.map(apiError, classResult);
	            }
	            Map<String, Object> enquiryMap = BaseService.getEnquiry(response);
	            System.out.println(enquiryMap);
	            ModelMapper mapper = new ModelMapper();
	             
	            return mapper.map(enquiryMap, classResult);
	        } catch (Exception eis) {
	            apiError.setCode("TIMEOUT");
	            apiError.setDesc(eis.toString());
	            ModelMapper mapper = new ModelMapper();
	            return mapper.map(apiError, classResult);
	        }
	    }
	

    

    private ApiHeader rebuildApiHeader(ApiHeader apiHeader){
        ApiHeader headerApi = new ApiHeader();
        headerApi.setChannel(apiHeader.getChannel());
        headerApi.setContext(apiHeader.getContext());
        headerApi.setLocation(apiHeader.getLocation());
        headerApi.setRequestNode(apiHeader.getRequestNode());
        headerApi.setReqType("REQUEST");
		headerApi.setPriority(apiHeader.getPriority());
        headerApi.setDuration(apiHeader.getDuration());
        headerApi.setRequestAPI(apiHeader.getRequestAPI());
        headerApi.setSynasyn("true");
        headerApi.setApi(apiName);
        headerApi.setApiKey(apiKey);
        return headerApi;
    }
	
	

}


