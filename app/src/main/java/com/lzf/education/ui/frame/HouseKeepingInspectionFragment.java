package com.lzf.education.ui.frame;


import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.lzf.education.R;
import com.lzf.education.constant.Constant;
import com.lzf.education.event.CheckItemEvent;
import com.lzf.education.event.CheckTitleEvent;
import com.lzf.education.model.CheckItemModel;
import com.lzf.education.model.CheckTitleModel;
import com.lzf.education.utils.RelayoutViewTool;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * A simple {@link Fragment} subclass.
 */
public class HouseKeepingInspectionFragment extends BaseCheckFragment {

    @BindView(R.id.tv_bed1)
    TextView mTvBed1;
    @BindView(R.id.tv_bed2)
    TextView mTvBed2;
    @BindView(R.id.tv_bed3)
    TextView mTvBed3;
    @BindView(R.id.tv_bed4)
    TextView mTvBed4;
    @BindView(R.id.tv_bed5)
    TextView mTvBed5;
    @BindView(R.id.tv_bed6)
    TextView mTvBed6;
    @BindView(R.id.tv_bed7)
    TextView mTvBed7;
    @BindView(R.id.tv_bed8)
    TextView mTvBed8;
    @BindView(R.id.tv_bed9)
    TextView mTvBed9;
    @BindView(R.id.tv_bed10)
    TextView mTvBed10;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_house_keeping_inspection;
    }

    private String[] mCheckTitleModels = {"地面及卫生工具", "门窗电器", "床上", "床下", "脸盆架", "鞋/鞋架/箱架", "其它"};

    @Override
    protected void initView() {
        super.initView();
        setCheckTitleModels();
        setmCheckItemModelList();
    }

    private void setCheckTitleModels() {
        mCheckTitleModelList.clear();
        for (String s : mCheckTitleModels) {
            CheckTitleModel checkTitleModel = new CheckTitleModel();
            checkTitleModel.setTitle(s);
            checkTitleModel.setDrawable(R.drawable.inspect7);
            checkTitleModel.setId(1);
            mCheckTitleModelList.add(checkTitleModel);
        }
        mCheckTitleAdapter.setData(mCheckTitleModelList);
        mCheckTitleAdapter.notifyDataSetChanged();
    }

    private void setmCheckItemModelList() {
        mCheckItemModelList.clear();
        for (int i = 0; i < 4; i++) {
            CheckItemModel checkItemModel = new CheckItemModel();
            checkItemModel.setItemContent("地面有垃圾" + (i + 1));
            checkItemModel.setItemClass("机电" + (i + 1));
            mCheckItemModelList.add(checkItemModel);
        }
        mCheckItemAdapter.notifyDataSetChanged();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void getCheckTitleEvent(CheckTitleEvent event) {
        mCheckTitleAdapter.notifyDataSetChanged();
    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void getCheckItemEvent(CheckItemEvent event) {
        showPopuWindow();
    }

    @OnClick({R.id.tv_bed1, R.id.tv_bed2, R.id.tv_bed3, R.id.tv_bed4, R.id.tv_bed5, R.id.tv_bed6, R.id.tv_bed7, R.id.tv_bed8, R.id.tv_bed9, R.id.tv_bed10})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_bed1:
                mTvBed1.setSelected(!mTvBed1.isSelected());
                break;
            case R.id.tv_bed2:
                mTvBed2.setSelected(!mTvBed2.isSelected());
                break;
            case R.id.tv_bed3:
                mTvBed3.setSelected(!mTvBed3.isSelected());
                break;
            case R.id.tv_bed4:
                mTvBed4.setSelected(!mTvBed4.isSelected());
                break;
            case R.id.tv_bed5:
                mTvBed5.setSelected(!mTvBed5.isSelected());
                break;
            case R.id.tv_bed6:
                mTvBed6.setSelected(!mTvBed6.isSelected());
                break;
            case R.id.tv_bed7:
                mTvBed7.setSelected(!mTvBed7.isSelected());
                break;
            case R.id.tv_bed8:
                mTvBed8.setSelected(!mTvBed8.isSelected());
                break;
            case R.id.tv_bed9:
                mTvBed9.setSelected(!mTvBed9.isSelected());
                break;
            case R.id.tv_bed10:
                mTvBed10.setSelected(!mTvBed10.isSelected());
                break;
        }
    }




}
