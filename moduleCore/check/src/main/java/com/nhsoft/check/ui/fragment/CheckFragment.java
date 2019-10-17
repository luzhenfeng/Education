package com.nhsoft.check.ui.fragment;


import android.arch.lifecycle.Observer;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import com.google.gson.Gson;
import com.lzf.takephoto.model.TResult;
import com.nhsoft.check.BR;
import com.nhsoft.check.R;
import com.nhsoft.check.databinding.FragmentCheckBinding;
import com.nhsoft.check.utils.CustomPopWindowUtil;
import com.nhsoft.check.viewModel.CheckViewModel;
import com.nhsoft.utils.utils.DateUtil;

import priv.lzf.mvvmhabit.base.BaseFragment;
import priv.lzf.mvvmhabit.utils.KLog;
import priv.lzf.mvvmhabit.utils.SPUtils;

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
        SPUtils.getInstance().put("photos","");
        SPUtils.getInstance().put("realityNum",0);
    }

    @Override
    public void initViewObservable() {
        super.initViewObservable();
        viewModel.uc.selectType.observe(this, new Observer<Integer>() {
            @Override
            public void onChanged(@Nullable Integer integer) {
                viewModel.entity.get().date.set(DateUtil.getCurrentTime());
                if (integer.intValue()==1){
                    viewModel.setTitle("选择楼宇");
                    CustomPopWindowUtil.getInstance().setData(binding.tvFloor.getText().toString(),viewModel.mFloorNameList);
                    viewModel.mPopupViewModel.selectPos.set(viewModel.mFloorNameList.indexOf(binding.tvFloor.getText().toString()));
                }else if (integer.intValue()==2){
                    viewModel.setTitle("选择班级");
                    CustomPopWindowUtil.getInstance().setData(binding.tvRoom.getText().toString(),viewModel.mRoomNameList);
                    viewModel.mPopupViewModel.selectPos.set(viewModel.mRoomNameList.indexOf(binding.tvRoom.getText().toString()));
                }
                CustomPopWindowUtil.getInstance().showAtLocationBottomPopupWindow(getContext(),getView());
                CustomPopWindowUtil.getInstance().setAdapter();
            }
        });
        viewModel.uc.takePhoto.observe(this, new Observer() {
            @Override
            public void onChanged(@Nullable Object o) {
//                showCutPopup(9);
            }
        });
    }

    @Override
    public void onStart() {
        super.onStart();
        viewModel.sentInformationMessage();
    }

    @Override
    public void onResume() {
        super.onResume();
        if (SPUtils.getInstance().getInt("realityNum")>0){
            viewModel.entity.get().showCameraNum.set(View.VISIBLE);
            viewModel.entity.get().cameraNum.set(String.valueOf(SPUtils.getInstance().getInt("realityNum")));
        }
    }
}
