package com.nhsoft.check.photo;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2018/4/3.
 */

public abstract class BaseAdapter<T> extends RecyclerView.Adapter<BaseViewHolder<T>> {
    List<T> dataList=new ArrayList<>();
    public Context context;
    public BaseAdapter(List<T> dataList){
        this.dataList=dataList;
    }
    public int viewType;
    protected BaseViewHolder.OnViewClickListener mOnViewClickListener = null;
    protected BaseViewHolder.OnViewLongClickListener mOnViewLongClickListener;


    @Override
    public BaseViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(getLayout(viewType), parent, false);
        context=parent.getContext();
        BaseViewHolder holder = getHolder(view,viewType);
        holder.setContext(context);
        holder.setOnItemClickListener(mOnViewClickListener);
        holder.setOnViewLongClickListener(mOnViewLongClickListener);
        holder.setNotifyChange(new BaseViewHolder.NotifyChange() {
            @Override
            public void notifyChange() {
                notifyDataSetChanged();
            }
        });
        return holder;
    }

    @Override
    public void onBindViewHolder(BaseViewHolder holder, int position) {
        holder.setData(dataList,position);
    }
    public void setOnViewLongClickListener(BaseViewHolder.OnViewLongClickListener listener) {
        this.mOnViewLongClickListener = listener;
    }
    public void setOnItemClickListener(BaseViewHolder.OnViewClickListener listener) {
        this.mOnViewClickListener = listener;
    }

    @Override
    public int getItemViewType(int position) {
        return viewType;
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }
    public void setData(List<T> dataList){
        this.dataList=dataList;
    }


    public abstract int getLayout(int viewType);
    public abstract BaseViewHolder getHolder(View v, int viewType);
}
