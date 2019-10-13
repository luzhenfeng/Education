package com.nhsoft.upload.viewModel;

import android.databinding.ObservableBoolean;
import android.databinding.ObservableField;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.widget.ImageView;

import com.nhsoft.upload.R;
import com.nhsoft.upload.entity.UploadEntity;

import priv.lzf.mvvmhabit.base.ItemViewModel;
import priv.lzf.mvvmhabit.binding.command.BindingAction;
import priv.lzf.mvvmhabit.binding.command.BindingCommand;

/**
 * Created by lzf on 2019/9/26.
 * Describe:
 */

public class FileItemViewModel extends ItemViewModel<UploadViewModel> {
    public ObservableField<UploadEntity> entity = new ObservableField<>();
    public Drawable drawableImg;
    public FileItemViewModel(@NonNull UploadViewModel viewModel, UploadEntity model) {
        super(viewModel);
        this.entity.set(model);
        drawableImg = ContextCompat.getDrawable(viewModel.getApplication(), R.drawable.off_line);
    }

    /**
     * 获取position的方式有很多种,indexOf是其中一种，常见的还有在Adapter中、ItemBinding.of回调里
     *
     * @return
     */
    public int getPosition() {
        return viewModel.getItemPosition(this);
    }

    //条目的点击事件
    public BindingCommand itemClick = new BindingCommand(new BindingAction() {
        @Override
        public void call() {

        }
    });
    //条目的长按事件
    public BindingCommand itemLongClick = new BindingCommand(new BindingAction() {
        @Override
        public void call() {

        }
    });


    public BindingCommand<ImageView> onClickCheckBox=new BindingCommand<>(new BindingAction() {
        @Override
        public void call() {
            entity.get().setSelect(!entity.get().isSelect());
            viewModel.observableList.set(getPosition(),FileItemViewModel.this);

        }
    });

}
