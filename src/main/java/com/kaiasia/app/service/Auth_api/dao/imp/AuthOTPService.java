package com.kaiasia.app.service.Auth_api.dao.imp;


import com.kaiasia.app.core.dao.PosgrestDAOHelper;
import com.kaiasia.app.core.model.ApiBody;
import com.kaiasia.app.core.model.ApiError;
import com.kaiasia.app.core.model.ApiResponse;
import com.kaiasia.app.core.utils.GetErrorUtils;
import com.kaiasia.app.service.Auth_api.dao.IAuthOTPService;
import com.kaiasia.app.service.Auth_api.model.Auth3Response;
import com.kaiasia.app.service.Auth_api.model.OTP;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Map;

@Service
public class AuthOTPService implements IAuthOTPService {

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

    public boolean checkTimeOut(Timestamp endTime) {
        Timestamp now = new Timestamp(System.currentTimeMillis());
        if (endTime.after(now)) {
            return false;
        }
        return true;
    }

    @Override
    public ApiResponse takeRespose(Auth3Response response, ApiError error) {
        ApiResponse apiResponse = new ApiResponse();
        apiResponse.setError(error);
        ApiBody apiBody = new ApiBody();
        apiBody.put("responseCode", response.getResponseCode());
        apiBody.put("transId", response.getResponseCode());
        apiResponse.setBody(apiBody);
        return apiResponse;
    }

    @Override
    public ApiResponse takeRespose(Auth3Response response) {
        ApiResponse apiResponse = new ApiResponse();
        ApiBody apiBody = new ApiBody();
        apiBody.put("responseCode", response.getResponseCode());
        apiBody.put("transId", response.getResponseCode());
        apiResponse.setBody(apiBody);
        return apiResponse;
    }

}
