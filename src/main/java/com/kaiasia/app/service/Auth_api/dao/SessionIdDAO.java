package com.kaiasia.app.service.Auth_api.dao;

import com.kaiasia.app.core.dao.CommonDAO;
import com.kaiasia.app.core.dao.PosgrestDAOHelper;
import com.kaiasia.app.service.Auth_api.model.AuthSessionRequest;
import com.kaiasia.app.service.Auth_api.model.AuthSessionResponse;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;

//@Component
public class SessionIdDAO extends CommonDAO implements IAuthSessionDao{
    private static final Logger log = Logger.getLogger(SessionIdDAO.class);
    @Autowired
    private PosgrestDAOHelper posgrestDAOHelper;

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

    @Override
    public boolean isSessionId(String sessionId) {
        String sql = "SELECT COUNT(*) FROM auth_api.auth_session WHERE session_id = :SESSION_ID";
        HashMap<String, Object> param = new HashMap<>();
        param.put("SESSION_ID", sessionId);

        try {
            int count = posgrestDAOHelper.query4Int(sql, param);
            return count > 0; // Nếu số lượng lớn hơn 0, sessionId tồn tại
        } catch (Exception e) {

            return false; // Trong trường hợp lỗi, giả định rằng sessionId không tồn tại
        }
    }

    @Override
    public AuthSessionResponse getAuthSessionId(String sessionId) throws Exception {
        String sql = "SELECT username, start_time, end_time, session_id, channel, \"location\", phone, email, company_code, customer_id " +
                "FROM " + this.getTableName() + " WHERE session_id = :SESSION_ID";
        HashMap<String, Object> param = new HashMap<>();
        param.put("SESSION_ID", sessionId);

//        try {

            AuthSessionResponse authSessionResponse = posgrestDAOHelper.querySingle(
                    sql,
                    param,
                    new BeanPropertyRowMapper<>(AuthSessionResponse.class)
            );
            return authSessionResponse; // Trả về thông tin session
//        } catch (Exception e) {
//            log.error("Error retrieving sessionId: " + sessionId, e);
//            return null;
//        }
    }

    @Override
    public int updateExpireSessionId(String sessionId) throws Exception{
        String sql = "UPDATE auth_api.auth_session SET end_time = :END_TIME WHERE session_id = :SESSION_ID";
        HashMap<String, Object> param = new HashMap<>();
        param.put("SESSION_ID", sessionId);
        param.put("END_TIME", new Date()); // Đặt thời gian hết hạn thành thời điểm hiện tại


        int result = posgrestDAOHelper.update(sql, param);
        return result;
    }

    @Override
    public int deleteExpireSessionId() throws Exception{
        Date date = new Date();
        long currentTimeMillis = date.getTime();


        long currentTimeSeconds = currentTimeMillis / 1000;


        String sql = "DELETE FROM auth_api.auth_session WHERE end_time < TO_TIMESTAMP(:currentTime)";


        HashMap<String, Object> param = new HashMap<>();
        param.put("currentTime", currentTimeSeconds);

        int result = posgrestDAOHelper.update(sql, param);
        return result;

    }


}
