package com.lzf.education.ui.view;

import android.content.Context;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.lzf.education.R;
import com.lzf.education.constant.Constant;
import com.lzf.education.utils.RelayoutViewTool;



/**
 * 作者：Created by 45703
 * 时间：Created on 2019/9/19.
 */
@RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
public class MyActionBar extends RelativeLayout {
    private ImageView iv_back;
    private TextView tv_name;
    private TextView tv_right;

    private Context mContext;
    public MyActionBar(Context context) {
        this(context,null);
    }

    public MyActionBar(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public MyActionBar(Context context, AttributeSet attrs, int defStyleAttr) {
        this(context, attrs, defStyleAttr,0);
    }

    public MyActionBar(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        mContext=context;
        initView();
    }


    public void initView(){
        View mView = View.inflate(mContext, R.layout.layout_actionbar,this);
        iv_back=mView.findViewById(R.id.iv_back);
        tv_name=mView.findViewById(R.id.tv_name);
        tv_right=mView.findViewById(R.id.tv_right);
    }

    public MyActionBar setName(String name){
        tv_name.setText(name);
        return this;
    }

    public MyActionBar setRightText(String rightText){
        tv_right.setText(rightText);
        return this;
    }

    public void setOnClickBack(OnClickListener onClickBack){
        iv_back.setOnClickListener(onClickBack);
    }

    public void setOnClickRight(OnClickListener onClickRight){
        tv_right.setOnClickListener(onClickRight);
    }

}
