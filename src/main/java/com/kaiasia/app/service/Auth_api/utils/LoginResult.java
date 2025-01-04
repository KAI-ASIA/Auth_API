package com.kaiasia.app.service.Auth_api.utils;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import com.kaiasia.app.core.model.ApiBody;
import com.kaiasia.app.core.model.ApiError;
import com.kaiasia.app.core.model.ApiHeader;
import lombok.Getter;
import lombok.Setter;

import java.util.TreeMap;

@JsonIgnoreProperties(ignoreUnknown = true)
@Getter
@Setter
public class LoginResult {
	private ApiHeader header;
	private ApiBody body;
	private ApiError error;

	public void setError(ApiError error) {
		this.error = error;
		if (error != null && !ApiError.OK_CODE.equals(error.getCode())) {
			this.body = new ApiBody();
			this.body.put("status", "FAILE");
		}
	}

}
