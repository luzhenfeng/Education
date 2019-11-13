package com.lzf.http.data.source.loacl;


import com.google.gson.Gson;
import com.lzf.greendao.entity.ChecksModel;
import com.lzf.greendao.entity.UserModel;
import com.lzf.greendao.service.ChecksModelService;
import com.lzf.greendao.service.UserService;
import com.lzf.http.data.source.LocalDataSource;
import com.lzf.http.entity.CheckModel;
import com.lzf.http.entity.LoginModel;
import com.nhsoft.utils.utils.DateUtil;

import java.util.List;

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
    public void saveBaseUrl(String baseUrl) {
        SPUtils.getInstance().put("baseUrl",baseUrl);
    }

    @Override
    public String getBaseUrl() {
        return SPUtils.getInstance().getString("baseUrl","http://10.44.48.71:8080/api/");
    }

    @Override
    public void saveBaseFaceUrl(String baseFaceUrl) {
        SPUtils.getInstance().put("baseFaceUrl",baseFaceUrl);
    }

    @Override
    public String getBaseFaceUrl() {
        return SPUtils.getInstance().getString("baseFaceUrl","http://192.168.0.50");
    }

    @Override
    public void saveUserName(String userName) {
        SPUtils.getInstance().put("username",userName);
    }

    @Override
    public String getUserName() {
        return SPUtils.getInstance().getString("username","");
    }

    @Override
    public void savePassword(String password) {
        SPUtils.getInstance().put("password",password);
    }

    @Override
    public String getPassword() {
        return SPUtils.getInstance().getString("password","");
    }

    @Override
    public void saveToken(String token) {
        SPUtils.getInstance().put("token",token);
    }

    @Override
    public String getToken() {
        return SPUtils.getInstance().getString("token","");
    }

    @Override
    public void saveFaceToken(String token) {
        SPUtils.getInstance().put("faceToken",token);
    }

    @Override
    public String getFaceToken() {
        return SPUtils.getInstance().getString("faceToken","");
    }

    @Override
    public void saveFaceId(String id) {
        SPUtils.getInstance().put("faceId",id);
    }

    @Override
    public String getFaceId() {
        return SPUtils.getInstance().getString("faceId","");
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
    public void saveAvatarsVersion(int version) {
        SPUtils.getInstance().put("avatars",version);
    }

    @Override
    public int getAvatarsVersion() {
        return SPUtils.getInstance().getInt("avatars",0);
    }

    @Override
    public void saveCodes(String codes) {
        SPUtils.getInstance().put("codes",codes);
    }

    @Override
    public String getCodes() {
        return SPUtils.getInstance().getString("codes","");
    }

    @Override
    public void saveCode(String code) {
        SPUtils.getInstance().put("code",code);
    }

    @Override
    public String getCode() {
        return SPUtils.getInstance().getString("code","");
    }

    @Override
    public boolean insertCheckModel(CheckModel checkModel) {
        ChecksModel model=new ChecksModel();
        model.setUserid(UserService.getInstance().getUserId());
        model.setMcode(checkModel.getMcode());
        model.setCategory(checkModel.getCategory());
        model.setCateId(checkModel.getCateId());
        model.setCateName(checkModel.getCateName());
        model.setCheckDate(checkModel.getCheckDate());
        model.setCreateDate(checkModel.getCreateDate());
        model.setClassId(checkModel.getClassId());
        model.setClassName(checkModel.getClassName());
        model.setObjectId(checkModel.getObjectId());
        model.setObjectName(checkModel.getObjectName());
        model.setRecords(checkModel.getRecords()==null?"":new Gson().toJson(checkModel.getRecords()));
        model.setStudents(checkModel.getStudents()==null?"":new Gson().toJson(checkModel.getStudents()));
        model.setPhotos(checkModel.getPhotos()==null?"":new Gson().toJson(checkModel.getPhotos()));
        model.setIsUpdate(false);
        List<ChecksModel> checksModelList=ChecksModelService.getInstance().getChecksModelList(DateUtil.getBeforeMinute(5));
        if (checksModelList.size()>0){
            for (ChecksModel checksModel:checksModelList){
                if (model.getRecords().equals(checksModel.getRecords())
                        &&model.getStudents().equals(checksModel.getStudents())
                        &&model.getUserid().equals(checksModel.getUserid())
                        &&model.getPhotos().equals(checksModel.getPhotos())
                        &&model.getMcode().equals(checksModel.getMcode())
                        &&model.getCategory()==checksModel.getCategory()
                        &&model.getCateId().equals(checksModel.getCateId())
                        &&model.getCateName().equals(checksModel.getCateName())
                        &&model.getClassId().equals(checksModel.getClassId())
                        &&model.getClassName().equals(checksModel.getClassName())
                        &&model.getObjectId().equals(checksModel.getObjectId())
                        &&model.getObjectName().equals(checksModel.getObjectName())){
                    return false;
                }
            }
        }
        return ChecksModelService.getInstance().insert(model);
    }

    @Override
    public void savePhotos(String photos) {
        SPUtils.getInstance().put("photos",photos);
    }

    @Override
    public String getPhotos() {
        return SPUtils.getInstance().getString("photos","");
    }
}
