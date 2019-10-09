package com.lzf.http.entity;

import java.util.List;

/**
 * Created by lzf on 2019/10/9.
 * Describe:
 */

public class AppListModel {

    /**
     * apps : [{"code":"m2-2","name":"学生会纪律","logo":null,"sort":1}]
     * code : m001
     * name : 德育
     * logo : null
     * sort : 1
     */

    private String code;
    private String name;
    private String logo;
    private int sort;
    private List<AppsBean> apps;

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

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public int getSort() {
        return sort;
    }

    public void setSort(int sort) {
        this.sort = sort;
    }

    public List<AppsBean> getApps() {
        return apps;
    }

    public void setApps(List<AppsBean> apps) {
        this.apps = apps;
    }

    public static class AppsBean {
        /**
         * code : m2-2
         * name : 学生会纪律
         * logo : null
         * sort : 1
         */

        private String code;
        private String name;
        private String logo;
        private int sort;

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

        public String getLogo() {
            return logo;
        }

        public void setLogo(String logo) {
            this.logo = logo;
        }

        public int getSort() {
            return sort;
        }

        public void setSort(int sort) {
            this.sort = sort;
        }
    }
}
