package com.lzf.education.event;

import com.lzf.education.model.CheckTitleModel;

/**
 * Created by lzf on 2019/9/21.
 * Describe:
 */

public class CheckTitleEvent {
    private int position;
    private CheckTitleModel data;

    public CheckTitleEvent(int position, CheckTitleModel data) {
        this.position = position;
        this.data = data;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public CheckTitleModel getData() {
        return data;
    }

    public void setData(CheckTitleModel data) {
        this.data = data;
    }
}
