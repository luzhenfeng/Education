package com.lzf.http.entity;

/**
 * 作者：Created by 45703
 * 时间：Created on 2019/10/20.
 */
public class LeaveStudentModel {
    /**
     * userid : rr1
     * studentid : 1
     * studentno : seer1
     * studentname : 454
     * classname : 233
     * status : 8
     */

    private String userid;
    private int studentid;
    private String studentno;
    private String studentname;
    private String classname;
    private int status;

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public int getStudentid() {
        return studentid;
    }

    public void setStudentid(int studentid) {
        this.studentid = studentid;
    }

    public String getStudentno() {
        return studentno;
    }

    public void setStudentno(String studentno) {
        this.studentno = studentno;
    }

    public String getStudentname() {
        return studentname;
    }

    public void setStudentname(String studentname) {
        this.studentname = studentname;
    }

    public String getClassname() {
        return classname;
    }

    public void setClassname(String classname) {
        this.classname = classname;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
