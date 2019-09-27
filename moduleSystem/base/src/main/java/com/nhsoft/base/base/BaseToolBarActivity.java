package com.nhsoft.base.base;

import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.v4.app.Fragment;

import com.nhsoft.base.BR;
import com.nhsoft.base.R;

import priv.lzf.mvvmhabit.base.BaseActivity;
import priv.lzf.mvvmhabit.base.BaseViewModel;

public abstract class BaseToolBarActivity<V extends ViewDataBinding, VM extends BaseViewModel> extends BaseActivity<V, VM> {

    private Fragment mFragment;
    @Override
    public int initContentView(Bundle savedInstanceState) {
        return R.layout.activity_base_tool_bar;
    }

    @Override
    public int initVariableId() {
        return BR.viewModel;
    }

    @Override
    public void initData() {
        super.initData();
        mFragment=initFragment();
        getSupportFragmentManager().beginTransaction().add(R.id.frame,mFragment).commit();
        initToolBar();
    }

    protected abstract void initToolBar();

    protected abstract Fragment initFragment();
}
