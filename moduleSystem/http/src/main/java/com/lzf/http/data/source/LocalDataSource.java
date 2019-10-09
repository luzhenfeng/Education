package com.lzf.http.data.source;

import com.lzf.http.entity.LoginModel;

/**
 * 作者：Created by 45703
 * 时间：Created on 2019/10/5.
 */
public interface LocalDataSource {
    /**
     * 保存用户名
     */
    void saveUserName(String userName);

    /**
     * 获取用户名
     */
    String getUserName();

    /**
     * 保存用户密码
     */

    void savePassword(String password);

    /**
     * 获取用户密码
     */
    String getPassword();

    /**
     * 保存token
     */
    void saveToken(String token);

    /**
     * 获取token
     */
    String getToken();

    /**
     * 保存user表
     */
    boolean insertUser(LoginModel loginModel);
}
