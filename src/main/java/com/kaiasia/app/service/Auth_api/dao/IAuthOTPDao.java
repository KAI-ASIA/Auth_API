package com.kaiasia.app.service.Auth_api.dao;

import com.kaiasia.app.core.model.ApiError;
import com.kaiasia.app.core.model.ApiResponse;
import com.kaiasia.app.service.Auth_api.model.Auth3Response;
import com.kaiasia.app.service.Auth_api.model.OTP;

import java.sql.Timestamp;
import java.util.HashMap;

public interface IAuthOTPDao {
    OTP getOTP(HashMap<String, String> body) throws Exception;
    void setConfirmTime( Timestamp now, OTP otp) throws Exception;
}