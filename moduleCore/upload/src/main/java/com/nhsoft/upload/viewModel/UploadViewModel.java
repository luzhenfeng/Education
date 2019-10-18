package com.nhsoft.upload.viewModel;

import android.app.Application;
import android.databinding.ObservableArrayList;
import android.databinding.ObservableInt;
import android.databinding.ObservableList;
import android.support.annotation.NonNull;
import android.support.design.widget.TabLayout;
import android.view.View;
import android.widget.Button;

import com.alibaba.android.arouter.launcher.ARouter;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.lzf.greendao.entity.ChecksModel;
import com.lzf.greendao.service.ChecksModelService;
import com.lzf.http.data.Injection;
import com.lzf.http.data.Repository;
import com.lzf.http.entity.CheckModel;
import com.lzf.http.utils.HttpDataUtil;
import com.nhsoft.base.router.RouterActivityPath;
import com.nhsoft.upload.BR;
import com.nhsoft.upload.R;
import com.nhsoft.upload.entity.UploadEntity;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import me.tatarka.bindingcollectionadapter2.ItemBinding;
import okhttp3.RequestBody;
import priv.lzf.mvvmhabit.base.BaseViewModel;
import priv.lzf.mvvmhabit.binding.command.BindingAction;
import priv.lzf.mvvmhabit.binding.command.BindingCommand;
import priv.lzf.mvvmhabit.binding.command.BindingConsumer;
import priv.lzf.mvvmhabit.bus.Messenger;
import priv.lzf.mvvmhabit.bus.event.SingleLiveEvent;
import priv.lzf.mvvmhabit.http.BaseResponse;
import priv.lzf.mvvmhabit.http.ResponseThrowable;
import priv.lzf.mvvmhabit.utils.KLog;
import priv.lzf.mvvmhabit.utils.RxUtils;
import priv.lzf.mvvmhabit.utils.ToastUtils;

/**
 * Created by lzf on 2019/9/25.
 * Describe:
 */

public class UploadViewModel extends BaseViewModel<Repository> {

    //封装一个界面发生改变的观察者
    public UploadViewModel.UIChangeObservable uc = new UploadViewModel.UIChangeObservable();

    public String[] tabs={"未上传","已上传","全部"};

    public ObservableInt isShowButton=new ObservableInt(View.VISIBLE);

    //上传到第几条
    public ObservableInt upLodePos=new ObservableInt(0);

//    public ObservableList<String> tabList=new ObservableArrayList<>();

    public List<CheckModel> checkModelList=new ArrayList<>();
    //选中的条目id列表
    public List<Long> ids=new ArrayList<>();

    public class UIChangeObservable {
        //下拉刷新完成
        public SingleLiveEvent finishRefreshing = new SingleLiveEvent<>();
        //上拉加载完成
        public SingleLiveEvent finishLoadmore = new SingleLiveEvent<>();
        //TabLayout切换
        public SingleLiveEvent<Integer> onTabSelectedCommand = new SingleLiveEvent<>();
    }

    public UploadViewModel(@NonNull Application application) {
        super(application);
        model = Injection.provideDemoRepository();
    }


    //TabLayout切换监听
    public BindingCommand<TabLayout.Tab> onTabSelectedCommand = new BindingCommand<>(new BindingConsumer<TabLayout.Tab>() {
        @Override
        public void call(TabLayout.Tab tab) {
            if (tab.getPosition()==0){
                isShowButton.set(View.VISIBLE);
            }else {
                isShowButton.set(View.GONE);
            }
            uc.onTabSelectedCommand.setValue(Integer.valueOf(tab.getPosition()));
        }
    });


    //一件上传
    public BindingCommand<Button> onClickBtn=new BindingCommand<>(new BindingAction() {
        @Override
        public void call() {
           checkModelList=getCheckModel();
           if (checkModelList.size()>0){
               upload(upLodePos.get(),checkModelList.get(upLodePos.get()));
           }else {
               ToastUtils.showShort("请选择要上传的条目");
           }
        }
    });

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
    public BindingCommand onTwinklingRefreshCommand = new BindingCommand(new BindingAction() {
        @Override
        public void call() {
            ToastUtils.showShort("下拉刷新");

        }
    });
    //上拉加载
    public BindingCommand onTwinklingLoadMoreCommand = new BindingCommand(new BindingAction() {
        @Override
        public void call() {

        }
    });

    public void setData(List<ChecksModel> checksModels){
        observableList.clear();
        for (ChecksModel checksModel:checksModels){
            List<CheckModel.RecordsBean> recordsBeanList=new Gson().fromJson(checksModel.getRecords(),new TypeToken<List<CheckModel.RecordsBean>>(){}.getType());
            UploadEntity uploadEntity =new UploadEntity();
            uploadEntity.setId(checksModel.getId());
            uploadEntity.setText1((checksModel.getObjectName()==null?"":checksModel.getObjectName()+"-")+(checksModel.getClassName().equals("")?"":"("+checksModel.getClassName()+")"));
            uploadEntity.setText2(checksModel.getCateName()+"("+checksModel.getCheckDate()+"-"+"扣"+score(recordsBeanList)+"分"+")");
            uploadEntity.setText3(getCheckItem(recordsBeanList));
            uploadEntity.setText4("类型:"+(checksModel.getCategory()==0?"班级检查":checksModel.getCategory()==1?"寝室检查":"公共场地"));
            uploadEntity.setUpload(checksModel.getIsUpdate());
            uploadEntity.setChecksModel(checksModel);
            FileItemViewModel itemViewModel = new FileItemViewModel(this, uploadEntity);
            //双向绑定动态添加Item
            observableList.add(itemViewModel);
        }
    }

