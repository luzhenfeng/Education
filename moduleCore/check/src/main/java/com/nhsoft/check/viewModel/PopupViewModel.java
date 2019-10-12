package com.nhsoft.check.viewModel;

import android.databinding.ObservableArrayList;
import android.databinding.ObservableField;
import android.databinding.ObservableInt;
import android.databinding.ObservableList;
import android.support.v4.content.ContextCompat;

import com.nhsoft.check.BR;
import com.nhsoft.check.R;
import com.nhsoft.check.message.ConstantMessage;

import me.tatarka.bindingcollectionadapter2.ItemBinding;
import priv.lzf.mvvmhabit.base.BaseApplication;
import priv.lzf.mvvmhabit.binding.command.BindingCommand;
import priv.lzf.mvvmhabit.bus.Messenger;

/**
 * 作者：Created by 45703
 * 时间：Created on 2019/10/11.
 */
public class PopupViewModel {
    public ObservableField<String> title=new ObservableField<>("选择班级");
    public BindingCommand onClick;
    //给左边RecyclerView添加ObservableList
    public ObservableList<PopupItemViewModel> observableList = new ObservableArrayList<>();
    //给左边RecyclerView添加ItemBinding
    public ItemBinding<PopupItemViewModel> itemBinding =ItemBinding.of(BR.viewModel, R.layout.item_select_class);

    //选中的条目
    public ObservableInt selectPos=new ObservableInt(-1);

    /**
     * 左边条目选中
     * @param pos
     */
    public void setSelectPos(int pos){
        if (selectPos.get()!=pos){
            if (selectPos.get()!=-1){
                PopupItemViewModel oldItemViewModel=observableList.get(selectPos.get());
                oldItemViewModel.entity.get().selectState= ContextCompat.getDrawable(BaseApplication.getInstance(),R.drawable.check_box_aaaaaa);
                observableList.set(selectPos.get(),oldItemViewModel);
            }
            PopupItemViewModel newItemViewModel=observableList.get(pos);
            newItemViewModel.entity.get().selectState=ContextCompat.getDrawable(BaseApplication.getInstance(),R.drawable.check_box_select);
            observableList.set(pos,newItemViewModel);
            selectPos.set(pos);
            Messenger.getDefault().send(pos, ConstantMessage.TOKEN_POPUPVIEWMODEL_SELECTITEM);
        }
//        else {
//            selectPos.set(-1);
//            PopupItemViewModel oldItemViewModel=observableList.get(selectPos.get());
//            oldItemViewModel.entity.get().selectState= ContextCompat.getDrawable(BaseApplication.getInstance(),R.drawable.check_box_aaaaaa);
//            observableList.set(selectPos.get(),oldItemViewModel);
//        }
    }

    /**
     * 获取条目下标
     *
     * @param popupItemViewModel
     * @return
     */
    public int getItemPosition(PopupItemViewModel popupItemViewModel) {
        return observableList.indexOf(popupItemViewModel);
    }

}
