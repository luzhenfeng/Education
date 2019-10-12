package com.nhsoft.check.utils;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.support.constraint.ConstraintLayout;
import android.support.v4.content.ContextCompat;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupWindow;

import com.nhsoft.base.base.adapter.RecyclerViewBindingAdapter;
import com.nhsoft.check.R;
import com.nhsoft.check.databinding.PopupSelectClassBinding;
import com.nhsoft.check.entity.PopupItemViewEntity;
import com.nhsoft.check.viewModel.PopupItemViewModel;
import com.nhsoft.check.viewModel.PopupViewModel;
import com.nhsoft.pxview.constant.Constant;
import com.nhsoft.pxview.utils.RelayoutViewTool;

import java.util.List;

import priv.lzf.mvvmhabit.base.BaseApplication;
import priv.lzf.mvvmhabit.widget.CustomPopWindow;

/**
 * 作者：Created by 45703
 * 时间：Created on 2019/10/11.
 */
public class CustomPopWindowUtil {

    private static CustomPopWindowUtil instant;

    private CustomPopWindowUtil(){}

    private PopupSelectClassBinding binding;

    private CustomPopWindow mCustomPopWindow;

    public static CustomPopWindowUtil getInstance(){
        if (instant==null){
            synchronized (CustomPopWindowUtil.class){
                if (instant==null){
                    instant=new CustomPopWindowUtil();
                }
            }
        }
        return instant;
    }

    public PopupSelectClassBinding getViewDataBinding(Context context){
        if (binding==null){
            View view= LayoutInflater.from(context).inflate(R.layout.popup_select_class,null);
            binding= DataBindingUtil.bind(view);
        }
        return binding;
    }

    public void showAtLocationBottomPopupWindow(Context context, View parent){
        if (Constant.isScale&&mCustomPopWindow==null){
            RelayoutViewTool.relayoutViewWithScale(binding.getRoot(), Constant.mScreenWidthScale);
        }
        mCustomPopWindow=new CustomPopWindow.PopupWindowBuilder(context)
                .setView(binding.getRoot())
                .size(ConstraintLayout.LayoutParams.MATCH_PARENT,ConstraintLayout.LayoutParams.WRAP_CONTENT)
                .enableBackgroundDark(true) //弹出popWindow时，背景是否变暗
                .setBgDarkAlpha(0.7f) // 控制亮度
                .setOnDissmissListener(new PopupWindow.OnDismissListener() {
                    @Override
                    public void onDismiss() {
                        binding.unbind();
                    }
                })
                .create()
                .showAtLocation(parent, Gravity.BOTTOM, 0,(int)(Constant.mScreenWidthScale * 252));
    }

    public void dismiss(){
        if (mCustomPopWindow!=null){
            mCustomPopWindow.dissmiss();
        }
    }

    public void setAdapter(){
        if (binding!=null)
            binding.setAdapter(new RecyclerViewBindingAdapter());
    }

    public void setData(String name,List<String> stringList){
        if (binding!=null){
            binding.getViewModel().observableList.clear();
            for (String s:stringList){
                PopupItemViewEntity entity=new PopupItemViewEntity();
                entity.text.set(s);
                if (name.equals(s)){
                    entity.selectState= ContextCompat.getDrawable(BaseApplication.getInstance(), R.drawable.check_box_select);
                }else {
                    entity.selectState= ContextCompat.getDrawable(BaseApplication.getInstance(), R.drawable.check_box_aaaaaa);
                }
                PopupItemViewModel popupItemViewModel=new PopupItemViewModel(binding.getViewModel(),entity);
                binding.getViewModel().observableList.add(popupItemViewModel);
            }
        }
    }

}
