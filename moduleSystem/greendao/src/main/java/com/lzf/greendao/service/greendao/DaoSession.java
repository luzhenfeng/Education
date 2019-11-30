package com.lzf.greendao.service.greendao;

import java.util.Map;

import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.AbstractDaoSession;
import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.identityscope.IdentityScopeType;
import org.greenrobot.greendao.internal.DaoConfig;

import com.lzf.greendao.entity.ChecksModel;
import com.lzf.greendao.entity.UserModel;
import com.lzf.greendao.entity.DormCheckModel;
import com.lzf.greendao.entity.StudentModel;

import com.lzf.greendao.service.greendao.ChecksModelDao;
import com.lzf.greendao.service.greendao.UserModelDao;
import com.lzf.greendao.service.greendao.DormCheckModelDao;
import com.lzf.greendao.service.greendao.StudentModelDao;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.

/**
 * {@inheritDoc}
 * 
 * @see org.greenrobot.greendao.AbstractDaoSession
 */
public class DaoSession extends AbstractDaoSession {

    private final DaoConfig checksModelDaoConfig;
    private final DaoConfig userModelDaoConfig;
    private final DaoConfig dormCheckModelDaoConfig;
    private final DaoConfig studentModelDaoConfig;

    private final ChecksModelDao checksModelDao;
    private final UserModelDao userModelDao;
    private final DormCheckModelDao dormCheckModelDao;
    private final StudentModelDao studentModelDao;

    public DaoSession(Database db, IdentityScopeType type, Map<Class<? extends AbstractDao<?, ?>>, DaoConfig>
            daoConfigMap) {
        super(db);

        checksModelDaoConfig = daoConfigMap.get(ChecksModelDao.class).clone();
        checksModelDaoConfig.initIdentityScope(type);

        userModelDaoConfig = daoConfigMap.get(UserModelDao.class).clone();
        userModelDaoConfig.initIdentityScope(type);

        dormCheckModelDaoConfig = daoConfigMap.get(DormCheckModelDao.class).clone();
        dormCheckModelDaoConfig.initIdentityScope(type);

        studentModelDaoConfig = daoConfigMap.get(StudentModelDao.class).clone();
        studentModelDaoConfig.initIdentityScope(type);

        checksModelDao = new ChecksModelDao(checksModelDaoConfig, this);
        userModelDao = new UserModelDao(userModelDaoConfig, this);
        dormCheckModelDao = new DormCheckModelDao(dormCheckModelDaoConfig, this);
        studentModelDao = new StudentModelDao(studentModelDaoConfig, this);

        registerDao(ChecksModel.class, checksModelDao);
        registerDao(UserModel.class, userModelDao);
        registerDao(DormCheckModel.class, dormCheckModelDao);
        registerDao(StudentModel.class, studentModelDao);
    }
    
    public void clear() {
        checksModelDaoConfig.clearIdentityScope();
        userModelDaoConfig.clearIdentityScope();
        dormCheckModelDaoConfig.clearIdentityScope();
        studentModelDaoConfig.clearIdentityScope();
    }

    public ChecksModelDao getChecksModelDao() {
        return checksModelDao;
    }

    public UserModelDao getUserModelDao() {
        return userModelDao;
    }

    public DormCheckModelDao getDormCheckModelDao() {
        return dormCheckModelDao;
    }

    public StudentModelDao getStudentModelDao() {
        return studentModelDao;
    }

}
