package com.nhsoft.check.ui.fragment;


import android.arch.lifecycle.Observer;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.nhsoft.check.BR;
import com.nhsoft.check.R;
import com.nhsoft.check.databinding.FragmentCheckBinding;
import com.nhsoft.check.databinding.PopupSelectClassBinding;
import com.nhsoft.check.utils.CustomPopWindowUtil;
import com.nhsoft.check.viewModel.CheckViewModel;
import com.nhsoft.check.viewModel.PopupViewModel;
import com.nhsoft.pxview.constant.Constant;

import java.util.ArrayList;
import java.util.List;

import priv.lzf.mvvmhabit.base.BaseFragment;
import priv.lzf.mvvmhabit.utils.MaterialDialogUtils;
import priv.lzf.mvvmhabit.widget.CustomPopWindow;

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
        viewModel.setPopupBinding(getContext());
    }

    @Override
    public void initViewObservable() {
        super.initViewObservable();
        viewModel.uc.selectType.observe(this, new Observer<Integer>() {
            @Override
            public void onChanged(@Nullable Integer integer) {
                if (integer.intValue()==1){
                    viewModel.setTitle("选择楼宇");
                }else if (integer.intValue()==2){
                    viewModel.setTitle("选择班级");
                }
                viewModel.setData();
                CustomPopWindowUtil.getInstance().showAtLocationBottomPopupWindow(getContext(),getView());
                CustomPopWindowUtil.getInstance().setAdapter();
            }
        });
    }

    @Override
    public void onStart() {
        super.onStart();
        viewModel.sentInformationMessage();
    }


}
