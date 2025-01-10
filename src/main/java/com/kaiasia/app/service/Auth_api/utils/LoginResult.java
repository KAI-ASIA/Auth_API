package com.kaiasia.app.service.Auth_api.utils;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;


import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;
import ms.apiclient.model.ApiBody;
import ms.apiclient.model.ApiError;
import ms.apiclient.model.ApiHeader;


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