    /**
     * 选中条目的总分数的分数
     * @return
     */
    public double score(List<CheckModel.RecordsBean> recordsBeanList){
        double score=0d;
        for (CheckModel.RecordsBean recordsBean:recordsBeanList){
            score+=recordsBean.getScore();
        }
        return score;
    }


    /**
     * 选中条目
     * @return
     */
    public String getCheckItem(List<CheckModel.RecordsBean> recordsBeanList){
        String items="";
        for (CheckModel.RecordsBean recordsBean:recordsBeanList){
            if (recordsBeanList.indexOf(recordsBean)==recordsBeanList.size()-1){
                items+=recordsBean.getName();
            }else {
                items+=recordsBean.getName()+",";
            }
        }
        return items;
    }

    /**
     * 获取选中的条目并转换成 CheckModel
     */
    public List<CheckModel> getCheckModel(){
        List<CheckModel> checkModelList=new ArrayList<>();
        for (FileItemViewModel fileItemViewModel:observableList){
            if (fileItemViewModel.entity.get().isSelect()){
                checkModelList.add(HttpDataUtil.getCheckModel(fileItemViewModel.entity.get().getChecksModel()));
                ids.add(fileItemViewModel.entity.get().getId());
            }
        }
        return checkModelList;
    }

    public void upload(int pos,CheckModel checkModel){
        checkModel.setPhotos(HttpDataUtil.getPhotoBase64(checkModel.getPhotos()));
        RequestBody body = RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"), new Gson().toJson(checkModel));
        addSubscribe(model.createCheck(model.getToken(),body)
                .compose(RxUtils.bindToLifecycle(getLifecycleProvider())) //请求与View周期同步（过度期，尽量少使用）
                .compose(RxUtils.schedulersTransformer()) //线程调度
                .compose(RxUtils.exceptionTransformer()) // 网络错误的异常转换, 这里可以换成自己的ExceptionHandle);
                .doOnSubscribe(new Consumer<Disposable>() {
                    @Override
                    public void accept(Disposable disposable) throws Exception {
                        if (pos==0){
                            showDialog();
                        }
                    }
                })
                .subscribe(new Consumer<BaseResponse>() {
                    @Override
                    public void accept(BaseResponse response) throws Exception {
                        if (response.isOk()){//更新数据库
                            ChecksModel checksModel=ChecksModelService.getInstance().getChecksModel(ids.get(upLodePos.get()));
                            checksModel.setIsUpdate(true);
                            boolean isUpdate=ChecksModelService.getInstance().updateChecksModel(checksModel);
                            if (isUpdate){
                                uc.onTabSelectedCommand.setValue(0);
                            }
                            KLog.e(isUpdate);
                        }
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        if (upLodePos.get()<checkModelList.size()-1){
                            KLog.e(upLodePos.get());
                            upLodePos.set(upLodePos.get()+1);
                            upload(upLodePos.get(),checkModelList.get(upLodePos.get()));
                        }else {
                            //关闭对话框
                            dismissDialog();
                        }
//                        if (throwable instanceof ResponseThrowable) {
//                            ToastUtils.showShort(((ResponseThrowable) throwable).message);
//                        }
                    }
                }, new Action() {
                    @Override
                    public void run() throws Exception {
                        if (upLodePos.get()<checkModelList.size()-1){
                            KLog.e(upLodePos.get());
                            upLodePos.set(upLodePos.get()+1);
                            upload(upLodePos.get(),checkModelList.get(upLodePos.get()));
                        }else {
                            //关闭对话框
                            dismissDialog();
                        }
                    }
                }));
    }

    /**
     * 网络请求方法，在ViewModel中调用Model层，通过Okhttp+Retrofit+RxJava发起请求
     */
    public void requestNetWork() {
        //建议调用addSubscribe()添加Disposable，请求与View周期同步
        //addSubscribe();

        for (int i=0;i<50;i++) {
            UploadEntity uploadEntity =new UploadEntity();
            uploadEntity.setId(i+1);
            uploadEntity.setText1("101-(18三维2)");
            uploadEntity.setText2("内务检查(晚上-扣1分)");
            uploadEntity.setText3("地面有垃圾");
            uploadEntity.setText4("类型：寝室纪律 周次：27");
            FileItemViewModel itemViewModel = new FileItemViewModel(this, uploadEntity);
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
//                .subscribe(new Consumer<BaseSystemResponse<DemoEntity>>() {
//                    @Override
//                    public void accept(BaseSystemResponse<DemoEntity> response) throws Exception {
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
