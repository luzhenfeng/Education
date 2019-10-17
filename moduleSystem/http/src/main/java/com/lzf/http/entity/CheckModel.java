package com.lzf.http.entity;

import java.util.List;

/**
 * 作者：Created by 45703
 * 时间：Created on 2019/10/13.
 */

public class CheckModel {

    /**
     * mcode : m1-4
     * category : 1
     * cateId : 4001
     * cateName : 的
     * checkDate : 2019-10-02 14:25:17
     * classId : 2
     * className : 233
     * objectId : 34
     * objectName : 233
     * records : [{"id":"001","code":"001","name":"有人吸烟","categoryId":"001001","deducttype":0,"ruletype":1,"score":2,"bednos":"#1,#2"},{"id":"002","code":"002","name":"携带手机","categoryId":"001002","deducttype":0,"ruletype":1,"score":2,"bednos":""}]
     * students : [{"userid":"rr","studentid":1,"studentno":"seer","studentname":"455","classid":"2","classname":"233","bedno":1,"score":2},{"userid":"rr1","studentid":1,"studentno":"seer1","studentname":"454","classid":"2","classname":"233","bedno":2,"score":2}]
     * photos : ["22","33"]
     */

    private String mcode;
    private int category;
    private String cateId;
    private String cateName;
    private String checkDate;
    private String classId;
    private String className;
    private String objectId;
    private String objectName;
    private List<RecordsBean> records;
    private List<StudentsBean> students;
    private List<String> photos;

    public String getMcode() {
        return mcode;
    }

    public void setMcode(String mcode) {
        this.mcode = mcode;
    }

    public int getCategory() {
        return category;
    }

    public void setCategory(int category) {
        this.category = category;
    }

    public String getCateId() {
        return cateId;
    }

    public void setCateId(String cateId) {
        this.cateId = cateId;
    }

    public String getCateName() {
        return cateName;
    }

    public void setCateName(String cateName) {
        this.cateName = cateName;
    }

    public String getCheckDate() {
        return checkDate;
    }

    public void setCheckDate(String checkDate) {
        this.checkDate = checkDate;
    }

    public String getClassId() {
        return classId;
    }

    public void setClassId(String classId) {
        this.classId = classId;
    }

    public String getClassName() {
        return className;
    }

    public void setClassName(String className) {
        this.className = className;
    }

    public String getObjectId() {
        return objectId;
    }

    public void setObjectId(String objectId) {
        this.objectId = objectId;
    }

    public String getObjectName() {
        return objectName;
    }

    public void setObjectName(String objectName) {
        this.objectName = objectName;
    }

    public List<RecordsBean> getRecords() {
        return records;
    }

    public void setRecords(List<RecordsBean> records) {
        this.records = records;
    }

    public List<StudentsBean> getStudents() {
        return students;
    }

    public void setStudents(List<StudentsBean> students) {
        this.students = students;
    }

    public List<String> getPhotos() {
        return photos;
    }

    public void setPhotos(List<String> photos) {
        this.photos = photos;
    }

    public static class RecordsBean {
        /**
         * id : 001
         * code : 001
         * name : 有人吸烟
         * categoryId : 001001
         * deducttype : 0
         * ruletype : 1
         * score : 2.0
         * bednos : #1,#2
         */

        private String id;
        private String code;
        private String name;
        private String categoryId;
        private int deducttype;
        private int ruletype;
        private double score;
        private String bednos;
        private String classId;
        private String className;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

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

        public String getCategoryId() {
            return categoryId;
        }

        public void setCategoryId(String categoryId) {
            this.categoryId = categoryId;
        }

        public int getDeducttype() {
            return deducttype;
        }

        public void setDeducttype(int deducttype) {
            this.deducttype = deducttype;
        }

        public int getRuletype() {
            return ruletype;
        }

        public void setRuletype(int ruletype) {
            this.ruletype = ruletype;
        }

        public double getScore() {
            return score;
        }

        public void setScore(double score) {
            this.score = score;
        }

        public String getBednos() {
            return bednos;
        }

        public void setBednos(String bednos) {
            this.bednos = bednos;
        }

        public String getClassId() {
            return classId;
        }

        public void setClassId(String classId) {
            this.classId = classId;
        }

        public String getClassName() {
            return className;
        }

        public void setClassName(String className) {
            this.className = className;
        }
    }

    public static class StudentsBean {
        /**
         * userid : rr
         * studentid : 1
         * studentno : seer
         * studentname : 455
         * classid : 2
         * classname : 233
         * bedno : 1
         * score : 2.0
         */

        private String userid;
        private int studentid;
        private String studentno;
        private String studentname;
        private String classid;
        private String classname;
        private int bedno;
        private double score;

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

        public String getClassid() {
            return classid;
        }

        public void setClassid(String classid) {
            this.classid = classid;
        }

        public String getClassname() {
            return classname;
        }

        public void setClassname(String classname) {
            this.classname = classname;
        }

        public int getBedno() {
            return bedno;
        }

        public void setBedno(int bedno) {
            this.bedno = bedno;
        }

        public double getScore() {
            return score;
        }

        public void setScore(double score) {
            this.score = score;
        }
    }
}
