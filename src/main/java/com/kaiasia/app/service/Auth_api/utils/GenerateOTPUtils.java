package com.kaiasia.app.service.Auth_api.utils;

import org.springframework.stereotype.Component;

import java.util.Random;

@Component
public class GenerateOTPUtils {
    public String generateOTP() {
        Random random = new Random();
        int otp = 100000 + random.nextInt(900000);
        return String.valueOf(otp);
    }
}
