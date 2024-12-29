package com.kaiasia.app.service.Auth_api.utils;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.net.URI;
@Component
@Slf4j
public class CallApiHelper<T> {


    @Autowired
    private RestTemplate restTemplate;


    public  <T> T call(String url, HttpMethod httpMethod, String body, MultiValueMap<String,String> headers,Class<T> responseType){
        RequestEntity<String> entity = new RequestEntity<>(body,headers,httpMethod, URI.create(url));
        ResponseEntity<T> response = restTemplate.exchange(entity,responseType);

        T apiResponse = response.getBody();

        return apiResponse;
    }


}
