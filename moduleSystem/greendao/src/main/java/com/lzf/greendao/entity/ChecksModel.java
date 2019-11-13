package com.lzf.greendao.entity;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

/**
 * 作者：Created by 45703
 * 时间：Created on 2019/10/13.
 */
@Entity
public class ChecksModel {
    @Id(autoincrement = true)
    private Long id;
    private String userid;
    private String mcode;
    private int category;
    private String cateId;
    private String cateName;
    private String checkDate;
    private String createDate;
    private String classId;
    private String className;
    private String objectId;
    private String objectName;
    private String records;
    private String students;
    private String photos;
    private boolean isUpdate;//是否已上传
    @Generated(hash = 1530252698)
    public ChecksModel(Long id, String userid, String mcode, int category,
            String cateId, String cateName, String checkDate, String createDate,
            String classId, String className, String objectId, String objectName,
            String records, String students, String photos, boolean isUpdate) {
        this.id = id;
        this.userid = userid;
        this.mcode = mcode;
        this.category = category;
        this.cateId = cateId;
        this.cateName = cateName;
        this.checkDate = checkDate;
        this.createDate = createDate;
        this.classId = classId;
        this.className = className;
        this.objectId = objectId;
        this.objectName = objectName;
        this.records = records;
        this.students = students;
        this.photos = photos;
        this.isUpdate = isUpdate;
    }
    @Generated(hash = 233724601)
    public ChecksModel() {
    }
    public Long getId() {
        return this.id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getMcode() {
        return this.mcode;
    }
    public void setMcode(String mcode) {
        this.mcode = mcode;
    }
    public int getCategory() {
        return this.category;
    }
    public void setCategory(int category) {
        this.category = category;
    }
    public String getCateId() {
        return this.cateId;
    }
    public void setCateId(String cateId) {
        this.cateId = cateId;
    }
    public String getCateName() {
        return this.cateName;
    }
    public void setCateName(String cateName) {
        this.cateName = cateName;
    }
    public String getCheckDate() {
        return this.checkDate==null?"2019-11-11 11:11":this.checkDate;
    }
    public void setCheckDate(String checkDate) {
        this.checkDate = checkDate;
    }
    public String getClassId() {
        return this.classId;
    }
    public void setClassId(String classId) {
        this.classId = classId;
    }
    public String getClassName() {
        return this.className;
    }
    public void setClassName(String className) {
        this.className = className;
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
    public String getRecords() {
        return this.records;
    }
    public void setRecords(String records) {
        this.records = records;
    }
    public String getStudents() {
        return this.students;
    }
    public void setStudents(String students) {
        this.students = students;
    }
    public String getPhotos() {
        return this.photos;
    }
    public void setPhotos(String photos) {
        this.photos = photos;
    }
    public boolean getIsUpdate() {
        return this.isUpdate;
    }
    public void setIsUpdate(boolean isUpdate) {
        this.isUpdate = isUpdate;
    }
    public String getUserid() {
        return this.userid;
    }
    public void setUserid(String userid) {
        this.userid = userid;
    }
    public String getCreateDate() {
        return this.createDate;
    }
    public void setCreateDate(String createDate) {
        this.createDate = createDate;
    }
}
