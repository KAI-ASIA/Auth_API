package com.kaiasia.app.service.Auth_api.dao.imp;


import com.kaiasia.app.core.dao.PosgrestDAOHelper;
import com.kaiasia.app.core.model.ApiBody;
import com.kaiasia.app.core.model.ApiError;
import com.kaiasia.app.core.model.ApiResponse;
import com.kaiasia.app.service.Auth_api.dao.IAuthOTPDao;
import com.kaiasia.app.service.Auth_api.model.Auth3Response;
import com.kaiasia.app.service.Auth_api.model.OTP;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

@Slf4j
@Service
public class AuthOTPDao implements IAuthOTPDao {

    @Autowired
    PosgrestDAOHelper posgrestDAOHelper;

//    @Autowired
//    private KafkaTemplate<String, String> kafkaTemplate;

    @Override
    public OTP getOTP(String sessionId, String username, String transId ) throws Exception {
        String sql = "SELECT * FROM auth_api.otp " +
                "where auth_api.otp.username = :username and auth_api.otp.session_id = :session_id " +
                "and auth_api.otp.trans_id = :trans_id;";
        Map<String, String> params = new HashMap<>();
        log.info("Info : {}: {} : {}", sessionId, username, transId);
        params.put("username", username);
        params.put("session_id", sessionId);
        params.put("trans_id", transId);
        OTP otp = posgrestDAOHelper.querySingle(sql, params, new BeanPropertyRowMapper<>(OTP.class));
        return otp;
    }

    @Override
    public void setConfirmTime( Timestamp now, OTP otp) throws Exception {
        String sql = "UPDATE auth_api.otp SET status='DONE', confirm_time= :confirm_time " +
                "WHERE auth_api.otp.username = :username and auth_api.otp.session_id = :session_id " +
                "and auth_api.otp.trans_id = :trans_id ;";
        Map<String, String> params = new HashMap<>();
        params.put("username", otp.getUsername());
        params.put("session_id", otp.getSession_id());
        params.put("trans_id", otp.getTrans_id());
        params.put("confirm_time", now.toString());
        posgrestDAOHelper.update(sql,params);
    }

    @Override
    public boolean compareOTPAndCheckExpiration(HashMap<String, String> body) throws Exception {
        String sql = "SELECT * FROM auth_api.otp  " +
                "WHERE auth_api.otp.session_id = :session_id AND auth_api.otp.channel = :channel AND auth_api.otp.username = :username  ORDER BY auth_api.otp.trans_id DESC";
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
    public String generateAndSaveOTP(HashMap<String, String> body) throws Exception{
        String otp = generateOTP();
        OTP newOTP = new OTP();
        newOTP.setSession_id(body.get("session_id"));
        newOTP.setChannel(body.get("channel"));
        newOTP.setUsername(body.get("username"));
        newOTP.setValidate_code(otp);
        newOTP.setEnd_time(Timestamp.valueOf(LocalDateTime.now().plusMinutes(2)));

        String sql = "INSERT INTO auth_api.otp\n" +
                "(validate_code, trans_id, username, channel, start_time, \"location\", end_time, status, session_id, confirm_time, trans_info, trans_time)\n" +
                "VALUES(:validate_code, :trans_id, :username,:channel, :start_time, :location,:end_time, :status, :session_id, :confirm_time, :trans_info, :trans_time);";
        HashMap<String, Object> param = new HashMap();
        param.put("validate_code", newOTP.getValidate_code());
        param.put("trans_id", newOTP.getTrans_id());
        param.put("username", newOTP.getUsername());
        param.put("channel", newOTP.getChannel());
        param.put("start_time", newOTP.getStart_time());
        param.put("location", newOTP.getLocation());
        param.put("end_time", newOTP.getEnd_time());
        param.put("status", newOTP.getStatus());
        param.put("session_id", newOTP.getSession_id());
        param.put("confirm_time",newOTP.getConfirm_time());
        param.put("trans_info", newOTP.getTrans_info());
        param.put("trans_time",newOTP.getTrans_time());

        int result = posgrestDAOHelper.update(sql, param);
        if (result > 0){
            //        kafkaTemplate.send("otp-topic", otp);
            return otp;
        }

        return null;
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
