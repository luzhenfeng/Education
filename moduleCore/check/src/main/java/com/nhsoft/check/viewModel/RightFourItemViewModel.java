package com.nhsoft.check.viewModel;

import android.databinding.ObservableField;
import android.support.annotation.NonNull;
import android.view.View;

import com.afollestad.materialdialogs.MaterialDialog;
import com.nhsoft.check.entity.RightFourEntity;

import java.util.ArrayList;
import java.util.List;

import priv.lzf.mvvmhabit.base.AppManager;
import priv.lzf.mvvmhabit.base.MultiItemViewModel;
import priv.lzf.mvvmhabit.binding.command.BindingAction;
import priv.lzf.mvvmhabit.binding.command.BindingCommand;
import priv.lzf.mvvmhabit.utils.MaterialDialogUtils;

/**
 * Created by lzf on 2019/9/29.
 * Describe:
 */

public class RightFourItemViewModel extends MultiItemViewModel<CheckBaseViewModel> {

    public ObservableField<RightFourEntity> entity=new ObservableField<>();

    public RightFourItemViewModel(@NonNull CheckBaseViewModel viewModel,RightFourEntity entity) {
        super(viewModel);
        this.entity.set(entity);
        bindingCommand();
    }

    public void bindingCommand() {
        entity.get().onClick=new BindingCommand<>(new BindingAction() {
            @Override
            public void call() {
//                entity.get().text1Select.set(!entity.get().text1Select.get());
//                MaterialDialogUtils.showSingleListDialog()
                List<String> strings=new ArrayList<>();
                strings.add("0.5");
                strings.add("1");
                strings.add("1.5");
                strings.add("2");
                MaterialDialogUtils.showListDialog(AppManager.getAppManager().currentActivity(),strings)
                        .itemsCallback(new MaterialDialog.ListCallback() {
                            @Override
                            public void onSelection(MaterialDialog dialog, View itemView, int position, CharSequence text) {
                                entity.get().deduction.set(text.toString());
                                viewModel.onRight4ItemClick(viewModel.getRightItemPosition(RightFourItemViewModel.this));
                            }
                        }).show();
            }
        });
        entity.get().onClickImage=new BindingCommand<>(new BindingAction() {
            @Override
            public void call() {
                viewModel.onRight4ImageItemClick(viewModel.getRightItemPosition(RightFourItemViewModel.this));
//                entity.get().text1Select.set(!entity.get().text1Select.get());
            }
        });
    }
}
