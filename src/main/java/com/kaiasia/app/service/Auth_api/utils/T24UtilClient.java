package com.kaiasia.app.service.Auth_api.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigureOrder;
import org.springframework.stereotype.Component;

import com.kaiasia.app.core.model.ApiRequest;


public class T24UtilClient extends CallApiHelper {

	public LoginResult callLogin(String location, ApiRequest apiReq) {
		return commonRest(location, apiReq, LoginResult.class);
	}
}


