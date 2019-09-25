package com.nhsoft.upload.viewModel;

import android.app.Application;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.view.View;

import com.nhsoft.base.viewModel.ToolbarViewModel;

/**
 * Created by lzf on 2019/9/25.
 * Describe:
 */

public class UploadViewModel extends ToolbarViewModel {


    public UploadViewModel(@NonNull Application application) {
        super(application);
    }

    /**
     * 初始化Toolbar
     */
    public void initToolbar() {
        //初始化标题栏
        setRightTextVisible(View.VISIBLE);
        setTitleText("上传文件");
        setRightText("编辑");
    }
}
