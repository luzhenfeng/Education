package com.nhsoft.check.ui.activity;

import android.arch.lifecycle.Observer;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.DatePicker;
import android.widget.TextView;


import com.nhsoft.base.base.adapter.RecyclerViewBindingAdapter;
import com.nhsoft.check.BR;
import com.nhsoft.check.R;
import com.nhsoft.check.databinding.ActivityThrowStudentBinding;
import com.nhsoft.check.ui.dialog.DoubleDatePickerDialog;
import com.nhsoft.check.viewModel.ThrowStudentViewModel;
import com.nhsoft.utils.utils.DateUtil;
import com.nhsoft.utils.utils.LanguageUtils;

import java.util.Calendar;

import priv.lzf.mvvmhabit.base.BaseActivity;
import priv.lzf.mvvmhabit.utils.KLog;

/**
 * 作者：Created by 45703
 * 时间：Created on 2019/12/10.
 */
public class ThrowStudentActivity extends BaseActivity<ActivityThrowStudentBinding, ThrowStudentViewModel> {
    @Override
    public int initContentView(Bundle savedInstanceState) {
        return R.layout.activity_throw_student;
    }

    @Override
    public int initVariableId() {
        return com.nhsoft.check.BR.viewModel;
    }

    @Override
    public void initData() {
        super.initData();
        LanguageUtils.setLanguageChina(this);
        viewModel.setToolbarViewModel();
        setTabs();
        binding.setAdapter(new RecyclerViewBindingAdapter());
        viewModel.init();
    }

    @Override
    public void initViewObservable() {
        super.initViewObservable();
        viewModel.uc.toorbarRight.observe(this, new Observer() {
            @Override
            public void onChanged(@Nullable Object o) {
                Calendar c = Calendar.getInstance();
                // 最后一个false表示不显示日期，如果要显示日期，最后参数可以是true或者不用输入
                final DoubleDatePickerDialog doubleDatePickerDialog=new DoubleDatePickerDialog(ThrowStudentActivity.this, 0, new DoubleDatePickerDialog.OnDateSetListener() {

                    @Override
                    public void onDateSet(DatePicker startDatePicker, int startYear, int startMonthOfYear,
                                          int startDayOfMonth, DatePicker endDatePicker, int endYear, int endMonthOfYear,
                                          int endDayOfMonth) {
                        String textString = String.format("开始时间：%d-%d-%d\n结束时间：%d-%d-%d\n", startYear,
                                startMonthOfYear + 1, startDayOfMonth, endYear, endMonthOfYear + 1, endDayOfMonth);
                        String startDate=startYear+"-"+(startMonthOfYear+1)+"-"+startDayOfMonth;
                        String endDate=endYear+"-"+(endMonthOfYear+1)+"-"+endMonthOfYear;
                        int index=binding.tabs.getSelectedTabPosition();
                        viewModel.dateChange(startDate,endDate,(index==0?0:(index==1?4:(index==2?2:(index==3?9:8)))));

                    }
                }, c.get(Calendar.YEAR), c.get(Calendar.MONTH), c.get(Calendar.DATE));
                doubleDatePickerDialog.show();
            }
        });
        viewModel.uc.selectTab.observe(this, new Observer<Integer>() {
            @Override
            public void onChanged(@Nullable Integer integer) {
//                binding.tabs.getTabAt(integer.intValue()).select();
                viewModel.initAdapter(integer.intValue());
            }
        });
    }


    private void setTabs() {
        for (String s : viewModel.mTabStrings) {
            binding.tabs.addTab(binding.tabs.newTab().setCustomView(getTabItemView(s)));
        }
    }

    public View getTabItemView(String name){
        View newtab = LayoutInflater.from(this).inflate(R.layout.tab_item_text, null);
        TextView tv = newtab.findViewById(R.id.tv_tab_text);
        tv.setText(name);
        return newtab;
    }
}
