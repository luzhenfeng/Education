package com.lzf.education.ui.frame;

import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.lzf.education.R;
import com.lzf.education.base.BaseFragment;
import com.lzf.education.constant.Constant;
import com.lzf.education.model.CheckItemModel;
import com.lzf.education.model.CheckTitleModel;
import com.lzf.education.model.ClassModel;
import com.lzf.education.ui.adpter.CheckItemAdapter;
import com.lzf.education.ui.adpter.CheckTitleAdapter;
import com.lzf.education.ui.adpter.ClassAdapter;
import com.lzf.education.utils.RelayoutViewTool;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

/**
 * 作者：Created by 45703
 * 时间：Created on 2019/9/21.
 */
public abstract class BaseCheckFragment extends BaseFragment {
    @BindView(R.id.rv_check_title)
    RecyclerView mRvCheckTitle;
    @BindView(R.id.rv_check_item)
    RecyclerView mRvCheckItem;

    protected CheckItemAdapter mCheckItemAdapter;
    protected List<CheckItemModel> mCheckItemModelList=new ArrayList<>();

    protected CheckTitleAdapter mCheckTitleAdapter;
    protected List<CheckTitleModel> mCheckTitleModelList=new ArrayList<>();

    protected PopupWindow mPopupWindow;

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }

    @Override
    protected void initView() {
        EventBus.getDefault().register(this);
        GridLayoutManager manager=new GridLayoutManager(getContext(),1);
        mRvCheckTitle.setLayoutManager(manager);
        mCheckTitleAdapter=new CheckTitleAdapter(mCheckTitleModelList);
        mRvCheckTitle.setAdapter(mCheckTitleAdapter);
        GridLayoutManager manager1=new GridLayoutManager(getContext(),1);
        mRvCheckItem.setLayoutManager(manager1);
        mCheckItemAdapter=new CheckItemAdapter(mCheckItemModelList);
        mRvCheckItem.setAdapter(mCheckItemAdapter);
    }

    protected void showPopuWindow() {
        WindowManager.LayoutParams lp =getActivity().getWindow()
                .getAttributes();
        lp.width=WindowManager.LayoutParams.MATCH_PARENT;
        lp.height=WindowManager.LayoutParams.MATCH_PARENT;
        lp.alpha = 0.4f;
        Window window=getActivity().getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        window.setAttributes(lp);


        //加载布局
        ConstraintLayout layout = (ConstraintLayout) LayoutInflater.from(getActivity()).inflate(
                R.layout.popup_select_class, null);
        RelayoutViewTool.relayoutViewWithScale(layout, Constant.mScreenWidthScale);
        TextView tv_confirm=layout.findViewById(R.id.tv_confirm);
        RecyclerView rv_class=layout.findViewById(R.id.rv_class);
        List<ClassModel> classModelList=setClassModelList();
        ClassAdapter adapter=new ClassAdapter(classModelList);
        GridLayoutManager manager=new GridLayoutManager(getContext(),4);

        tv_confirm.setOnClickListener(new View.OnClickListener() {//确定
            @Override
            public void onClick(View v) {

            }
        });
        rv_class.setLayoutManager(manager);
        rv_class.setAdapter(adapter);
        // 实例化popupWindow
        mPopupWindow = new PopupWindow(layout, ConstraintLayout.LayoutParams.MATCH_PARENT,ConstraintLayout.LayoutParams.WRAP_CONTENT );
        //控制键盘是否可以获得焦点
        mPopupWindow.setFocusable(true);
        mPopupWindow.showAtLocation(getView(), Gravity.BOTTOM, 0,(int)(Constant.mScreenWidthScale * 252));
        mPopupWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            // 在dismiss中恢复透明度
            public void onDismiss() {
                WindowManager.LayoutParams lp = getActivity().getWindow()
                        .getAttributes();
                lp.alpha = 1f;
                getActivity().getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
                getActivity().getWindow().setAttributes(lp);
            }
        });
    }

    private  List<ClassModel> setClassModelList(){
        List<ClassModel> classModelList=new ArrayList<>();
        for (int i=0;i<8;i++){
            ClassModel classModel=new ClassModel();
            classModel.setName("18机电"+(i+1));
            classModel.setSelect(false);
            classModelList.add(classModel);
        }
        return classModelList;
    }
}
