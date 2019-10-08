package com.lzf.greendao.service;

import com.lzf.greendao.db.DaoManager;
import com.lzf.greendao.entity.LoginModel;
import com.lzf.greendao.entity.TokenModel;
import com.lzf.greendao.entity.UserMdole;
import com.lzf.greendao.service.greendao.DaoSession;
import com.lzf.greendao.utils.MatterUtils;

/**
 * 作者：Created by 45703
 * 时间：Created on 2019/10/6.
 */
public class LoginService {
    private volatile static LoginService instance;
    private LoginService(){}
    public static LoginService getInstance(){
        if (instance==null){
            synchronized (LoginService.class){
                if (instance==null){
                    instance=new LoginService();
                }
            }
        }
        return instance;
    }

    private DaoSession mDaoSession = DaoManager.getInstance().getDaoSession();


    //插入一条
    public boolean insert(final LoginModel loginModel, final TokenModel tokenModel, final UserMdole userMdole) {
        return  MatterUtils.doMatter(mDaoSession.getLoginModelDao(), new Runnable() {
            @Override
            public void run() {
                long tokenFlag=mDaoSession.getTokenModelDao().insertOrReplace(tokenModel);
                long userFlag=mDaoSession.getUserMdoleDao().insertOrReplace(userMdole);
//                EvtLog.e("--------------->customerflag:"+customerflag);
                loginModel.setTokenModelId(tokenFlag);
                loginModel.setUserModelId(userFlag);
                mDaoSession.getLoginModelDao().insertOrReplaceInTx(loginModel);
            }
        });
    }

}
