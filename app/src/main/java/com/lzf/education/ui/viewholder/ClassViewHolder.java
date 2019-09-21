package com.lzf.education.ui.viewholder;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.lzf.education.R;
import com.lzf.education.base.BaseViewHolder;
import com.lzf.education.model.ClassModel;

import java.util.List;

import butterknife.BindView;


/**
 * Created by lzf on 2019/9/21.
 * Describe:
 */

public class ClassViewHolder extends BaseViewHolder<ClassModel> {

    @BindView(R.id.tv_class_name)
    TextView mTvClassName;
    @BindView(R.id.iv_check_box)
    ImageView mIvCheckBox;

    public ClassViewHolder(View itemView, int viewType) {
        super(itemView, viewType);
    }

    @Override
    public void setData(final List<ClassModel> data, final int position) {
        mTvClassName.setText(data.get(position).getName());
        if (data.get(position).isSelect()){
            mIvCheckBox.setBackground(context.getResources().getDrawable(R.drawable.check_box_select));
        }else {
            mIvCheckBox.setBackground(context.getResources().getDrawable(R.drawable.check_box_aaaaaa));
        }
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                data.get(position).setSelect(!data.get(position).isSelect());
                if (data.get(position).isSelect()){
                    mIvCheckBox.setBackground(context.getResources().getDrawable(R.drawable.check_box_select));
                }else {
                    mIvCheckBox.setBackground(context.getResources().getDrawable(R.drawable.check_box_aaaaaa));
                }
            }
        });
    }
}
