package com.lzf.http.data.source.loacl;


import com.lzf.greendao.entity.UserModel;
import com.lzf.greendao.service.UserService;
import com.lzf.http.data.source.LocalDataSource;
import com.lzf.http.entity.LoginModel;

import priv.lzf.mvvmhabit.utils.SPUtils;

/**
 * 作者：Created by 45703
 * 时间：Created on 2019/10/5.
 */
public class LocalDataSourceImpl implements LocalDataSource {
    private volatile static LocalDataSourceImpl INSTANCE = null;

    public static LocalDataSourceImpl getInstance() {
        if (INSTANCE == null) {
            synchronized (LocalDataSourceImpl.class) {
                if (INSTANCE == null) {
                    INSTANCE = new LocalDataSourceImpl();
                }
            }
        }
        return INSTANCE;
    }

    public static void destroyInstance() {
        INSTANCE = null;
    }

    private LocalDataSourceImpl() {
        //数据库Helper构建
    }

    @Override
    public void saveUserName(String userName) {
        SPUtils.getInstance().put("username",userName);
    }

    @Override
    public String getUserName() {
        return SPUtils.getInstance().getString("username");
    }

    @Override
    public void savePassword(String password) {
        SPUtils.getInstance().put("password",password);
    }

    @Override
    public String getPassword() {
        return SPUtils.getInstance().getString("password");
    }

    @Override
    public void saveToken(String token) {
        SPUtils.getInstance().put("token",token);
    }

    @Override
    public String getToken() {
        return SPUtils.getInstance().getString("token");
    }

    @Override
    public boolean insertUser(LoginModel loginModel) {
        UserModel userModel=new UserModel();
        userModel.setAccess_token(loginModel.getToken().getAccess_token());
        userModel.setExpires_in(loginModel.getToken().getExpires_in());
        userModel.setAvatar(loginModel.getUser().getAvatar());
        userModel.setRealname(loginModel.getUser().getRealname());
        userModel.setSubjectid(loginModel.getUser().getSubjectid());
        userModel.setUserid(loginModel.getUser().getUserid());
        userModel.setUsername(loginModel.getUser().getUsername());
        userModel.setUsertype(loginModel.getUser().getUsertype());
        userModel.setId(1l);
        return UserService.getInstance().insert(userModel);
    }

    @Override
    public void saveCheckObjectVersion(int version) {
        SPUtils.getInstance().put("checkObjectVersion",version);
    }

    @Override
    public int getCheckObjectVersion() {
        return SPUtils.getInstance().getInt("checkObjectVersion",0);
    }

    @Override
    public void saveCheckCategoryVersion(int version) {
        SPUtils.getInstance().put("checkCategoryVersion",version);
    }

    @Override
    public int getCheckCategoryVersion() {
        return SPUtils.getInstance().getInt("checkCategoryVersion",0);
    }

    @Override
    public void saveCodes(String codes) {
        SPUtils.getInstance().put("codes",codes);
    }

    @Override
    public String getCodes() {
        return SPUtils.getInstance().getString("checkCategoryVersion");
    }
}
