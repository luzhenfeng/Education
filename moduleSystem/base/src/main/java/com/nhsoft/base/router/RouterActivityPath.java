package com.nhsoft.base.router;

/**
 * 用于组件开发中，ARouter单Activity跳转的统一路径注册
 * 在这里注册添加路由路径，需要清楚的写好注释，标明功能界面
 * Created by goldze on 2018/6/21
 */

public class RouterActivityPath {
//    /**
//     * 主业务组件
//     */
//    public static class Main {
//        private static final String MAIN = "/main";
//        /*主业务界面*/
//        public static final String PAGER_MAIN = MAIN +"/Main";
//    }
//
//    /**
//     * 身份验证组件
//     */
//    public static class Sign {
//        private static final String SIGN = "/sign";
//        /*登录界面*/
//        public static final String PAGER_LOGIN = SIGN + "/Login";
//    }
//
//    /**
//     * 用户组件
//     */
//    public static class User {
//        private static final String USER = "/user";
//        /*用户详情*/
//        public static final String PAGER_USERDETAIL = USER + "/UserDetail";
//    }

    /**
     * 上传文件组件
     */
    public static class Upload{
        private static final String UPLOAD = "/upload";
        /*上传文件界面*/
        public static final String PAGER_UPLOAD = UPLOAD + "/upload";
    }

    /**
     * 常规检查组件
     */
    public static class Check{
        private static final String CHECK = "/check";
        /*上传文件界面*/
        public static final String PAGER_CHECK = CHECK + "/check";
        /*上传文件界面*/
        public static final String PAGER_PHOTO = CHECK + "/photo";
    }

    /**
     * 登录检查组件
     */
    public static class Login{
        private static final String LOGIN = "/login";
        /*登录界面*/
        public static final String PAGER_LOGIN = LOGIN + "/login";
    }
}
