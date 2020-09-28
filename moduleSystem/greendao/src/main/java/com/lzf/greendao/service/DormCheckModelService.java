package com.lzf.greendao.service;

import android.content.Context;

import com.google.gson.Gson;
import com.lzf.greendao.db.DaoManager;
import com.lzf.greendao.entity.ChecksModel;
import com.lzf.greendao.entity.DormCheckModel;
import com.lzf.greendao.entity.StudentModel;
import com.lzf.greendao.service.greendao.DaoSession;
import com.lzf.greendao.service.greendao.DormCheckModelDao;
import com.lzf.greendao.utils.DataUtils;
import com.lzf.greendao.utils.MatterUtils;
import com.nhsoft.utils.utils.DateUtil;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import priv.lzf.mvvmhabit.utils.KLog;

/**
 * 作者：Created by 45703
 * 时间：Created on 2019/11/29.
 */
public class DormCheckModelService {
    private volatile static DormCheckModelService instance;
    private DormCheckModelService(){}
    public static DormCheckModelService getInstance(){
        if (instance==null){
            synchronized (DormCheckModelService.class){
                if (instance==null){
                    instance=new DormCheckModelService();
                }
            }
        }
        return instance;
    }

    private DaoSession mDaoSession = DaoManager.getInstance().getDaoSession();


    //插入一条
    public boolean insert(final DormCheckModel dormCheckModel, final List<StudentModel> studentModelList) {
        return  MatterUtils.doMatter(mDaoSession.getDormCheckModelDao(), new Runnable() {
            @Override
            public void run() {
                mDaoSession.getDormCheckModelDao().insertOrReplaceInTx(dormCheckModel);
                for (StudentModel studentModel:studentModelList){
                    studentModel.setStudentModelId(dormCheckModel.getId());
                }
                mDaoSession.getStudentModelDao().insertOrReplaceInTx(studentModelList);
                KLog.e(new Gson().toJson(studentModelList));
                KLog.e(new Gson().toJson(dormCheckModel));
            }
        });
    }

    /**
     * 获取全部
     * @return
     */
    public List<DormCheckModel> getDormCheckModelList(){
        List<DormCheckModel> dormCheckModelList=mDaoSession.getDormCheckModelDao().queryBuilder()
                .where(DormCheckModelDao.Properties.Userid.eq(UserService.getInstance().getUserId()))
                .list();
        if (dormCheckModelList!=null&&dormCheckModelList.size()!=0){
            for (DormCheckModel dormCheckModel:dormCheckModelList){
                dormCheckModel.getStudents();
            }
            Collections.reverse(dormCheckModelList);
            return dormCheckModelList;
        }else {
            return null;
        }
    }

    /**
     * 上传成功更新上传状态
     * @return
     */
    public boolean updateChecksModel(final DormCheckModel checksModel, final List<StudentModel> studentModelList){
        return  MatterUtils.doMatter(mDaoSession.getDormCheckModelDao(), new Runnable() {
            @Override
            public void run() {
                checksModel.setIsUpdate(false);
                for (StudentModel studentModel:checksModel.getStudents()){
                    studentModel.setStatus(studentModelList.get(checksModel.getStudents().indexOf(studentModel)).getStatus());
                }
                mDaoSession.getDormCheckModelDao().insertOrReplace(checksModel);
                mDaoSession.getStudentModelDao().insertOrReplaceInTx(checksModel.getStudents());
            }
        });
    }

    /**
     * 上传成功更新上传状态
     * @return
     */
    public boolean updateChecksModelList(final List<DormCheckModel> checkModelList){
        return  MatterUtils.doMatter(mDaoSession.getDormCheckModelDao(), new Runnable() {
            @Override
            public void run() {
                mDaoSession.getDormCheckModelDao().insertOrReplaceInTx(checkModelList);
            }
        });
    }

    /**
     * 获取最后一条数据
     * @return
     */
    public DormCheckModel getMaxChecksModel(){
        List<DormCheckModel> checksModelList=mDaoSession.getDormCheckModelDao().loadAll();
        DormCheckModel dormCheckModel=checksModelList.get(checksModelList.size()-1);
        dormCheckModel.getStudents();
        return dormCheckModel;
    }

