package com.nhsoft.upload.entity;

import com.lzf.greendao.entity.ChecksModel;

/**
 * 作者：Created by 45703
 * 时间：Created on 2019/9/20.
 */
public class UploadEntity {
    private long id;
    private String text1;
    private String text2;
    private String text3;
    private String text4;
    private boolean isSelect;
    private boolean isUpload;
    private ChecksModel mChecksModel;

    public boolean isUpload() {
        return isUpload;
    }

    public void setUpload(boolean upload) {
        isUpload = upload;
    }

    public String getText4() {
        return text4;
    }

    public void setText4(String text4) {
        this.text4 = text4;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getText1() {
        return text1;
    }

    public void setText1(String text1) {
        this.text1 = text1;
    }

    public String getText2() {
        return text2;
    }

    public void setText2(String text2) {
        this.text2 = text2;
    }

    public String getText3() {
        return text3;
    }

    public void setText3(String text3) {
        this.text3 = text3;
    }

    public boolean isSelect() {
        return isSelect;
    }

    public void setSelect(boolean select) {
        isSelect = select;
    }

    public ChecksModel getChecksModel() {
        return mChecksModel;
    }

    public void setChecksModel(ChecksModel checksModel) {
        mChecksModel = checksModel;
    }
}
