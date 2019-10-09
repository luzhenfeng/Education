package com.lzf.greendao.entity;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

/**
 * 作者：Created by 45703
 * 时间：Created on 2019/10/5.
 */
@Entity
public class UserModel {
    @Id(autoincrement = true)
    private Long id;
    private String userid;
    private String username;
    private String realname;
    private int usertype;
    private String avatar;
    private int subjectid;
    private String access_token;
    private int expires_in;
    @Generated(hash = 1127209273)
    public UserModel(Long id, String userid, String username, String realname,
            int usertype, String avatar, int subjectid, String access_token,
            int expires_in) {
        this.id = id;
        this.userid = userid;
        this.username = username;
        this.realname = realname;
        this.usertype = usertype;
        this.avatar = avatar;
        this.subjectid = subjectid;
        this.access_token = access_token;
        this.expires_in = expires_in;
    }
    @Generated(hash = 782181818)
    public UserModel() {
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
    public String getUsername() {
        return this.username;
    }
    public void setUsername(String username) {
        this.username = username;
    }
    public String getRealname() {
        return this.realname;
    }
    public void setRealname(String realname) {
        this.realname = realname;
    }
    public int getUsertype() {
        return this.usertype;
    }
    public void setUsertype(int usertype) {
        this.usertype = usertype;
    }
    public String getAvatar() {
        return this.avatar;
    }
    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }
    public int getSubjectid() {
        return this.subjectid;
    }
    public void setSubjectid(int subjectid) {
        this.subjectid = subjectid;
    }
    public String getAccess_token() {
        return this.access_token;
    }
    public void setAccess_token(String access_token) {
        this.access_token = access_token;
    }
    public int getExpires_in() {
        return this.expires_in;
    }
    public void setExpires_in(int expires_in) {
        this.expires_in = expires_in;
    }
}
