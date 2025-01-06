package com.kaiasia.app.service.Auth_api.dao.imp;


import com.kaiasia.app.core.dao.PosgrestDAOHelper;
import com.kaiasia.app.core.model.ApiBody;
import com.kaiasia.app.core.model.ApiError;
import com.kaiasia.app.core.model.ApiResponse;
import com.kaiasia.app.service.Auth_api.dao.IAuthOTPDao;
import com.kaiasia.app.service.Auth_api.model.Auth3Response;
import com.kaiasia.app.service.Auth_api.model.OTP;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

@Service
public class AuthOTPDao implements IAuthOTPDao {

    @Autowired
    PosgrestDAOHelper posgrestDAOHelper;


    @Override
    public OTP getOTP(HashMap<String, String> body) throws Exception {
        String sql = "SELECT * FROM auth_api.otp where auth_api.otp.username = :username " +
                "and auth_api.otp.session_id = :session_id;" +
                "and auth_api.otp.trans_id = :trans_id;";
        Map<String, String> params = new HashMap<>();
        params.put("username", body.get("username"));
        params.put("session_id", body.get("session_id"));
        params.put("trans_id", body.get("trans_id"));
        OTP otp = posgrestDAOHelper.querySingle(sql, params, new BeanPropertyRowMapper<>(OTP.class));
        return otp;
    }

    @Override
    public void setConfirmTime( Timestamp now, OTP otp) throws Exception {
        String sql = "UPDATE auth_api.otp SET confirm_time=:confirm_time " +
                "WHERE auth_api.otp.username = :username and auth_api.otp.session_id =:session_id " +
                "and auth_api.otp.trans_id =:trans_id;";
        Map<String, String> params = new HashMap<>();
        params.put("username", otp.getUsername());
        params.put("session_id", otp.getSession_id());
        params.put("trans_id", otp.getTrans_id());
        params.put("confirm_time", now.toString());
        posgrestDAOHelper.update(sql,params);
    }

    @Override
    public boolean compareOTPAndCheckExpiration(HashMap<String, String> body) throws Exception {
        String sql = "SELECT * FROM OTP  " +
                "WHERE session_id = :session_id AND channel = :channel AND username = :username  ORDER BY trans_id DESC";
        Map<String, String> params = new HashMap<>();
        params.put("session_id", body.get("session_id"));
        params.put("channel", body.get("channel"));
        params.put("username", body.get("username"));
        params.put("trans_id", body.get("trans_id"));

        OTP lastestOtp = posgrestDAOHelper.querySingle(sql,params,new BeanPropertyRowMapper<>(OTP.class));
        if (lastestOtp != null && lastestOtp.getValidate_code().equals(body.get("validate_code"))) {
            LocalDateTime now = LocalDateTime.now();
            return lastestOtp.getEnd_time().toLocalDateTime().isBefore(now.plusMinutes(2));
        }
        return false;
    }

    @Override
    public String generateAndSaveOTP(HashMap<String, String> body) {
        String otp = generateOTP();
        OTP newOTP = new OTP();
        newOTP.setSession_id(body.get("session_id"));
        newOTP.setChannel(body.get("channel"));
        newOTP.setUsername(body.get("username"));
        newOTP.setValidate_code(otp);
        newOTP.setEnd_time(Timestamp.valueOf(LocalDateTime.now().plusMinutes(2)));

//        kafkaTemplate.send("otp-topic", otp);
        return otp;
    }

    private String generateOTP() {
        Random random = new Random();
        int otpLength = 6;
        StringBuilder otp = new StringBuilder();

        for (int i = 0; i < otpLength; i++) {
            otp.append(random.nextInt(10));
        }

        return otp.toString();
    }

}
