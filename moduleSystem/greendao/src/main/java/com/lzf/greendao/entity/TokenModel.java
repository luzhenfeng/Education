package com.lzf.greendao.entity;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

/**
 * 作者：Created by 45703
 * 时间：Created on 2019/10/5.
 */
@Entity
public class TokenModel {
    @Id(autoincrement = true)
    private Long id;
    private String access_token;
    private int expires_in;
    @Generated(hash = 1579339838)
    public TokenModel(Long id, String access_token, int expires_in) {
        this.id = id;
        this.access_token = access_token;
        this.expires_in = expires_in;
    }
    @Generated(hash = 169426701)
    public TokenModel() {
    }
    public Long getId() {
        return this.id;
    }
    public void setId(Long id) {
        this.id = id;
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
