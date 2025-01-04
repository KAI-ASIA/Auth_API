package com.kaiasia.app.service.Auth_api.dao;

import com.kaiasia.app.service.Auth_api.model.AuthSessionRequest;

public interface IAuthSessionDao {
    int insertSessionId(AuthSessionRequest authSessionRequest) throws Exception;

}
