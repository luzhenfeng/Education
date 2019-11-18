package com.lzf.http.entity;

/**
 * 作者：Created by 45703
 * 时间：Created on 2019/10/8.
 */
public class LoginModel {

    /**
     * token : {"access_token":"eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJpc3MiOiJlZjY5ZmEyMS03MzU2LTQ5ZjAtYmJjOS00ZTA5MzkxYTY0MWYiLCJuYW1lIjoi5p2O5b-X6b6ZIiwidXNlcnR5cGUiOjIsImlhdCI6MTU3MDU0Mjc0NX0.O8a_MukOrgJn30ojUgawEW8o80CuOjwQBt0g6VkzhJ8","expires_in":604800}
     * user : {"avatar":"","realname":"李志龙","subjectid":0,"userid":"ef69fa21-7356-49f0-bbc9-4e09391a641f","username":"3413404195","usertype":2}
     */

    private TokenBean token;
    private UserBean user;

    public TokenBean getToken() {
        return token;
    }

    public void setToken(TokenBean token) {
        this.token = token;
    }

    public UserBean getUser() {
        return user;
    }

    public void setUser(UserBean user) {
        this.user = user;
    }

    public static class TokenBean {
        /**
         * access_token : eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJpc3MiOiJlZjY5ZmEyMS03MzU2LTQ5ZjAtYmJjOS00ZTA5MzkxYTY0MWYiLCJuYW1lIjoi5p2O5b-X6b6ZIiwidXNlcnR5cGUiOjIsImlhdCI6MTU3MDU0Mjc0NX0.O8a_MukOrgJn30ojUgawEW8o80CuOjwQBt0g6VkzhJ8
         * expires_in : 604800
         */

        private String access_token;
        private int expires_in;

        public String getAccess_token() {
            return access_token;
        }

        public void setAccess_token(String access_token) {
            this.access_token = access_token;
        }

        public int getExpires_in() {
            return expires_in;
        }

        public void setExpires_in(int expires_in) {
            this.expires_in = expires_in;
        }
    }

    public static class UserBean {
        /**
         * avatar :
         * realname : 李志龙
         * subjectid : 0
         * userid : ef69fa21-7356-49f0-bbc9-4e09391a641f
         * username : 3413404195
         * usertype : 2
         */

        private String avatar;
        private String realname;
        private int subjectid;
        private String userid;
        private String username;
        private int usertype;
        private int theme;

        public String getAvatar() {
            return avatar;
        }

        public void setAvatar(String avatar) {
            this.avatar = avatar;
        }

        public String getRealname() {
            return realname;
        }

        public void setRealname(String realname) {
            this.realname = realname;
        }

        public int getSubjectid() {
            return subjectid;
        }

        public void setSubjectid(int subjectid) {
            this.subjectid = subjectid;
        }

        public String getUserid() {
            return userid;
        }

        public void setUserid(String userid) {
            this.userid = userid;
        }

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public int getUsertype() {
            return usertype;
        }

        public void setUsertype(int usertype) {
            this.usertype = usertype;
        }

        public int getTheme() {
            return theme;
        }

        public void setTheme(int theme) {
            this.theme = theme;
        }
    }
}
