package com.nhsoft.upload.viewModel;

import android.database.Observable;
import android.databinding.ObservableField;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.widget.ImageView;

import com.nhsoft.upload.R;
import com.nhsoft.upload.entity.UploadModel;

import priv.lzf.mvvmhabit.base.ItemViewModel;
import priv.lzf.mvvmhabit.binding.command.BindingAction;
import priv.lzf.mvvmhabit.binding.command.BindingCommand;
import priv.lzf.mvvmhabit.utils.ToastUtils;

/**
 * Created by lzf on 2019/9/26.
 * Describe:
 */

public class FileItemViewModel extends ItemViewModel<UploadViewModel> {
    public ObservableField<UploadModel> entity = new ObservableField<>();
    public Drawable drawableImg;
    public FileItemViewModel(@NonNull UploadViewModel viewModel,UploadModel model) {
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
            //这里可以通过一个标识,做出判断，已达到跳入不同界面的逻辑
//            if (entity.get().getId() == -1) {
//                viewModel.deleteItemLiveData.setValue(NetWorkItemViewModel.this);
//            } else {
//                //跳转到详情界面,传入条目的实体对象
//                Bundle mBundle = new Bundle();
//                mBundle.putParcelable("entity", entity.get());
//                viewModel.startContainerActivity(DetailFragment.class.getCanonicalName(), mBundle);
//            }
        }
    });
    //条目的长按事件
    public BindingCommand itemLongClick = new BindingCommand(new BindingAction() {
        @Override
        public void call() {
            //以前是使用Messenger发送事件，在NetWorkViewModel中完成删除逻辑
//            Messenger.getDefault().send(NetWorkItemViewModel.this, NetWorkViewModel.TOKEN_NETWORKVIEWMODEL_DELTE_ITEM);
            //现在ItemViewModel中存在ViewModel引用，可以直接拿到LiveData去做删除
//            ToastUtils.showShort(entity.get().getName());
        }
    });


    public BindingCommand<ImageView> onClickCheckBox=new BindingCommand<>(new BindingAction() {
        @Override
        public void call() {
            entity.get().setSelect(!entity.get().isSelect());
            viewModel.observableList.set(getPosition(),FileItemViewModel.this);
//            ToastUtils.showShort("点击上传");
        }
    });

}
