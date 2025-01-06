package com.kaiasia.app.service.Auth_api.dao;

import com.kaiasia.app.core.dao.PosgrestDAOHelper;
import com.kaiasia.app.service.Auth_api.model.AuthSessionRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;

@Component
public class SessionIdDAO implements IAuthSessionDao{
    @Autowired
    PosgrestDAOHelper posgrestDAOHelper;

    @Override
    public int insertSessionId(AuthSessionRequest authSessionRequest) throws Exception{
        String sql = "INSERT INTO auth_api.auth_session\n" +
                "(username, start_time, end_time, session_id, channel, \"location\", phone, email, company_code, customer_id)\n" +
                "VALUES(:USERNAME, :START_TIME, :END_TIME,:SESSION_ID, :CHANNEL, :LOCATION,:PHONE, :EMAIL, :COMPANY_CODE, :CUSTOMER_ID);";
        HashMap<String, Object> param = new HashMap();
        param.put("USERNAME", authSessionRequest.getUsername());
        param.put("START_TIME", authSessionRequest.getStartTime());
        param.put("END_TIME", authSessionRequest.getEndTime());
        param.put("SESSION_ID", authSessionRequest.getSessionId());
        param.put("CHANNEL", authSessionRequest.getChannel());
        param.put("LOCATION", authSessionRequest.getLocation());
        param.put("PHONE", authSessionRequest.getPhone());
        param.put("EMAIL", authSessionRequest.getEmail());
        param.put("COMPANY_CODE", authSessionRequest.getCompanyCode());
        param.put("CUSTOMER_ID",authSessionRequest.getCustomerId());
        int result = posgrestDAOHelper.update(sql, param);
        return result;
    }
}
