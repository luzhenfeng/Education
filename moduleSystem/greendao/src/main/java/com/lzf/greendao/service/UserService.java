package com.lzf.greendao.service;

import com.lzf.greendao.db.DaoManager;
import com.lzf.greendao.entity.UserModel;
import com.lzf.greendao.service.greendao.DaoSession;
import com.lzf.greendao.utils.MatterUtils;

/**
 * 作者：Created by 45703
 * 时间：Created on 2019/10/6.
 */
public class UserService {
    private volatile static UserService instance;
    private UserService(){}
    public static UserService getInstance(){
        if (instance==null){
            synchronized (UserService.class){
                if (instance==null){
                    instance=new UserService();
                }
            }
        }
        return instance;
    }

    private DaoSession mDaoSession = DaoManager.getInstance().getDaoSession();


    //插入一条
    public boolean insert(final UserModel userModel) {
        return  MatterUtils.doMatter(mDaoSession.getUserModelDao(), new Runnable() {
            @Override
            public void run() {
                mDaoSession.getUserModelDao().insertOrReplaceInTx(userModel);
            }
        });
    }

    public String getUserId(){
        return mDaoSession.getUserModelDao().load(1l).getUserid();
    }

    public String getRealname(){
        return mDaoSession.getUserModelDao().load(1l).getRealname();
    }

}
