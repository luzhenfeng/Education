package com.nhsoft.check.ui.fragment;


import android.arch.lifecycle.Observer;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.nhsoft.base.base.adapter.RecyclerViewBindingAdapter;
import com.nhsoft.check.BR;
import com.nhsoft.check.R;
import com.nhsoft.check.databinding.FragmentCheckListBinding;
import com.nhsoft.check.databinding.ItemRightOneBinding;
import com.nhsoft.check.utils.CustomPopWindowUtil;
import com.nhsoft.check.viewModel.CheckListViewModel;

import priv.lzf.mvvmhabit.base.BaseFragment;
import priv.lzf.mvvmhabit.utils.KLog;
import priv.lzf.mvvmhabit.utils.ToastUtils;

/**
 * A simple {@link Fragment} subclass.
 */
public class CheckListFragment extends BaseFragment<FragmentCheckListBinding, CheckListViewModel> {

    @Override
    public int initContentView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return R.layout.fragment_check_list;
    }

    @Override
    public int initVariableId() {
        return BR.viewModel;
    }

    @Override
    public void initViewObservable() {
        super.initViewObservable();
        viewModel.uc.type.observe(this, new Observer<Integer>() {
            @Override
            public void onChanged(@Nullable Integer integer) {
                if (integer.intValue()==3){
                    viewModel.setTitle("选择学生");
                    viewModel.isShowButton(View.VISIBLE);
                    CustomPopWindowUtil.getInstance().setData(viewModel.mSelectSudentList, viewModel.mStudentList);
                }else if (integer.intValue()==4){
                    viewModel.setTitle("选择检查项班级");
                    viewModel.isShowButton(View.VISIBLE);
                    CustomPopWindowUtil.getInstance().setData(viewModel.getCurrenItem(),viewModel.mRoomModel);
                }
                CustomPopWindowUtil.getInstance().showAtLocationBottomPopupWindow(getContext(), getView());
                CustomPopWindowUtil.getInstance().setAdapter();
            }
        });
        viewModel.uc.setTabs.observe(this, new Observer() {
            @Override
            public void onChanged(@Nullable Object o) {
                if (binding.tabs.getTabCount()==0){
                    setTabs();
                }
            }
        });
        viewModel.uc.selectTab.observe(this, new Observer<Integer>() {
            @Override
            public void onChanged(@Nullable Integer integer) {
                binding.tabs.getTabAt(integer.intValue()).select();
            }
        });

        viewModel.uc.scrollPosition.observe(this, new Observer<Integer>() {
            @Override
            public void onChanged(@Nullable Integer integer) {
                ((LinearLayoutManager)binding.rvRight.getLayoutManager()).scrollToPositionWithOffset(integer.intValue(),0);
            }
        });
    }

    @Override
    public void initData() {
        super.initData();
        binding.setAdapter(new RecyclerViewBindingAdapter());
        binding.setRightAdapter(new RecyclerViewBindingAdapter());
        viewModel.setPopupBinding(getContext());

    }

    private void setTabs() {
        if (viewModel.entity.get().tabs.get().size() > 5) {
            binding.tabs.setTabMode(TabLayout.MODE_SCROLLABLE);
        }
        for (String s : viewModel.entity.get().tabs.get()) {
            binding.tabs.addTab(binding.tabs.newTab().setCustomView(getTabItemView(s)));
        }
    }

    public View getTabItemView(String name){
        View newtab = LayoutInflater.from(getContext()).inflate(R.layout.tab_item_text, null);
        TextView tv = newtab.findViewById(R.id.tv_tab_text);
        tv.setText(name);
        return newtab;
    }
}
