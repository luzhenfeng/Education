package com.lzf.login.ui.fragment;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.lzf.login.BR;
import com.lzf.login.R;
import com.lzf.login.databinding.FragmentSelectMcodeBinding;
import com.lzf.login.viewModel.SelectMCodeViewModel;
import com.nhsoft.base.base.adapter.RecyclerViewBindingAdapter;

import priv.lzf.mvvmhabit.base.BaseFragment;

/**
 * A simple {@link Fragment} subclass.
 */
public class SelectMCodeFragment extends BaseFragment<FragmentSelectMcodeBinding, SelectMCodeViewModel> {

    @Override
    public int initContentView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return R.layout.fragment_select_mcode;
    }

    @Override
    public int initVariableId() {
        return BR.viewModel;
    }

    @Override
    public void initData() {
        super.initData();
        viewModel.initReceiver(getContext());
        binding.setAdapter(new RecyclerViewBindingAdapter());
        viewModel.initData();
//        getActivity().runOnUiThread(new Runnable() {
//            @Override
//            public void run() {
        viewModel.setHead(binding.ivHead);
//            }
//        });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        viewModel.relese(getContext());
    }
}
