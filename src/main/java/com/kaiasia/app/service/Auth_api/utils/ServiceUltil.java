package com.kaiasia.app.service.Auth_api.utils;


import ms.apiclient.model.ApiBody;
import ms.apiclient.model.ApiError;
import ms.apiclient.model.ApiResponse;

public class ServiceUltil {

    /**
     * Trả về 1 response khi api chạy có lỗi
     * @param response Giữ liệu trả về
     * @param error Lỗi được truyền vào để thêm vào response
     * @return Trả về 1 ApiResponse bao gồm 1 apiBody có kiểu giữ liệu trả về và 1 ApiError
     * @param <T> Kiểu giữ liệu được chuyền vào để cho vào apiBody
     */
    public static <T> ApiResponse takeRespose(T response, ApiError error) {
        ApiResponse apiResponse = new ApiResponse();
        apiResponse.setError(error);
        ApiBody apiBody = new ApiBody();
        apiBody.put("enquiry", response);
        apiResponse.setBody(apiBody);
        return apiResponse;
    }

    /**
     * Trả về 1 response khi api chay không có lỗi
     * @param response Giữ liệu trả về
     * @return Trả về 1 ApiResponse bao gồm 1 apiBody có kiểu giữ liệu trả về
     * @param <T> Kiểu giữ liệu được chuyền vào để cho vào apiBody
     */
    public static <T> ApiResponse takeRespose(T response) {
        ApiResponse apiResponse = new ApiResponse();
        ApiBody apiBody = new ApiBody();
        apiBody.put("enquiry", response);
        apiResponse.setBody(apiBody);
        return apiResponse;
    }
}
