package com.lzf.greendao.entity;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Transient;

/**
 * 作者：Created by 45703
 * 时间：Created on 2019/11/29.
 */
@Entity
public class StudentModel {
    @Id(autoincrement = true)
    private Long id;
    private String userid;
    private String studentname;
    private String bendno;
    private int status;//1-到寝 2-晚归 4-未归 8-请假 9-晚出
    private String className;
    @Transient
    private String checkDate;
    @Transient
    private String objectName;
    @Transient
    private String headPic;
    private Long studentModelId;//对应父类
    @Generated(hash = 439113944)
    public StudentModel(Long id, String userid, String studentname, String bendno,
            int status, String className, Long studentModelId) {
        this.id = id;
        this.userid = userid;
        this.studentname = studentname;
        this.bendno = bendno;
        this.status = status;
        this.className = className;
        this.studentModelId = studentModelId;
    }
    @Generated(hash = 2060229341)
    public StudentModel() {
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
    public String getStudentname() {
        return this.studentname;
    }
    public void setStudentname(String studentname) {
        this.studentname = studentname;
    }
    public String getBendno() {
        return this.bendno;
    }
    public void setBendno(String bendno) {
        this.bendno = bendno;
    }
    public int getStatus() {
        return this.status;
    }
    public void setStatus(int status) {
        this.status = status;
    }
    public Long getStudentModelId() {
        return this.studentModelId;
    }
    public void setStudentModelId(Long studentModelId) {
        this.studentModelId = studentModelId;
    }

    public String getCheckDate() {
        return checkDate;
    }

    public void setCheckDate(String checkDate) {
        this.checkDate = checkDate;
    }

    public String getObjectName() {
        return objectName;
    }

    public void setObjectName(String objectName) {
        this.objectName = objectName;
    }

    public String getHeadPic() {
        return headPic;
    }

    public void setHeadPic(String headPic) {
        this.headPic = headPic;
    }
    public String getClassName() {
        return this.className==null?"":this.className;
    }
    public void setClassName(String className) {
        this.className = className;
    }
}
