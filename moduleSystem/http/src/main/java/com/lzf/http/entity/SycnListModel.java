package com.lzf.http.entity;

/**
 * Created by lzf on 2019/10/9.
 * Describe:
 */

public class SycnListModel {

    /**
     * code : 01
     * name : 检查对象
     * api : /mq/getcheckobjects
     * version : 10001
     */

    private String code;
    private String name;
    private String api;
    private int version;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getApi() {
        return api;
    }

    public void setApi(String api) {
        this.api = api;
    }

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }
}
