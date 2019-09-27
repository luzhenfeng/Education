package com.nhsoft.upload.adapter;

import android.databinding.ViewDataBinding;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.nhsoft.pxview.constant.Constant;
import com.nhsoft.pxview.utils.RelayoutViewTool;
import com.nhsoft.upload.databinding.ItemFileBinding;
import com.nhsoft.upload.viewModel.FileItemViewModel;

import me.tatarka.bindingcollectionadapter2.BindingRecyclerViewAdapter;

/**
 * 作者：Created by 45703
 * 时间：Created on 2019/9/26.
 */
public class RecyclerViewBindingAdapter extends BindingRecyclerViewAdapter<FileItemViewModel> {
//    @Override
//    public void onBindBinding(ViewDataBinding binding, int variableId, int layoutRes, int position, FileItemViewModel item) {
//        if (Constant.isScale){
//            RelayoutViewTool.relayoutViewWithScale(binding.getRoot(),Constant.mScreenWidthScale);
//        }
//        super.onBindBinding(binding, variableId, layoutRes, position, item);
//    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewDataBinding binding) {
        if (Constant.isScale){
            RelayoutViewTool.relayoutViewWithScale(binding.getRoot(),Constant.mScreenWidthScale);
        }
        return super.onCreateViewHolder(binding);
    }

//    @Override
//    public ViewDataBinding onCreateBinding(LayoutInflater inflater, int layoutId, ViewGroup viewGroup) {
//        if (Constant.isScale){
//            RelayoutViewTool.relayoutViewWithScale(inflater.inflate(layoutId,viewGroup,false),Constant.mScreenWidthScale);
//        }
//        return super.onCreateBinding(inflater, layoutId, viewGroup);
//    }
}
