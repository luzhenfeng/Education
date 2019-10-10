package com.nhsoft.check.ui.fragment;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.nhsoft.check.BR;
import com.nhsoft.check.R;
import com.nhsoft.check.databinding.FragmentCheckBinding;
import com.nhsoft.check.viewModel.CheckViewModel;

import priv.lzf.mvvmhabit.base.BaseFragment;

/**
 * A simple {@link Fragment} subclass.
 */
public class CheckFragment extends BaseFragment<FragmentCheckBinding, CheckViewModel> {

    @Override
    public int initContentView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return R.layout.fragment_check;
    }

    @Override
    public int initVariableId() {
        return BR.viewModel;
    }

    @Override
    public void initData() {
        super.initData();
        getActivity().getSupportFragmentManager().beginTransaction().add(R.id.frame_check,new CheckListFragment()).commit();
    }
}
