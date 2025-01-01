package com.kaiasia.app.service.Auth_api.utils;

import com.kaiasia.app.core.model.ApiBody;
import com.kaiasia.app.core.model.ApiError;
import com.kaiasia.app.core.model.ApiResponse;
import com.kaiasia.app.service.Auth_api.model.Auth3Response;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;


public class ServiceUltil {

    /**
     *
     * @param response
     * @param error
     * @return
     * @param <T>
     */
    public static <T> ApiResponse takeRespose( T response, ApiError error) {
        ApiResponse apiResponse = new ApiResponse();
        apiResponse.setError(error);
        ApiBody apiBody = new ApiBody();
        apiBody.put("enquiry", response);
        apiResponse.setBody(apiBody);
        return apiResponse;
    }

    public static <T> ApiResponse takeRespose(T response) {
        ApiResponse apiResponse = new ApiResponse();
        ApiBody apiBody = new ApiBody();
        apiBody.put("enquiry", response);
        apiResponse.setBody(apiBody);
        return apiResponse;
    }
}
