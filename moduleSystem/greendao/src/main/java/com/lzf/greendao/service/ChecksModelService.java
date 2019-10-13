package com.lzf.greendao.service;

import com.lzf.greendao.db.DaoManager;
import com.lzf.greendao.entity.ChecksModel;
import com.lzf.greendao.service.greendao.ChecksModelDao;
import com.lzf.greendao.service.greendao.DaoSession;
import com.lzf.greendao.utils.MatterUtils;

import java.util.List;

import priv.lzf.mvvmhabit.utils.KLog;

/**
 * 作者：Created by 45703
 * 时间：Created on 2019/10/13.
 */
public class ChecksModelService {
    private volatile static ChecksModelService instance;
    private ChecksModelService(){}
    public static ChecksModelService getInstance(){
        if (instance==null){
            synchronized (ChecksModelService.class){
                if (instance==null){
                    instance=new ChecksModelService();
                }
            }
        }
        return instance;
    }

    private DaoSession mDaoSession = DaoManager.getInstance().getDaoSession();


    //插入一条
    public boolean insert(final ChecksModel checksModel) {
        return  MatterUtils.doMatter(mDaoSession.getChecksModelDao(), new Runnable() {
            @Override
            public void run() {
                KLog.e(getChecksModelList().size());
                mDaoSession.getChecksModelDao().insertOrReplaceInTx(checksModel);
            }
        });
    }

    /**
     * 获取全部
     * @return
     */
    public List<ChecksModel> getChecksModelList(){
        return mDaoSession.getChecksModelDao().loadAll();
    }

    /**
     * 获取未上传
     * @return
     */
    public List<ChecksModel> getNoUpdateChecksModelList(){
        return mDaoSession.getChecksModelDao().queryBuilder()
                .where(ChecksModelDao.Properties.IsUpdate.eq(false))
                .list();
    }

    /**
     * 获取已上传
     * @return
     */
    public List<ChecksModel> getUpdateChecksModelList(){
        return mDaoSession.getChecksModelDao().queryBuilder()
                .where(ChecksModelDao.Properties.IsUpdate.eq(true))
                .list();
    }

    /**
     * 上传成功更新上传状态
     * @return
     */
    public boolean updateChecksModel(final ChecksModel checksModel){
        return  MatterUtils.doMatter(mDaoSession.getChecksModelDao(), new Runnable() {
            @Override
            public void run() {
                mDaoSession.getChecksModelDao().insertOrReplaceInTx(checksModel);
            }
        });
    }

    public ChecksModel getChecksModel(long id){
        return mDaoSession.getChecksModelDao().queryBuilder()
                .where(ChecksModelDao.Properties.Id.eq(id))
                .unique();
    }

}
