//package com.kaiasia.app.service.Auth_api;
//
//
//
//
//
//import com.kaiasia.app.core.model.*;
//import com.kaiasia.app.service.Auth_api.utils.KaiRestTemplate;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Component;
//
//
//import javax.annotation.PostConstruct;
//import java.util.HashMap;
//import java.util.Map;
//
//
//
//@Component
//@Slf4j
//public class Test {
//
//    @Autowired
//    private KaiRestTemplate kaiRestTemplate;
//
//    @PostConstruct
//    public void test() {
//        // Khởi tạo đối tượng ApiRequest
//        ApiRequest apiReq = new ApiRequest();
//        ApiHeader apiHeader = new ApiHeader();
//        apiHeader.setChannel("API");
//        apiHeader.setLocation("PC/IOS");
//        apiHeader.setRequestAPI("FE API");
//        apiHeader.setRequestNode("node 01");
//        apiHeader.setSynasyn("true");
//        apiHeader.setApi("T24_UTIL_API");
//        apiHeader.setApiKey("VDI0X1VUSUxfQVBJ");
//        apiReq.setHeader(apiHeader);
//
//        // Tạo body của API request
//        Map<String, Object> body = new HashMap<>();
//        body.put("COMMAND", "GET_ENQUIRY");
//        Map<String, String> enquiry = new HashMap<>();
//        enquiry.put("authenType", "KAI.API.AUTHEN.GET.LOGIN");
//        enquiry.put("username", "28169200");
//        enquiry.put("password", "Phaivu@123");
//        body.put("ENQUIRY", enquiry);
//
//
//        apiReq.setBody((ApiBody) body);
//
//        // Khởi tạo KaiRestTemplate và gọi API
//        String url = "http://14.225.254.212:8082/T24_UTIL_API/process";  // Thay bằng URL thực tế của API
//        int apiTimeout = 5000;  // Timeout là 5 giây
//        ApiResponse response = null;
//        try {
//            response = kaiRestTemplate.callApi(apiReq, url, apiTimeout);
//            log.info("API Response: {}", response);
//        } catch (Exception e) {
//            log.error("API call failed: ", e);
//            throw new RuntimeException("API call failed", e);
//        }
//
//        // In kết quả
//        if (response != null) {
//            System.out.println("Response: " + response);
//        } else {
//            System.out.println("No response from API.");
//        }
//    }
//}
