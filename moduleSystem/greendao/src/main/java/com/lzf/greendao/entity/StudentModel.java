package com.lzf.greendao.entity;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

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
    private Long studentModelId;//对应父类
    @Generated(hash = 901521756)
    public StudentModel(Long id, String userid, String studentname, String bendno,
            int status, Long studentModelId) {
        this.id = id;
        this.userid = userid;
        this.studentname = studentname;
        this.bendno = bendno;
        this.status = status;
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
}
