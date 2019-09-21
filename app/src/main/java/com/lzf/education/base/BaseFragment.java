package com.lzf.education.base;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.lzf.education.constant.Constant;
import com.lzf.education.utils.RelayoutViewTool;

import butterknife.ButterKnife;

/**
 * 作者：Created by 45703
 * 时间：Created on 2019/9/21.
 */
public abstract class BaseFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View mRootView = inflater.inflate(getLayoutId(), container, false);
        RelayoutViewTool.relayoutViewWithScale(mRootView, Constant.mScreenWidthScale);
        return mRootView;
    }



    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ButterKnife.bind(this,view);
        initView();
    }

    protected abstract int getLayoutId() ;

    protected abstract void initView();
}
