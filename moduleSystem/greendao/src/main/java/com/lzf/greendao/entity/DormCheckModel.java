package com.lzf.greendao.entity;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.ToMany;

import java.util.List;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.DaoException;
import com.lzf.greendao.service.greendao.DaoSession;
import com.lzf.greendao.service.greendao.StudentModelDao;
import com.lzf.greendao.service.greendao.DormCheckModelDao;

/**
 * 作者：Created by 45703
 * 时间：Created on 2019/11/29.
 */
@Entity
public class DormCheckModel {
    @Id(autoincrement = true)
    private Long id;
    private String userid;
    private String objectId;
    private String objectName;
    private String checkDate;
    private String createDate;
    private boolean isUpdate;//是否已上传
    @ToMany(referencedJoinProperty = "studentModelId")
    private List<StudentModel> students;
    /** Used to resolve relations */
    @Generated(hash = 2040040024)
    private transient DaoSession daoSession;
    /** Used for active entity operations. */
    @Generated(hash = 1972491723)
    private transient DormCheckModelDao myDao;
    @Generated(hash = 971959415)
    public DormCheckModel(Long id, String userid, String objectId,
            String objectName, String checkDate, String createDate,
            boolean isUpdate) {
        this.id = id;
        this.userid = userid;
        this.objectId = objectId;
        this.objectName = objectName;
        this.checkDate = checkDate;
        this.createDate = createDate;
        this.isUpdate = isUpdate;
    }
    @Generated(hash = 1453062169)
    public DormCheckModel() {
    }
    public Long getId() {
        return this.id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getUserid() {
        return this.userid;
    }
    public void setUserid(String userid) {
        this.userid = userid;
    }
    public String getObjectId() {
        return this.objectId;
    }
    public void setObjectId(String objectId) {
        this.objectId = objectId;
    }
    public String getObjectName() {
        return this.objectName;
    }
    public void setObjectName(String objectName) {
        this.objectName = objectName;
    }
    public String getCheckDate() {
        return this.checkDate;
    }
    public void setCheckDate(String checkDate) {
        this.checkDate = checkDate;
    }
    public String getCreateDate() {
        return this.createDate;
    }
    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }
    public boolean getIsUpdate() {
        return this.isUpdate;
    }
    public void setIsUpdate(boolean isUpdate) {
        this.isUpdate = isUpdate;
    }
    /**
     * To-many relationship, resolved on first access (and after reset).
     * Changes to to-many relations are not persisted, make changes to the target entity.
     */
    @Generated(hash = 675686871)
    public List<StudentModel> getStudents() {
        if (students == null) {
            final DaoSession daoSession = this.daoSession;
            if (daoSession == null) {
                throw new DaoException("Entity is detached from DAO context");
            }
            StudentModelDao targetDao = daoSession.getStudentModelDao();
            List<StudentModel> studentsNew = targetDao
                    ._queryDormCheckModel_Students(id);
            synchronized (this) {
                if (students == null) {
                    students = studentsNew;
                }
            }
        }
        return students;
    }
    /** Resets a to-many relationship, making the next get call to query for a fresh result. */
    @Generated(hash = 238993120)
    public synchronized void resetStudents() {
        students = null;
    }
    /**
     * Convenient call for {@link org.greenrobot.greendao.AbstractDao#delete(Object)}.
     * Entity must attached to an entity context.
     */
    @Generated(hash = 128553479)
    public void delete() {
        if (myDao == null) {
            throw new DaoException("Entity is detached from DAO context");
        }
        myDao.delete(this);
    }
    /**
     * Convenient call for {@link org.greenrobot.greendao.AbstractDao#refresh(Object)}.
     * Entity must attached to an entity context.
     */
    @Generated(hash = 1942392019)
    public void refresh() {
        if (myDao == null) {
            throw new DaoException("Entity is detached from DAO context");
        }
        myDao.refresh(this);
    }
    /**
     * Convenient call for {@link org.greenrobot.greendao.AbstractDao#update(Object)}.
     * Entity must attached to an entity context.
     */
    @Generated(hash = 713229351)
    public void update() {
        if (myDao == null) {
            throw new DaoException("Entity is detached from DAO context");
        }
        myDao.update(this);
    }
    /** called by internal mechanisms, do not call yourself. */
    @Generated(hash = 510058261)
    public void __setDaoSession(DaoSession daoSession) {
        this.daoSession = daoSession;
        myDao = daoSession != null ? daoSession.getDormCheckModelDao() : null;
    }
 

}
