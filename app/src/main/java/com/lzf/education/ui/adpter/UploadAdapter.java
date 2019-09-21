package com.lzf.education.ui.adpter;

import android.view.View;

import com.lzf.education.R;
import com.lzf.education.base.BaseAdapter;
import com.lzf.education.base.BaseViewHolder;
import com.lzf.education.model.UploadModel;
import com.lzf.education.ui.viewholder.UploadViewHolder;

import java.util.List;

/**
 * 作者：Created by 45703
 * 时间：Created on 2019/9/20.
 */
public class UploadAdapter extends BaseAdapter<UploadModel> {

    public UploadAdapter(List<UploadModel> dataList) {
        super(dataList);
    }

    @Override
    public int getLayout(int viewType) {
        return R.layout.item_file;
    }

    @Override
    public BaseViewHolder getHolder(View v, int viewType) {
        return new UploadViewHolder(v,viewType);
    }
}