    /**
     * 获取未提交的
     * @return
     */
    public List<DormCheckModel> getNoUpdateDormCheckModelList(){
        List<DormCheckModel> dormCheckModelList=mDaoSession.getDormCheckModelDao().queryBuilder()
                .where(DormCheckModelDao.Properties.IsUpdate.eq(false),DormCheckModelDao.Properties.Userid.eq(UserService.getInstance().getUserId()))
                .list();
        if (dormCheckModelList!=null&&dormCheckModelList.size()>0){
            for (DormCheckModel dormCheckModel:dormCheckModelList){
                dormCheckModel.getStudents();
            }
            return dormCheckModelList;
        }
        return null;
    }

    /**
     * 获取提交的数据
     * @param  objectId 寝室id
     * @param checkDate 检查时间
     * @return
     */
    public DormCheckModel getNowDormCheckModel(String objectId,String checkDate){
//        mDaoSession.getDormCheckModelDao().deleteAll();
//        mDaoSession.getStudentModelDao().deleteAll();
        DormCheckModel dormCheckModel=mDaoSession.getDormCheckModelDao().queryBuilder()
                .where(DormCheckModelDao.Properties.CheckDate.ge(checkDate+" 00:00"),
                        DormCheckModelDao.Properties.CheckDate.le(checkDate+" 23:59"),
                        DormCheckModelDao.Properties.ObjectId.eq(objectId),
                        DormCheckModelDao.Properties.Userid.eq(UserService.getInstance().getUserId()))
                .unique();
        if (dormCheckModel!=null){
            dormCheckModel.getStudents();
            return dormCheckModel;
        }
        return null;
    }

    /**
     * 获取异常的学生数据
     * @param startDate 开始时间
     * @param endDate 结束时间
     * @return
     */
    public List<StudentModel> getThrowStudentCheckModel(Context context,String startDate, String endDate){
        List<StudentModel> studentModelList=new ArrayList<>();
        List<DormCheckModel> dormCheckModelList=mDaoSession.getDormCheckModelDao().queryBuilder()
                .where(DormCheckModelDao.Properties.CheckDate.ge((startDate==null?"1990-01-01":startDate)+" 00:00"),
                        DormCheckModelDao.Properties.CheckDate.le(endDate+" 23:59"),
                        DormCheckModelDao.Properties.Userid.eq(UserService.getInstance().getUserId()))
                .list();
        if (dormCheckModelList!=null&&dormCheckModelList.size()>0){
            for (DormCheckModel dormCheckModel:dormCheckModelList){
                List<StudentModel> studentModels= dormCheckModel.getStudents();
                for (StudentModel studentModel:studentModels){
                    if (studentModel.getStatus()!=1){
                        studentModel.setCheckDate(dormCheckModel.getCheckDate());
                        studentModel.setObjectName(dormCheckModel.getObjectName());
                        studentModel.setHeadPic(context.getExternalCacheDir().getPath() + "PhotoFile/"+studentModel.getUserid()+".jpg");
                        studentModelList.add(studentModel);
                    }
                }
            }
        }
        return studentModelList;
    }

//    /**
//     * 获取今天提交的数据
//     * @param  objectId 寝室id
//     * @return
//     */
//    public List<DormCheckModel> getNowDormCheckModel(String objectId){
//        List<DormCheckModel> dormCheckModelList=mDaoSession.getDormCheckModelDao().queryBuilder()
//                .where(DormCheckModelDao.Properties.CreateDate.ge(DateUtil.getCurrentDate()+" 00:00"),
//                        DormCheckModelDao.Properties.CreateDate.le(DateUtil.getCurrentDate()+" 23:59"),
//                        DormCheckModelDao.Properties.ObjectId.eq(objectId),
//                        DormCheckModelDao.Properties.Userid.eq(UserService.getInstance().getUserId()))
//                .list();
//        if (dormCheckModelList!=null&&dormCheckModelList.size()>0){
//            for (DormCheckModel dormCheckModel:dormCheckModelList){
//                dormCheckModel.getStudents();
//            }
//            return dormCheckModelList;
//        }
//        return null;
//    }

    public List<DormCheckModel> getDormCheckModelList(String time){
        return mDaoSession.getDormCheckModelDao().queryBuilder()
                .where(DormCheckModelDao.Properties.CreateDate.ge(time),DormCheckModelDao.Properties.Userid.eq(UserService.getInstance().getUserId()))
                .list();
    }

    /**
     * 删除一个月前数据
     */
    public void deleteMonthAgoDormCheckModeList(){
         mDaoSession.getDormCheckModelDao().queryBuilder()
                .where(DormCheckModelDao.Properties.CreateDate.le(DataUtils.getAMonthAgoData()))
                .buildDelete()
                .executeDeleteWithoutDetachingEntities();
    }
}
