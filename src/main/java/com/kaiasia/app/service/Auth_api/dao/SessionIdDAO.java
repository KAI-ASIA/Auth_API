package com.kaiasia.app.service.Auth_api.dao;

import com.kaiasia.app.service.Auth_api.model.AuthSessionRequest;

import java.util.HashMap;

public class SessionIdDAO implements IAuthSessionDao{
    @Override
    public int insertSessionId(AuthSessionRequest authSessionRequest) {
//        String sql = "INSERT INTO " + this.getTableName() + "(req_id,  priority, receive_time, request_msg, request_api, request_node, status, timeout, authen_type) VALUES (:REQ_ID, :PRIORITY, :RECEIVE_TIME, :REQUEST_MSG, :REQUEST_API, :REQUEST_NODE, :STATUS, :TIMEOUT, :AUTHEN_TYPE)";
//        HashMap<String, Object> param = new HashMap();
//        param.put("REQ_ID", apiReq.getReqId());
//        param.put("REQUEST_MSG", apiReq.getRequestMsg());
//        param.put("RECEIVE_TIME", apiReq.getReceiveTime());
//        param.put("STATUS", apiReq.getStatus());
//        param.put("REQUEST_API", apiReq.getRequestAPI());
//        param.put("REQUEST_NODE", apiReq.getRequestNode());
//        param.put("PRIORITY", apiReq.getPriority());
//        param.put("TIMEOUT", apiReq.getTimeout());
//        param.put("AUTHEN_TYPE", apiReq.getAuthenType());
//        int result = posgrestDAOHelper.update(sql, param);
        return 0;
    }
}
