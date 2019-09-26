package com.nhsoft.upload.viewModel;

import android.app.Application;
import android.databinding.ObservableArrayList;
import android.databinding.ObservableField;
import android.databinding.ObservableList;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.view.View;

import com.nhsoft.base.viewModel.ToolbarViewModel;
import com.nhsoft.upload.BR;
import com.nhsoft.upload.R;

import me.tatarka.bindingcollectionadapter2.BindingViewPagerAdapter;
import me.tatarka.bindingcollectionadapter2.ItemBinding;
import priv.lzf.mvvmhabit.binding.command.BindingCommand;
import priv.lzf.mvvmhabit.binding.command.BindingConsumer;
import priv.lzf.mvvmhabit.bus.event.SingleLiveEvent;
import priv.lzf.mvvmhabit.utils.ToastUtils;

/**
 * Created by lzf on 2019/9/25.
 * Describe:
 */

public class UploadViewModel extends ToolbarViewModel {


    public SingleLiveEvent<String> itemClickEvent = new SingleLiveEvent<>();

    private String[] tabLayoutItems={"未上传","已上传","全部"};

    //给ViewPager添加ObservableList
    public ObservableList<ViewPagerItemViewModel> items = new ObservableArrayList<>();
    //给ViewPager添加ItemBinding
    public ItemBinding<ViewPagerItemViewModel> itemBinding = ItemBinding.of(BR.viewModel, R.layout.item_viewpager);
    //选中的viewPager的item的position
//    public int positon;
    public ObservableField<ViewPagerItemViewModel> item = new ObservableField<>();
    //给ViewPager添加PageTitle
    public final BindingViewPagerAdapter.PageTitles<ViewPagerItemViewModel> pageTitles = new BindingViewPagerAdapter.PageTitles<ViewPagerItemViewModel>() {
        @Override
        public CharSequence getPageTitle(int position, ViewPagerItemViewModel item) {
            return tabLayoutItems[position];
        }
    };
    //ViewPager切换监听
    public BindingCommand<Integer> onPageSelectedCommand = new BindingCommand<>(new BindingConsumer<Integer>() {
        @Override
        public void call(Integer index) {
            item.set(items.get(index));
//            positon=index;
            ToastUtils.showShort("ViewPager切换：" + index);
        }
    });

    public UploadViewModel(@NonNull Application application) {
        super(application);
        //模拟3个ViewPager页面
        setItems(tabLayoutItems);
        item.set(items.get(0));
        getItem().requestNetWork();
    }


//    public int getViewPagerItemViewModel(ViewPagerItemViewModel viewPagerItemViewModel){
//        return items.indexOf(viewPagerItemViewModel);
//    }

    public ViewPagerItemViewModel getItem(){
        return item.get();
    }

    /**
     * 设置TabLayout
     * @param tabLayoutItems
     */
    private void setItems(String[] tabLayoutItems){
        for (String s:tabLayoutItems){
            ViewPagerItemViewModel itemViewModel = new ViewPagerItemViewModel(this);
            items.add(itemViewModel);
        }
    }

    /**
     * 初始化Toolbar
     */
    public void initToolbar() {
        //初始化标题栏
        setRightTextVisible(View.VISIBLE);
        setTitleText("上传文件");
        setRightText("编辑");
    }


}
