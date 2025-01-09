package com.kaiasia.app.service.Auth_api.utils;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.kaiasia.app.core.model.ApiBody;
import com.kaiasia.app.core.model.ApiError;
import com.kaiasia.app.core.model.ApiHeader;
import lombok.*;

import java.util.TreeMap;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class LoginResult {
	private ApiHeader apiHeader;
	private ApiBody body;
	private ApiError error;



}
