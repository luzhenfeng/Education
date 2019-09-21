package com.lzf.education.ui.frame;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.lzf.education.R;
import com.lzf.education.event.CheckItemEvent;
import com.lzf.education.event.CheckTitleEvent;
import com.lzf.education.model.CheckItemModel;
import com.lzf.education.model.CheckTitleModel;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * A simple {@link Fragment} subclass.
 */
public class RoutineFragment extends BaseCheckFragment {

    @BindView(R.id.tv_student_name)
    TextView mTvStudentName;

    private String[] mCheckTitleModels={"常规"};

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_routine;
    }

    @Override
    protected void initView() {
        super.initView();
        setCheckTitleModels();
        setmCheckItemModelList();
    }


    @OnClick(R.id.iv_add_student)
    public void onViewClicked() {
        Toast.makeText(getContext(),"添加学生",Toast.LENGTH_LONG).show();
    }

    private void setCheckTitleModels(){
        mCheckTitleModelList.clear();
        for (String s:mCheckTitleModels){
            CheckTitleModel checkTitleModel=new CheckTitleModel();
            checkTitleModel.setTitle(s);
            checkTitleModel.setDrawable(R.drawable.inspect7);
            checkTitleModel.setId(1);
            mCheckTitleModelList.add(checkTitleModel);
        }
        mCheckTitleAdapter.setData(mCheckTitleModelList);
        mCheckTitleAdapter.notifyDataSetChanged();
    }

    private void setmCheckItemModelList(){
        mCheckItemModelList.clear();
        for (int i=0;i<4;i++){
            CheckItemModel checkItemModel=new CheckItemModel();
            checkItemModel.setItemContent("地面有垃圾"+(i+1));
            checkItemModel.setItemClass("机电"+(i+1));
            mCheckItemModelList.add(checkItemModel);
        }
        mCheckItemAdapter.notifyDataSetChanged();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void getCheckTitleEvent(CheckTitleEvent event){
        mCheckTitleAdapter.notifyDataSetChanged();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void getCheckItemEvent(CheckItemEvent event) {
        showPopuWindow();
    }

}
