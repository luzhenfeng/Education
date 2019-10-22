package com.lzf.login.ui.fragment;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.lzf.login.BR;
import com.lzf.login.R;
import com.lzf.login.databinding.FragmentRegisterBinding;
import com.lzf.login.viewModel.RegisterViewModel;

import priv.lzf.mvvmhabit.base.BaseFragment;

/**
 * A simple {@link Fragment} subclass.
 */
public class RegisterFragment extends BaseFragment<FragmentRegisterBinding, RegisterViewModel> {


    @Override
    public int initContentView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return R.layout.fragment_register;
    }

    @Override
    public int initVariableId() {
        return BR.viewModel;
    }

}
