package com.kaiasia.app.service.Auth_api.utils;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

@Component
public class SessionUtil {
    @Value("${sessionTime.duration}")
    public static long timeoutSession;

    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd HHmmss");



    public String createCustomerSessionId(String customerId) {
        long timestamp = System.currentTimeMillis();
        String uuid = UUID.randomUUID().toString().replace("-", ""); // Tạo UUID
        return customerId + "_" + timestamp + "_" + uuid;
    }


    public  String createEndTime(String startTime) {

        LocalDateTime startDateTime = LocalDateTime.parse(startTime, formatter);


        LocalDateTime endDateTime = startDateTime.plusSeconds(timeoutSession);


        return endDateTime.format(formatter);
    }
}
