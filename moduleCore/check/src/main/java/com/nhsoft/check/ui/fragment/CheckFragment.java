package com.nhsoft.check.ui.fragment;


import android.arch.lifecycle.Observer;
import android.content.DialogInterface;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import com.afollestad.materialdialogs.MaterialDialog;
import com.google.gson.Gson;
import com.lzf.takephoto.model.TResult;
import com.nhsoft.check.BR;
import com.nhsoft.check.R;
import com.nhsoft.check.databinding.DialogSuccessBinding;
import com.nhsoft.check.databinding.FragmentCheckBinding;
import com.nhsoft.check.utils.CustomPopWindowUtil;
import com.nhsoft.check.viewModel.CheckViewModel;
import com.nhsoft.pxview.constant.Constant;
import com.nhsoft.utils.utils.DateUtil;

import priv.lzf.mvvmhabit.base.BaseFragment;
import priv.lzf.mvvmhabit.utils.KLog;
import priv.lzf.mvvmhabit.utils.MaterialDialogUtils;
import priv.lzf.mvvmhabit.utils.SPUtils;

/**
 * A simple {@link Fragment} subclass.
 */
public class CheckFragment extends BaseFragment<FragmentCheckBinding, CheckViewModel> {

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
    }

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
                viewModel.entity.get().date.set(DateUtil.getCurrentTime());
                if (integer.intValue()==1){
                    viewModel.setTitle("选择楼宇");
                    CustomPopWindowUtil.getInstance().setData(binding.tvFloor.getText().toString(),viewModel.mFloorNameList);
                    viewModel.mPopupViewModel.selectPos.set(viewModel.mFloorNameList.indexOf(binding.tvFloor.getText().toString()));
                    CustomPopWindowUtil.getInstance().showAtBottomPopupWindow(getContext(),getView(),(int) (172* Constant.mScreenWidthScale));
                }else if (integer.intValue()==2){
                    viewModel.setTitle("选择班级");
                    CustomPopWindowUtil.getInstance().setData(binding.tvRoom.getText().toString(),viewModel.mRoomNameList);
                    viewModel.mPopupViewModel.selectPos.set(viewModel.mRoomNameList.indexOf(binding.tvRoom.getText().toString()));
                    CustomPopWindowUtil.getInstance().showAtLocationBottomPopupWindow(getContext(),getView());
                }
                CustomPopWindowUtil.getInstance().setAdapter();
            }
        });
        viewModel.uc.face.observe(this, new Observer() {
            @Override
            public void onChanged(@Nullable Object o) {
                viewModel.getPermission(CheckFragment.this);
            }
        });

        viewModel.uc.success.observe(this, new Observer() {
            @Override
            public void onChanged(@Nullable Object o) {
                View view= LayoutInflater.from(getContext()).inflate(R.layout.dialog_success,null);
                final DialogSuccessBinding binding = DataBindingUtil.bind(view);
                final MaterialDialog dialog=MaterialDialogUtils.showCustomDialog(getContext(),view).build();
                dialog.show();
                dialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
                    @Override
                    public void onDismiss(DialogInterface dialog) {
                        binding.unbind();
                    }
                });
                binding.ivCancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });
                binding.btnOk.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        dialog.dismiss();
                    }
                });
            }
        });
//        viewModel.uc.takePhoto.observe(this, new Observer() {
//            @Override
//            public void onChanged(@Nullable Object o) {
////                showCutPopup(9);
//            }
//        });
    }

    @Override
    public void onStart() {
        super.onStart();
        if (viewModel.isFirst.get()){
            viewModel.isFirst.set(false);
            viewModel.sentInformationMessage();
        }
    }

}
