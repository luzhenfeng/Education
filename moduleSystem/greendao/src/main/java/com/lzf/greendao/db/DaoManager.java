package com.lzf.greendao.db;

import android.content.Context;


import com.lzf.greendao.service.greendao.DaoMaster;
import com.lzf.greendao.service.greendao.DaoSession;

import org.greenrobot.greendao.database.Database;

/**
 * Created by Iverson on 2018/11/15 5:49 PM
 * 此类用于：用于数据更新管理
 */
public class DaoManager {

    private static String              DB_NAME = "iwmpos-db";
    private static DaoManager          mDaoManager;
    private static MySqlLiteOpenHelper mySqlLiteOpenHelper;
    private static DaoSession mDaoSession;
    private static Database            mDatabase;
    private DaoManager() {}

    public static DaoManager getInstance(){
        if (mDaoManager == null){
            synchronized (DaoManager.class){
                if (mDaoManager == null){
                    mDaoManager = new DaoManager();
                }
            }
        }
        return mDaoManager;
    }

    public static void init(Context context){
        mySqlLiteOpenHelper = new MySqlLiteOpenHelper(context,DB_NAME,null);
        mDatabase = mySqlLiteOpenHelper.getWritableDb();
        mDaoSession = new DaoMaster(mDatabase).newSession();
    }

    public DaoSession getDaoSession(){
        return mDaoSession;
    }
}
