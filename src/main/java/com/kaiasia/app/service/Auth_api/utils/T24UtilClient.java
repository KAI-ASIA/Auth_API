package com.kaiasia.app.service.Auth_api.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureOrder;
import org.springframework.stereotype.Component;

import com.kaiasia.app.core.model.ApiRequest;

@Component
public class T24UtilClient extends CallApiHelper{
	
	@Autowired
	private CallApiHelper CallApiHelper;
	
	public LoginResult callLogin(String location, ApiRequest apiReq){
		return CallApiHelper.commonRest(location, apiReq, LoginResult.class);
	}

}
