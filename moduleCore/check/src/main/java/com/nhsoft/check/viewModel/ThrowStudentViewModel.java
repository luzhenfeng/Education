package com.nhsoft.check.viewModel;

import android.app.Application;
import android.databinding.ObservableArrayList;
import android.databinding.ObservableField;
import android.databinding.ObservableList;
import android.support.annotation.NonNull;
import android.support.design.widget.TabLayout;
import android.view.View;

import com.lzf.greendao.entity.StudentModel;
import com.lzf.greendao.service.DormCheckModelService;
import com.nhsoft.base.base.viewModel.ToolbarViewModel;
import com.nhsoft.check.BR;
import com.nhsoft.check.R;
import com.nhsoft.check.entity.ThrowStudentEntity;
import com.nhsoft.utils.utils.DateUtil;

import me.tatarka.bindingcollectionadapter2.ItemBinding;
import priv.lzf.mvvmhabit.base.BaseViewModel;
import priv.lzf.mvvmhabit.binding.command.BindingAction;
import priv.lzf.mvvmhabit.binding.command.BindingCommand;
import priv.lzf.mvvmhabit.binding.command.BindingConsumer;
import priv.lzf.mvvmhabit.bus.event.SingleLiveEvent;

/**
 * 作者：Created by 45703
 * 时间：Created on 2019/12/10.
 */
public class ThrowStudentViewModel extends BaseViewModel {
    public String[] mTabStrings={"全部","未到","晚归","晚出","请假"};
    public ToolbarViewModel mToolbarViewModel;

    public ObservableField<ThrowStudentEntity> entity=new ObservableField<>();
    public ObservableList<StudentModel> mModelObservableList=new ObservableArrayList<>();

    //封装一个界面发生改变的观察者
    public UIChangeObservable uc = new UIChangeObservable();

    public class UIChangeObservable {
        //        public ObservableBoolean showSelectClassPopupWindow=new ObservableBoolean(false);
        public SingleLiveEvent toorbarRight = new SingleLiveEvent<>();

        //设置TabLayout选中的条目
        public SingleLiveEvent<Integer> selectTab = new SingleLiveEvent<>();

    }

    public ThrowStudentViewModel(@NonNull Application application) {
        super(application);
        mToolbarViewModel=new ToolbarViewModel(application);
        entity.set(new ThrowStudentEntity());
        entity.get().itemBinding = ItemBinding.of(BR.viewModel, R.layout.item_throw_student);
        bindingCommand();
    }

    @Override
    public void bindingCommand() {
        super.bindingCommand();
        entity.get().onTabSelectedCommand=new BindingCommand(new BindingConsumer<TabLayout.Tab>() {
            @Override
            public void call(TabLayout.Tab tab) {
                int index=tab.getPosition();
                uc.selectTab.setValue(index==0?0:(index==1?4:(index==2?2:(index==3?9:8))));
            }
        });
    }

    public void setToolbarViewModel(){
        mToolbarViewModel.setTitleText("考勤异常名单");
        mToolbarViewModel.setRightIconVisible(View.VISIBLE);
        mToolbarViewModel.setRightIconOnClick(new BindingCommand(new BindingAction() {
            @Override
            public void call() {
                uc.toorbarRight.call();
            }
        }));
    }

    public void init(){
        mModelObservableList.addAll(DormCheckModelService.getInstance().getThrowStudentCheckModel(getApplication(),null, DateUtil.getCurrentDate()));
        initAdapter(0);
    }

    public void dateChange(String startDate,String endDate,int status){
        mModelObservableList.clear();
        mModelObservableList.addAll(DormCheckModelService.getInstance().getThrowStudentCheckModel(getApplication(),startDate, endDate));
        initAdapter(status);
    }

    public void initAdapter(int status){
        entity.get().observableList.clear();
        if (entity.get().observableList.size() == 0) {
            for (StudentModel studentModel:mModelObservableList){
                if (status==0){
                    ThrowStudentItemViewModel throwStudentItemViewModel = new ThrowStudentItemViewModel(this,studentModel);
                    entity.get().observableList.add(throwStudentItemViewModel);
                }else {
                    if (status==studentModel.getStatus()){
                        ThrowStudentItemViewModel throwStudentItemViewModel = new ThrowStudentItemViewModel(this,studentModel);
                        entity.get().observableList.add(throwStudentItemViewModel);
                    }
                }
            }
        }
    }
}
