package com.lzf.education.base;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.lzf.education.constant.Constant;
import com.lzf.education.utils.RelayoutViewTool;

import butterknife.ButterKnife;


/**
 * 作者：Created by 45703
 * 时间：Created on 2019/9/19.
 */
public abstract class BaseActivity extends AppCompatActivity {
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getLayout());
        ButterKnife.bind(this);
        initView();
    }

    @Override
    public void setContentView(View view) {
        if (Constant.isScale){
            RelayoutViewTool.relayoutViewWithScale(view,Constant.mScreenWidthScale);
        }
        super.setContentView(view);
    }

    @Override
    public void setContentView(int layoutResID) {
        View mView = View.inflate(this, layoutResID, null);
        this.setContentView(mView);
    }

    protected abstract int getLayout();


    protected abstract void initView();
}
