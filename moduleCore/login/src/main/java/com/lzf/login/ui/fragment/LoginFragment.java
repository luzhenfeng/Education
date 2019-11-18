package com.lzf.login.ui.fragment;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.lzf.login.BR;
import com.lzf.login.R;

import com.lzf.login.databinding.FragmentLoginBinding;
import com.lzf.login.viewModel.LoginViewModel;

import priv.lzf.mvvmhabit.base.BaseFragment;


/**
 * A simple {@link Fragment} subclass.
 */
public class LoginFragment extends BaseFragment<FragmentLoginBinding,LoginViewModel> {


    @Override
    public int initContentView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return R.layout.fragment_login;
    }

    @Override
    public int initVariableId() {
        return BR.viewModel;
    }

    @Override
    public void initData() {
        super.initData();
        viewModel.getPermission(this);
    }

    @Override
    public void initViewObservable() {
        super.initViewObservable();
    }

    @Override
    public void onResume() {
        super.onResume();
    }
}
