package com.lzf.http.entity;

import java.util.List;

/**
 * Created by lzf on 2019/10/9.
 * Describe:
 */

public class AllCategoryModel {

    /**
     * mcode : m1-1
     * id : 9000
     * name : 妨碍检查
     * category : 0
     * sort : 10
     * showperson : true
     * chidrens : [{"id":"9001001","name":"常用"}]
     * items : [{"id":"c9205022-af1d-4871-9a1e-33f6fc6e375b","code":"001","name":"当检查人员进教室检查时，出现不尊重检查人员，以各种方式不配合检查（如故意挡路，无故拒绝检查等）","categoryId":"9001001","deducttype":0,"ruletype":1,"score":2},{"id":"ec41574d-63ff-4c44-a8c4-0fd220546c15","code":"002","name":"用其他不礼貌的方式对待检查人员（如全班性起哄，辱骂检查人员等）","categoryId":"9001001","deducttype":0,"ruletype":1,"score":2}]
     */

    private String mcode;
    private String id;
    private String name;
    private int category;
    private int sort;
    private boolean showperson;
    private List<ChidrensBean> chidrens;
    private List<ItemsBean> items;

    public String getMcode() {
        return mcode;
    }

    public void setMcode(String mcode) {
        this.mcode = mcode;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getCategory() {
        return category;
    }

    public void setCategory(int category) {
        this.category = category;
    }

    public int getSort() {
        return sort;
    }

    public void setSort(int sort) {
        this.sort = sort;
    }

    public boolean isShowperson() {
        return showperson;
    }

    public void setShowperson(boolean showperson) {
        this.showperson = showperson;
    }

    public List<ChidrensBean> getChidrens() {
        return chidrens;
    }

    public void setChidrens(List<ChidrensBean> chidrens) {
        this.chidrens = chidrens;
    }

    public List<ItemsBean> getItems() {
        return items;
    }

    public void setItems(List<ItemsBean> items) {
        this.items = items;
    }

    public static class ChidrensBean {
        /**
         * id : 9001001
         * name : 常用
         */

        private String id;
        private String name;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }

    public static class ItemsBean {
        /**
         * id : c9205022-af1d-4871-9a1e-33f6fc6e375b
         * code : 001
         * name : 当检查人员进教室检查时，出现不尊重检查人员，以各种方式不配合检查（如故意挡路，无故拒绝检查等）
         * categoryId : 9001001
         * deducttype : 0
         * ruletype : 1
         * score : 2.0
         */

        private String id;
        private String code;
        private String name;
        private String categoryId;
        private int deducttype;
        private int ruletype;
        private double score;

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
    }
}
