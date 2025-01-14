package com.kaiasia.app.service.Auth_api.dao.imp;

import com.kaiasia.app.core.dao.PosgrestDAOHelper;
import com.kaiasia.app.service.Auth_api.dao.IResetPwdDao;
import com.kaiasia.app.service.Auth_api.model.Auth5InsertDb;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;


@Service
@Slf4j
public class ResetPwdDao implements IResetPwdDao {

    @Autowired
    private PosgrestDAOHelper posgrestDAOHelper;

    @Override
    public int insertResetPwdRecord(Auth5InsertDb fields) {

        if (fields == null || fields.getTransId() == null || fields.getValidateCode() == null
                || fields.getChannel() == null || fields.getSessionId() == null) {
            log.info("Missing required fields for insertion" + this.getClass().getName());
            throw new IllegalArgumentException("Missing required fields for insertion");
        }

        String sql = "INSERT INTO auth_api.otp (trans_id, validate_code, username, channel, location, session_id, "
                + "start_time, end_time, confirm_time, trans_info, trans_time) "
                + "VALUES (:trans_id, :validate_code, :username, :channel, :location, :session_id, "
                + ":start_time, :end_time, :confirm_time, :trans_info, :trans_time)";

        HashMap<String , Object> param = new HashMap<>();
        param.put("trans_id",fields.getTransId());
        param.put("validate_code",fields.getValidateCode());
        param.put("username",fields.getUsername());
        param.put("channel",fields.getChannel());
        param.put("location",fields.getLocation());
        param.put("session_id",fields.getSessionId());
        param.put("start_time",fields.getStartTime());
        param.put("end_time",fields.getEndTime());
        param.put("confirm_time",fields.getConfirmTime());
        param.put("trans_info",fields.getTransInfo());
        param.put("trans_time",fields.getTransTime());


        int result ;
        try{
            log.info("insert to db with trans_id: {}-{}",fields.getTransId(),System.currentTimeMillis());
            result = posgrestDAOHelper.update(sql,param);
        }catch (Exception e) {
            log.info("Error inserting OTP record with trans_id: {}-{}", fields.getTransId(),e);
            throw new RuntimeException("Error inserting OTP record", e);
        }

        return result;
    }
}
