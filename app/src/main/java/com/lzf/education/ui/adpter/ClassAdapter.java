package com.lzf.education.ui.adpter;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.lzf.education.R;
import com.lzf.education.base.BaseAdapter;
import com.lzf.education.base.BaseViewHolder;
import com.lzf.education.model.ClassModel;
import com.lzf.education.ui.viewholder.ClassViewHolder;

import java.util.List;

import butterknife.BindView;


/**
 * Created by lzf on 2019/9/21.
 * Describe:
 */

public class ClassAdapter extends BaseAdapter<ClassModel> {


    public ClassAdapter(List<ClassModel> dataList) {
        super(dataList);
    }

    @Override
    public int getLayout(int viewType) {
        return R.layout.item_select_class;
    }

    @Override
    public BaseViewHolder getHolder(View v, int viewType) {
        return new ClassViewHolder(v, viewType);
    }
}
