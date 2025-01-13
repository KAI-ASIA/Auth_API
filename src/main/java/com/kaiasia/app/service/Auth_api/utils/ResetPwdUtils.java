package com.kaiasia.app.service.Auth_api.utils;

import org.springframework.stereotype.Component;

import java.util.Random;
import java.util.UUID;

@Component
public class ResetPwdUtils {

    public String generateValidateCode(){
        Random random = new Random();
        int r = 100000 + random.nextInt(900000);
        return  String.valueOf(r) ;
    }
}
