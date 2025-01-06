package com.kaiasia.app.core.model;

import com.fasterxml.jackson.annotation.JsonInclude;

import lombok.Data;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Data
public class T24Request {
	private String authenType;
	private String username;
	private String password;

}
