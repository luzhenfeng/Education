package com.nhsoft.upload.viewModel;

import android.databinding.ObservableArrayList;
import android.databinding.ObservableList;
import android.support.annotation.NonNull;

import com.nhsoft.pxview.utils.RelayoutViewTool;
import com.nhsoft.upload.BR;
import com.nhsoft.upload.R;
import com.nhsoft.upload.adapter.ViewPagerBindingAdapter;
import com.nhsoft.upload.databinding.ItemViewpagerBinding;
import com.nhsoft.upload.entity.UploadModel;

import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;
import me.tatarka.bindingcollectionadapter2.ItemBinding;
import priv.lzf.mvvmhabit.base.BaseViewModel;
import priv.lzf.mvvmhabit.base.ItemViewModel;
import priv.lzf.mvvmhabit.binding.command.BindingAction;
import priv.lzf.mvvmhabit.binding.command.BindingCommand;
import priv.lzf.mvvmhabit.bus.event.SingleLiveEvent;
import priv.lzf.mvvmhabit.utils.RxUtils;
import priv.lzf.mvvmhabit.utils.ToastUtils;


/**
 * Created by lzf on 2019/9/26.
 * Describe:
 */

public class ViewPagerItemViewModel extends ItemViewModel<UploadViewModel> {
    public ItemViewpagerBinding binding;

    public SingleLiveEvent<FileItemViewModel> deleteItemLiveData = new SingleLiveEvent<>();

    //封装一个界面发生改变的观察者
    public UIChangeObservable uc = new UIChangeObservable();


    public class UIChangeObservable {
        //下拉刷新完成
        public SingleLiveEvent finishRefreshing = new SingleLiveEvent<>();
        //上拉加载完成
        public SingleLiveEvent finishLoadmore = new SingleLiveEvent<>();
    }

    public ViewPagerItemViewModel(@NonNull UploadViewModel viewModel) {
        super(viewModel);
    }

    //给RecyclerView添加ObservableList
    public ObservableList<FileItemViewModel> observableList = new ObservableArrayList<>();
    //给RecyclerView添加ItemBinding
    public ItemBinding<FileItemViewModel> itemBinding = ItemBinding.of(BR.viewModel, R.layout.item_file);

    public BindingCommand onItemClick = new BindingCommand(new BindingAction() {
        @Override
        public void call() {
            //点击之后将逻辑转到activity中处理
//            viewModel.itemClickEvent.setValue(text);
        }
    });


    //下拉刷新
    public BindingCommand onRefreshCommand = new BindingCommand(new BindingAction() {
        @Override
        public void call() {
            ToastUtils.showShort("下拉刷新");
//            requestNetWork();
        }
    });
    //上拉加载
    public BindingCommand onLoadMoreCommand = new BindingCommand(new BindingAction() {
        @Override
        public void call() {
//            if (observableList.size() > 50) {
//                ToastUtils.showLong("兄dei，你太无聊啦~崩是不可能的~");
//                uc.finishLoadmore.call();
//                return;
//            }
//            //模拟网络上拉加载更多
//            viewModel.addSubscribe(viewModel.model.loadMore()
//                    .compose(RxUtils.schedulersTransformer()) //线程调度
//                    .doOnSubscribe(new Consumer<Disposable>() {
//                        @Override
//                        public void accept(Disposable disposable) throws Exception {
//                            ToastUtils.showShort("上拉加载");
//                        }
//                    })
//                    .subscribe(new Consumer<DemoEntity>() {
//                        @Override
//                        public void accept(DemoEntity entity) throws Exception {
//                            for (DemoEntity.ItemsEntity itemsEntity : entity.getItems()) {
//                                NetWorkItemViewModel itemViewModel = new NetWorkItemViewModel(NetWorkViewModel.this, itemsEntity);
//                                //双向绑定动态添加Item
//                                observableList.add(itemViewModel);
//                            }
//                            //刷新完成收回
//                            uc.finishLoadmore.call();
//                        }
//                    }));
        }
    });

    /**
     * 网络请求方法，在ViewModel中调用Model层，通过Okhttp+Retrofit+RxJava发起请求
     */
    public void requestNetWork() {
        //建议调用addSubscribe()添加Disposable，请求与View周期同步
        //addSubscribe();

        for (int i=0;i<50;i++) {
            UploadModel uploadModel=new UploadModel();
            uploadModel.setId(i+1);
            uploadModel.setText1("101-(18三维2)");
            uploadModel.setText2("内务检查(晚上-扣1分)");
            uploadModel.setText3("地面有垃圾");
            uploadModel.setText4("类型：寝室纪律 周次：27");
            FileItemViewModel itemViewModel = new FileItemViewModel(viewModel, uploadModel);
            //双向绑定动态添加Item
            observableList.add(itemViewModel);
        }
        //请求刷新完成收回
        uc.finishRefreshing.call();
//        model.demoGet()
//                .compose(RxUtils.bindToLifecycle(getLifecycleProvider())) //请求与View周期同步（过度期，尽量少使用）
//                .compose(RxUtils.schedulersTransformer()) //线程调度
//                .compose(RxUtils.exceptionTransformer()) // 网络错误的异常转换, 这里可以换成自己的ExceptionHandle
//                .doOnSubscribe(new Consumer<Disposable>() {
//                    @Override
//                    public void accept(Disposable disposable) throws Exception {
//                        showDialog("正在请求...");
//                    }
//                })
//                .subscribe(new Consumer<BaseResponse<DemoEntity>>() {
//                    @Override
//                    public void accept(BaseResponse<DemoEntity> response) throws Exception {
//                        //清除列表
//                        observableList.clear();
//                        //请求成功
//                        if (response.getCode() == 1) {
//                            for (DemoEntity.ItemsEntity entity : response.getResult().getItems()) {
//                                NetWorkItemViewModel itemViewModel = new NetWorkItemViewModel(NetWorkViewModel.this, entity);
//                                //双向绑定动态添加Item
//                                observableList.add(itemViewModel);
//                            }
//                        } else {
//                            //code错误时也可以定义Observable回调到View层去处理
//                            ToastUtils.showShort("数据错误");
//                        }
//                    }
//                }, new Consumer<Throwable>() {
//                    @Override
//                    public void accept(Throwable throwable) throws Exception {
//                        //关闭对话框
//                        dismissDialog();
//                        //请求刷新完成收回
//                        uc.finishRefreshing.call();
//                        if (throwable instanceof ResponseThrowable) {
//                            ToastUtils.showShort(((ResponseThrowable) throwable).message);
//                        }
//                    }
//                }, new Action() {
//                    @Override
//                    public void run() throws Exception {
//                        //关闭对话框
//                        dismissDialog();
//                        //请求刷新完成收回
//                        uc.finishRefreshing.call();
//                    }
//                });
    }


    /**
     * 获取条目下标
     *
     * @return
     */
    public int getItemPosition(FileItemViewModel fileItemViewModel) {
        return observableList.indexOf(fileItemViewModel);
    }
}
