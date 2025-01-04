package com.kaiasia.app.service.Auth_api.utils;

import java.util.Arrays;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import com.kaiasia.app.core.model.ApiError;
import com.kaiasia.app.core.model.ApiHeader;
import com.kaiasia.app.core.model.ApiRequest;
import com.kaiasia.app.core.model.ApiResponse;
import com.kaiasia.app.core.utils.ApiConstant;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class CallApiHelper {
	
	
	private String url;
	private String apiName;
	private String apiKey;
	private int apiTimeout;
	
	
	@Autowired
    private KaiRestTemplate kaiRestTemplate;
    
    
	 	
	 public Map<String, Object> getEnquiry(ApiResponse response){
	        return (Map<String, Object>) request.getBody().get(ApiConstant.COMMAND.ENQUIRY);
	 }

	 public <T> ApiResponse commonRest(String location, ApiRequest apiReq, Class<T> classResult) {
	        ApiError apiError = new ApiError();
	        long a = System.currentTimeMillis();
	        try {
	            ApiHeader headerApi = this.rebuildApiHeader(apiReq.getHeader());
	            ApiRequest apiReqRebuild = new ApiRequest();
	            apiReqRebuild.setHeader(headerApi);
	            apiReqRebuild.setBody(apiReq.getBody()); 
	            ApiResponse response = kaiRestTemplate.callApi(apiReqRebuild, apiTimeout, url); 
	            if (response != null && response.getError() != null) {
	                apiError = response.getError();
	                ModelMapper mapper = new ModelMapper();
	                return mapper.map(apiError, classResult);
	            }
	            Map<String, Object> enquiryMap = getEnquiry(response);
	            
	            ModelMapper mapper = new ModelMapper();
	             
	            return mapper.map(enquiryMap, classResult);
	        } catch (Exception eis) {
	            apiError.setCode("TIMEOUT");
	            apiError.setDesc(eis.toString());
	            ModelMapper mapper = new ModelMapper();
	            return mapper.map(apiError, classResult);
	        }
	    }
	
//    private static final JsonAndObjectUtils jsonAndObjectUtils = new JsonAndObjectUtils();
//    public <T> T call(HttpMethod httpMethod, ApiRequest req, HttpHeaders customHeaders) {
//        try {
//            log.info("Calling API - URL: {}, Method: {}, Body: {}", url, httpMethod, body);
//
//            if (customHeaders == null) {
//                customHeaders = new HttpHeaders();
//            }
//            customHeaders.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
//            ApiRequest apiRequest = jsonAndObjectUtils.jsonToObject(body, ApiRequest.class);
//            HttpEntity<ApiRequest> entity = new HttpEntity<>(apiRequest, customHeaders);
//
//            ResponseEntity<T> response = restTemplate.exchange(url, httpMethod, entity, responseType);
//            log.info("Response: {}", response);
//            return response.getBody();
//        } catch (Exception e) {
//            log.error("Error calling API: {}", e.getMessage());
//
//            // Trả về null hoặc giá trị mặc định nếu có lỗi
//            return null;
//        }
//    }
    
    
    
    

    private ApiHeader rebuildApiHeader(ApiHeader apiHeader){
        ApiHeader headerApi = new ApiHeader();
        headerApi.setChannel(apiHeader.getChannel());
        headerApi.setSubChannel(apiHeader.getSubChannel());
        headerApi.setContext(apiHeader.getContext());
        headerApi.setLocation(apiHeader.getLocation());
        headerApi.setRequestNode(apiHeader.getRequestNode());
        headerApi.setReqType("REQUEST");
        headerApi.setTrusted(apiHeader.getTrusted());
        headerApi.setUserID(apiHeader.getUserID());
        headerApi.setRequestAPI(apiHeader.getRequestAPI());
        headerApi.setSynasyn("true");
        headerApi.setApi(apiName);
        headerApi.setApiKey(apiKey);
        return headerApi;
    }
	
	
    public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getApiName() {
		return apiName;
	}
	public void setApiName(String apiName) {
		this.apiName = apiName;
	}
	public String getApiKey() {
		return apiKey;
	}
	public void setApiKey(String apiKey) {
		this.apiKey = apiKey;
	}
	public int getApiTimeout() {
		return apiTimeout;
	}
	public void setApiTimeout(int apiTimeout) {
		this.apiTimeout = apiTimeout;
	}
}


