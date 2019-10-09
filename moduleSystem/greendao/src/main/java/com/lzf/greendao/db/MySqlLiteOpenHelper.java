package com.lzf.greendao.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;


import com.lzf.greendao.service.greendao.DaoMaster;
import com.lzf.greendao.service.greendao.UserModelDao;

import org.greenrobot.greendao.database.Database;

/**
 * Created by Iverson on 2018/11/15 5:47 PM
 * 此类用于：
 */
public class MySqlLiteOpenHelper extends DaoMaster.OpenHelper{

    public MySqlLiteOpenHelper(Context context, String name, SQLiteDatabase.CursorFactory factory) {
        super(context, name, factory);
    }

    @Override
    public void onUpgrade(Database db, int oldVersion, int newVersion) {
        new UpgradeHelper().migrate(db, UserModelDao.class) ;
    }

}

