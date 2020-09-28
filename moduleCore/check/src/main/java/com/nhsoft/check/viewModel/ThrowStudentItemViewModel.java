package com.nhsoft.check.viewModel;

import android.databinding.ObservableArrayList;
import android.databinding.ObservableField;
import android.databinding.ObservableList;
import android.support.annotation.NonNull;

import com.lzf.greendao.entity.StudentModel;

import java.util.List;

import priv.lzf.mvvmhabit.base.ItemViewModel;

/**
 * 作者：Created by 45703
 * 时间：Created on 2019/12/10.
 */
public class ThrowStudentItemViewModel extends ItemViewModel<ThrowStudentViewModel> {
    public ObservableField<StudentModel> mStudentModel=new ObservableField<>();
//    public ObservableField<String> headPic=new ObservableField<>("");
    public ThrowStudentItemViewModel(@NonNull ThrowStudentViewModel viewModel,StudentModel studentModel) {
        super(viewModel);
        mStudentModel.set(studentModel);

    }
}
