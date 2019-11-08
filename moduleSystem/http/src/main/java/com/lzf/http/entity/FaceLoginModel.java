package com.lzf.http.entity;

import java.util.List;

/**
 * 作者：Created by 45703
 * 时间：Created on 2019/11/7.
 */
public class FaceLoginModel {


    /**
     * avatar : null
     * boxes : [{"box_address":"10.201.102.3","box_token":"token","company_id":1,"id":1}]
     * company_id : 1
     * id : 2
     * password_reseted : false
     * role_id : 2
     * screen_token : 29HSPI3412PwrZUM
     * username : a
     */

    private Object avatar;
    private int company_id;
    private int id;
    private boolean password_reseted;
    private int role_id;
    private String screen_token;
    private String username;
    private List<BoxesBean> boxes;

    public Object getAvatar() {
        return avatar;
    }

    public void setAvatar(Object avatar) {
        this.avatar = avatar;
    }

    public int getCompany_id() {
        return company_id;
    }

    public void setCompany_id(int company_id) {
        this.company_id = company_id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public boolean isPassword_reseted() {
        return password_reseted;
    }

    public void setPassword_reseted(boolean password_reseted) {
        this.password_reseted = password_reseted;
    }

    public int getRole_id() {
        return role_id;
    }

    public void setRole_id(int role_id) {
        this.role_id = role_id;
    }

    public String getScreen_token() {
        return screen_token;
    }

    public void setScreen_token(String screen_token) {
        this.screen_token = screen_token;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public List<BoxesBean> getBoxes() {
        return boxes;
    }

    public void setBoxes(List<BoxesBean> boxes) {
        this.boxes = boxes;
    }

    public static class BoxesBean {
        /**
         * box_address : 10.201.102.3
         * box_token : token
         * company_id : 1
         * id : 1
         */

        private String box_address;
        private String box_token;
        private int company_id;
        private int id;

        public String getBox_address() {
            return box_address;
        }

        public void setBox_address(String box_address) {
            this.box_address = box_address;
        }

        public String getBox_token() {
            return box_token;
        }

        public void setBox_token(String box_token) {
            this.box_token = box_token;
        }

        public int getCompany_id() {
            return company_id;
        }

        public void setCompany_id(int company_id) {
            this.company_id = company_id;
        }

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }
    }
}
