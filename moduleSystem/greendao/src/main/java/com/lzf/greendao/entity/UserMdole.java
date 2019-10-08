package com.lzf.greendao.entity;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

/**
 * 作者：Created by 45703
 * 时间：Created on 2019/10/5.
 */
@Entity
public class UserMdole {
    @Id(autoincrement = true)
    private Long id;
    private String userid;
    private String username;
    private String realname;
    private int usertype;
    private String avatar;
    private int subjectid;
    @Generated(hash = 5953601)
    public UserMdole(Long id, String userid, String username, String realname,
            int usertype, String avatar, int subjectid) {
        this.id = id;
        this.userid = userid;
        this.username = username;
        this.realname = realname;
        this.usertype = usertype;
        this.avatar = avatar;
        this.subjectid = subjectid;
    }
    @Generated(hash = 2027612949)
    public UserMdole() {
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

    
   
}
