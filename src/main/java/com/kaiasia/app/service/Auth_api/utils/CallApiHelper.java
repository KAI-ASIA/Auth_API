package com.kaiasia.app.service.Auth_api.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.kaiasia.app.core.model.ApiRequest;
import com.kaiasia.app.core.model.ApiResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
import java.util.Arrays;

@Component
@Slf4j
public class CallApiHelper<T> {
    @Autowired
    private RestTemplate restTemplate;


    private static final JsonAndObjectUtils jsonAndObjectUtils = new JsonAndObjectUtils();
    public <T> T call(String url, HttpMethod httpMethod, String body, Class<T> responseType, HttpHeaders customHeaders) {
        try {
            log.info("Calling API - URL: {}, Method: {}, Body: {}", url, httpMethod, body);

            if (customHeaders == null) {
                customHeaders = new HttpHeaders();
            }
            customHeaders.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
            ApiRequest apiRequest = jsonAndObjectUtils.jsonToObject(body, ApiRequest.class);
            HttpEntity<ApiRequest> entity = new HttpEntity<>(apiRequest, customHeaders);

            ResponseEntity<T> response = restTemplate.exchange(url, httpMethod, entity, responseType);
            log.info("Response: {}", response);
            return response.getBody();
        } catch (Exception e) {
            log.error("Error calling API: {}", e.getMessage());
            // Trả về null hoặc giá trị mặc định nếu có lỗi
            return null;
        }
    }
}


