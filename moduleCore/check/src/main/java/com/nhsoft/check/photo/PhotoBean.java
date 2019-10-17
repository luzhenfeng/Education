package com.nhsoft.check.photo;

/**
 * Created by NBMY on 2018/10/10.
 */

public class PhotoBean {
    private String imagePath;
    private boolean showError=true;//是否显示错号
    private boolean isClick;//是否能点击
    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public boolean isShowError() {
        return showError;
    }

    public void setShowError(boolean showError) {
        this.showError = showError;
    }

    public boolean isClick() {
        return isClick;
    }

    public void setClick(boolean click) {
        isClick = click;
    }
}
