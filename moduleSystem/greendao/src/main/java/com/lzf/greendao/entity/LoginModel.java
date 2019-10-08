package com.lzf.greendao.entity;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.ToOne;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.DaoException;

import com.google.gson.annotations.SerializedName;
import com.lzf.greendao.service.greendao.DaoSession;
import com.lzf.greendao.service.greendao.UserMdoleDao;
import com.lzf.greendao.service.greendao.TokenModelDao;
import com.lzf.greendao.service.greendao.LoginModelDao;

/**
 * 作者：Created by 45703
 * 时间：Created on 2019/10/5.
 */
@Entity
public class LoginModel {
    /**
     * token : {"access_token":"eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJpc3MiOiJlZjY5ZmEyMS03MzU2LTQ5ZjAtYmJjOS00ZTA5MzkxYTY0MWYiLCJuYW1lIjoi5p2O5b-X6b6ZIiwidXNlcnR5cGUiOjIsImlhdCI6MTU3MDI4NjAxNH0.AoKo28ljkTs0xWrWivrXLy-VUttMiFPOMF2eCqq4vjo","expires_in":604800}
     * user : {"userid":"ef69fa21-7356-49f0-bbc9-4e09391a641f","username":"3413404195","realname":"李志龙","usertype":2,"avatar":"","subjectid":0}
     */

    @Id(autoincrement = true)
    private Long id;
    private Long tokenModelId;
    @SerializedName("token")
    @ToOne(joinProperty = "tokenModelId")
    private TokenModel tokenModel;
    private Long userModelId;
    @SerializedName("user")
    @ToOne(joinProperty = "userModelId")
    private UserMdole userModle;
    /** Used to resolve relations */
    @Generated(hash = 2040040024)
    private transient DaoSession daoSession;
    /** Used for active entity operations. */
    @Generated(hash = 520140402)
    private transient LoginModelDao myDao;
    @Generated(hash = 1685072942)
    public LoginModel(Long id, Long tokenModelId, Long userModelId) {
        this.id = id;
        this.tokenModelId = tokenModelId;
        this.userModelId = userModelId;
    }
    @Generated(hash = 333234990)
    public LoginModel() {
    }
    public Long getId() {
        return this.id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public Long getTokenModelId() {
        return this.tokenModelId;
    }
    public void setTokenModelId(Long tokenModelId) {
        this.tokenModelId = tokenModelId;
    }
    public Long getUserModelId() {
        return this.userModelId;
    }
    public void setUserModelId(Long userModelId) {
        this.userModelId = userModelId;
    }
    @Generated(hash = 739211116)
    private transient Long tokenModel__resolvedKey;
    /** To-one relationship, resolved on first access. */
    @Generated(hash = 710638585)
    public TokenModel getTokenModel() {
        Long __key = this.tokenModelId;
        if (tokenModel__resolvedKey == null || !tokenModel__resolvedKey.equals(__key)) {
            final DaoSession daoSession = this.daoSession;
            if (daoSession == null) {
                throw new DaoException("Entity is detached from DAO context");
            }
            TokenModelDao targetDao = daoSession.getTokenModelDao();
            TokenModel tokenModelNew = targetDao.load(__key);
            synchronized (this) {
                tokenModel = tokenModelNew;
                tokenModel__resolvedKey = __key;
            }
        }
        return tokenModel;
    }
    /** called by internal mechanisms, do not call yourself. */
    @Generated(hash = 1214512881)
    public void setTokenModel(TokenModel tokenModel) {
        synchronized (this) {
            this.tokenModel = tokenModel;
            tokenModelId = tokenModel == null ? null : tokenModel.getId();
            tokenModel__resolvedKey = tokenModelId;
        }
    }
    @Generated(hash = 204612037)
    private transient Long userModle__resolvedKey;
    /** To-one relationship, resolved on first access. */
    @Generated(hash = 2097596656)
    public UserMdole getUserModle() {
        Long __key = this.userModelId;
        if (userModle__resolvedKey == null || !userModle__resolvedKey.equals(__key)) {
            final DaoSession daoSession = this.daoSession;
            if (daoSession == null) {
                throw new DaoException("Entity is detached from DAO context");
            }
            UserMdoleDao targetDao = daoSession.getUserMdoleDao();
            UserMdole userModleNew = targetDao.load(__key);
            synchronized (this) {
                userModle = userModleNew;
                userModle__resolvedKey = __key;
            }
        }
        return userModle;
    }
    /** called by internal mechanisms, do not call yourself. */
    @Generated(hash = 1131890388)
    public void setUserModle(UserMdole userModle) {
        synchronized (this) {
            this.userModle = userModle;
            userModelId = userModle == null ? null : userModle.getId();
            userModle__resolvedKey = userModelId;
        }
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
    @Generated(hash = 388044322)
    public void __setDaoSession(DaoSession daoSession) {
        this.daoSession = daoSession;
        myDao = daoSession != null ? daoSession.getLoginModelDao() : null;
    }




}
