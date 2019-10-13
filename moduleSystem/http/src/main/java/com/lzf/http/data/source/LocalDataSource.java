package com.lzf.http.data.source;

import com.lzf.greendao.entity.ChecksModel;
import com.lzf.http.entity.CheckModel;
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

    /**
     * 保存检查对象版本
     */
    void saveCheckObjectVersion(int version);

    /**
     * 获取检查对象版本
     * @return
     */
    int getCheckObjectVersion();

    /**
     * 保存检查分类版本
     */
    void saveCheckCategoryVersion(int version);

    /**
     * 获取检查分类版本
     * @return
     */
    int getCheckCategoryVersion();

    /**
     * 保存权限模块
     */
    void saveCodes(String codes);

    /**
     * 获取权限模块
     * @return
     */
    String getCodes();

    /**
     * 插入
     * @param checkModel
     */
    boolean insertCheckModel(CheckModel checkModel);
}
