package com.kaiasia.app.service.Auth_api.utils;

import java.util.Collections;

import ms.apiclient.model.ApiRequest;
import ms.apiclient.model.ApiResponse;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;


@Component
public class KaiRestTemplate {
        public ApiResponse callApi(ApiRequest apiReq, String url, int apiTimeout) throws Exception {
		        RestTemplate  restTemplate = new RestTemplate();
		        HttpComponentsClientHttpRequestFactory clientHttpRequestFactory = new HttpComponentsClientHttpRequestFactory();
                clientHttpRequestFactory.setConnectTimeout(apiTimeout);
                clientHttpRequestFactory.setReadTimeout(apiTimeout);
                restTemplate.setRequestFactory(clientHttpRequestFactory);


                HttpHeaders headers = new HttpHeaders();
                headers.setContentType(MediaType.APPLICATION_JSON);
                headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));

                System.out.println(apiReq);
                HttpEntity<ApiRequest> req = new HttpEntity<ApiRequest>(apiReq, headers);
                ResponseEntity<ApiResponse> result;
                System.out.println("adsdfsdf");

                try {
                        System.out.println(url + req);
                        result = restTemplate.postForEntity(url, req, ApiResponse.class);
                        System.out.println("result :"+ result);
                } catch (Exception restEx) {
                        restEx.printStackTrace();
                        throw new Exception("callRestAPISync_" + apiReq.getHeader().getApi(), restEx);
                }
                ApiResponse apiRes = result.getBody();
                return apiRes;
 }
	
	
	
}
