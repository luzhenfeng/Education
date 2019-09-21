package com.lzf.education.event;

import com.lzf.education.model.CheckItemModel;

/**
 * Created by lzf on 2019/9/21.
 * Describe:
 */

public class CheckItemEvent {
    private int position;
    private CheckItemModel data;

    public CheckItemEvent(int position, CheckItemModel data) {
        this.position = position;
        this.data = data;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public CheckItemModel getData() {
        return data;
    }

    public void setData(CheckItemModel data) {
        this.data = data;
    }
}
