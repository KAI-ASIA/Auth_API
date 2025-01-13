package com.kaiasia.app.service.Auth_api.dao;

import com.kaiasia.app.service.Auth_api.model.Auth5InsertDb;

public interface IResetPwdDao {

    public int insertResetPwdRecord(Auth5InsertDb Fields);
}
