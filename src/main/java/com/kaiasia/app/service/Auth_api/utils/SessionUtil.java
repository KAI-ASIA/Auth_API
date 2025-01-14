package com.kaiasia.app.service.Auth_api.utils;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.UUID;

@Component
public class SessionUtil {


    public String createCustomerSessionId(String customerId) {
        long timestamp = System.currentTimeMillis();
        String uuid = UUID.randomUUID().toString().replace("-", ""); // Tạo UUID
        return customerId + "_" + timestamp + "_" + uuid;
    }

//    public int checkSessionAvailable(String cuss){
//
//    }
}
