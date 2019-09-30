package com.nhsoft.check.ui.fragment;


import android.arch.lifecycle.Observer;
import android.databinding.Observable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.nhsoft.base.base.adapter.RecyclerViewBindingAdapter;
import com.nhsoft.check.BR;
import com.nhsoft.check.R;
import com.nhsoft.check.databinding.FragmentCheckInteriorBinding;
import com.nhsoft.check.viewModel.CheckInteriorViewModel;
import com.nhsoft.pxview.constant.Constant;

import priv.lzf.mvvmhabit.base.BaseFragment;
import priv.lzf.mvvmhabit.widget.CustomPopWindow;

/**
 * 内务检查
 */
public class CheckInteriorFragment extends BaseFragment<FragmentCheckInteriorBinding, CheckInteriorViewModel> {


    @Override
    public int initContentView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return R.layout.fragment_check_interior;
    }

    @Override
    public int initVariableId() {
        return BR.viewModel;
    }

    @Override
    public void initData() {
        super.initData();
        binding.setAdapter(new RecyclerViewBindingAdapter());
        viewModel.initLeftItem();
        binding.setRightAdapter(new RecyclerViewBindingAdapter());
        viewModel.initRightItem();
//        viewModel.registerMessenger();
    }

    @Override
    public void initViewObservable() {
        super.initViewObservable();
//        viewModel.uc.showSelectClassPopupWindow.addOnPropertyChangedCallback(new Observable.OnPropertyChangedCallback() {
//            @Override
//            public void onPropertyChanged(Observable sender, int propertyId) {
//
//            }
//        });
        viewModel.uc.type.observe(this, new Observer<Integer>() {
            @Override
            public void onChanged(@Nullable Integer integer) {
//                if (integer.intValue()==1){
                    new CustomPopWindow.PopupWindowBuilder(getContext())
                            .setView(R.layout.popup_select_class)
                            .size(ConstraintLayout.LayoutParams.MATCH_PARENT,ConstraintLayout.LayoutParams.WRAP_CONTENT)
                            .enableBackgroundDark(true) //弹出popWindow时，背景是否变暗
                            .setBgDarkAlpha(0.7f) // 控制亮度
                            .create()
                            .showAtLocation(getView(), Gravity.BOTTOM, 0,(int)(Constant.mScreenWidthScale * 252));
//                }
            }
        });
    }
}
