package com.kaiasia.app.service.Auth_api.dao;

import com.kaiasia.app.service.Auth_api.model.AuthSessionRequest;
import com.kaiasia.app.service.Auth_api.model.AuthSessionResponse;

public interface IAuthSessionDao {
    int insertSessionId(AuthSessionRequest authSessionRequest) throws Exception;

    boolean isSessionId(String sessionId)  throws Exception;

    AuthSessionResponse getAuthSessionId(String sessionId) throws Exception;

    int updateExpireSessionId(String sessionId) throws Exception;

    int deleteExpireSessionId() throws Exception;

}
