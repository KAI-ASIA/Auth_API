package com.kaiasia.app.service.Auth_api.utils;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Getter;
import lombok.Setter;
import ms.apiclient.model.ApiBody;
import ms.apiclient.model.ApiError;
import ms.apiclient.model.ApiHeader;

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
