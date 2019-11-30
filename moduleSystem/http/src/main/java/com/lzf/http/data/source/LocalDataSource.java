package com.lzf.http.data.source;

import com.lzf.greendao.entity.ChecksModel;
import com.lzf.greendao.entity.DormCheckModel;
import com.lzf.greendao.entity.StudentModel;
import com.lzf.http.entity.CheckModel;
import com.lzf.http.entity.LoginModel;

import java.util.List;

/**
 * 作者：Created by 45703
 * 时间：Created on 2019/10/5.
 */
public interface LocalDataSource {
    /**
     * 保存服务器网址
     */
    void saveBaseUrl(String baseUrl);

    /**
     * 获取服务器网址
     */
    String getBaseUrl();

    /**
     * 保存人脸识别网址
     */
    void saveBaseFaceUrl(String baseFaceUrl);

    /**
     * 获取人脸识别网址
     */
    String getBaseFaceUrl();

    /**
     * 保存用户名
     */
    void saveUserName(String userName);

    /**
     * 获取用户名
     */
    String getUserName();

    /**
     * 保存用户密码
     */

    void savePassword(String password);

    /**
     * 获取用户密码
     */
    String getPassword();

    /**
     * 保存token
     */
    void saveToken(String token);

    /**
     * 获取token
     */
    String getToken();

    /**
     * 保存人脸识别token
     */
    void saveFaceToken(String token);

    /**
     * 获取人脸识别token
     */
    String getFaceToken();

    /**
     * 保存人脸识别id
     */
    void saveFaceId(String id);

    /**
     * 获取人脸识别id
     */
    String getFaceId();


    /**
     * 保存user表
     */
    boolean insertUser(LoginModel loginModel);

    /**
     * 保存检查对象版本
     */
    void saveCheckObjectVersion(int version);

    /**
     * 获取检查对象版本
     * @return
     */
    int getCheckObjectVersion();

    /**
     * 保存检查分类版本
     */
    void saveCheckCategoryVersion(int version);

    /**
     * 获取检查分类版本
     * @return
     */
    int getCheckCategoryVersion();


    /**
     * 保存检查分类版本
     */
    void saveAvatarsVersion(int version);

    /**
     * 获取检查分类版本
     * @return
     */
    int getAvatarsVersion();


    /**
     * 保存全部权限模块 保存例(m3-3:卫生,m3-4:纪律) ,把多个模块隔开  ：把模块和模块名隔开 -取-后面的数字取对应本地图片
     */
    void saveCodes(String codes);

    /**
     * 获取全部权限模块
     * @return
     */
    String getCodes();

    /**
     * 保存当前模块
     * @param code
     */
    void saveCode(String code);

    /**
     * 获取当前权限模块
     * @return
     */
    String getCode();

    /**
     * 插入
     * @param checkModel
     */
    boolean insertCheckModel(CheckModel checkModel);

    /**
     * 插入
     * @param dormCheckModel
     */
    boolean insertDormCheckModel(DormCheckModel dormCheckModel, List<StudentModel> studentModelList);

    /**
     * 保存图片路径
     */
    void savePhotos(String photos);

    /**
     * 获取图片路径
     */
    String getPhotos();


}
