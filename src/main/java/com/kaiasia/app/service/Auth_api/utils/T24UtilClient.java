package com.kaiasia.app.service.Auth_api.utils;

import com.kaiasia.app.core.job.BaseService;
import com.kaiasia.app.core.model.*;
import lombok.Data;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureOrder;
import org.springframework.stereotype.Component;

import java.util.Map;

@Data
public class T24UtilClient  {

	public LoginResult callLogin(String location, ApiRequest apiReq) {
		LoginResult loginResult = commonRest(location, apiReq, LoginResult.class);
		System.out.println(loginResult.getBody());
		return loginResult;
	}
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
			System.out.println("a = " +  response);
			if (response != null && response.getError() != null) {
				apiError = response.getError();

				ModelMapper mapper = new ModelMapper();
				return mapper.map(response, classResult);
			}
			Map<String, Object> enquiryMap = BaseService.getEnquiry(response);
			System.out.println("b = " +enquiryMap);
			ApiBody apiBody = new ApiBody();
			apiBody.put("enquiry",enquiryMap);
			System.out.println(apiBody);
			ModelMapper mapper = new ModelMapper();
			System.out.println("c = " + mapper.map(response, classResult));

			return mapper.map(response, classResult);
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


