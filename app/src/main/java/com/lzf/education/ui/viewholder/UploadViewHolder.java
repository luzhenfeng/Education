package com.lzf.education.ui.viewholder;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.lzf.education.R;
import com.lzf.education.base.BaseViewHolder;
import com.lzf.education.model.UploadModel;

import java.util.List;

import butterknife.ButterKnife;
import butterknife.BindView;
/**
 * 作者：Created by 45703
 * 时间：Created on 2019/9/20.
 */
public class UploadViewHolder extends BaseViewHolder<UploadModel> {

    @BindView(R.id.tv_class)
    TextView tv_class;
    @BindView(R.id.tv_check_type)
    TextView tv_check_type;
    @BindView(R.id.tv_content)
    TextView tv_content;
    @BindView(R.id.tv_type)
    TextView tv_type;
    @BindView(R.id.iv_check_box)
    ImageView iv_check_box;

    public UploadViewHolder(View itemView, int viewType) {
        super(itemView, viewType);
    }

    @Override
    public void setData(final List<UploadModel> data, final int position) {
        tv_class.setText(data.get(position).getText1());
        tv_check_type.setText(data.get(position).getText2());
        tv_content.setText(data.get(position).getText3());
        tv_type.setText(data.get(position).getText4());
        if (data.get(position).isSelect()){
            iv_check_box.setImageDrawable(context.getResources().getDrawable(R.drawable.check_box_select));
        }else {
            iv_check_box.setImageDrawable(context.getResources().getDrawable(R.drawable.check_box_aaaaaa));
        }
        iv_check_box.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                iv_check_box.setSelected(iv_check_box.isSelected());
                data.get(position).setSelect(!data.get(position).isSelect());
                if (data.get(position).isSelect()){
                    iv_check_box.setImageDrawable(context.getResources().getDrawable(R.drawable.check_box_select));
                }else {
                    iv_check_box.setImageDrawable(context.getResources().getDrawable(R.drawable.check_box_aaaaaa));
                }
            }
        });
    }
}
