package com.lzf.login.viewModel;

import android.app.Application;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.databinding.ObservableBoolean;
import android.databinding.ObservableField;
import android.databinding.ObservableInt;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiManager;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.widget.ImageView;

import com.alibaba.android.arouter.launcher.ARouter;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.gson.Gson;
import com.lzf.greendao.entity.ChecksModel;
import com.lzf.greendao.service.ChecksModelService;
import com.lzf.greendao.service.UserService;
import com.lzf.http.data.Injection;
import com.lzf.http.data.Repository;
import com.lzf.http.entity.AllCategoryModel;
import com.lzf.http.entity.CheckModel;
import com.lzf.http.utils.HttpDataUtil;
import com.lzf.login.BR;
import com.lzf.login.R;
import com.lzf.login.entity.SelectMCodeEntity;
import com.lzf.login.entity.SelectMCodeItemEntity;
import com.nhsoft.base.base.ConstantMessage;
import com.nhsoft.base.router.RouterActivityPath;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import me.tatarka.bindingcollectionadapter2.ItemBinding;
import okhttp3.RequestBody;
import priv.lzf.mvvmhabit.base.BaseViewModel;
import priv.lzf.mvvmhabit.binding.command.BindingAction;
import priv.lzf.mvvmhabit.binding.command.BindingConsumer;
import priv.lzf.mvvmhabit.bus.Messenger;
import priv.lzf.mvvmhabit.http.BaseResponse;
import priv.lzf.mvvmhabit.utils.KLog;
import priv.lzf.mvvmhabit.utils.RxUtils;
import priv.lzf.mvvmhabit.utils.ToastUtils;

/**
 * 作者：Created by 45703
 * 时间：Created on 2019/10/20.
 */
public class SelectMCodeViewModel extends BaseViewModel<Repository> {

    public ObservableField<SelectMCodeEntity> entity = new ObservableField<>();

    public ObservableField<String> avatar=new ObservableField<>(UserService.getInstance().getAvatar().equals("")?"http://work.nbnz.net//Content/images/head/on-boy.jpg":UserService.getInstance().getAvatar());

    public ObservableField<String> name=new ObservableField<>(UserService.getInstance().getRealname());

    //上传到第几条
    public ObservableInt upLodePos=new ObservableInt(0);

    //条目id列表
    public List<Long> ids=new ArrayList<>();

    public List<CheckModel> checkModelList=new ArrayList<>();
    //是否正在上传
    public ObservableBoolean isUploading=new ObservableBoolean(false);

    public SelectMCodeViewModel(@NonNull Application application) {
        super(application);
        model = Injection.provideDemoRepository();
        entity.set(new SelectMCodeEntity());
        itemBinding();
        initMessenger();
    }


    public void itemBinding() {
        entity.get().itemBinding = ItemBinding.of(BR.viewModel, R.layout.item_select_mcode);
    }


    public void initMessenger() {
        Messenger.getDefault().register(this, ConstantMessage.TOKEN_UPLOADVIEWMODEL_GET_ISUPLOAD, new BindingAction() {
            @Override
            public void call() {
                Messenger.getDefault().send(isUploading.get(),ConstantMessage.TOKEN_SELECTMCODEVIEWMODEL_ISUPLOAD);
            }
        });
    }


    public void initData() {
        for (String s : HttpDataUtil.getAllMCode(model.getCodes())) {
            String[] mcode = s.split(":");
            SelectMCodeItemEntity selectMCodeItemEntity = new SelectMCodeItemEntity();
            selectMCodeItemEntity.image = getDrawable(mcode[0]);
            selectMCodeItemEntity.mCodeName.set(mcode[1]);
            selectMCodeItemEntity.mCode.set(mcode[0]);
            SelectMCodeItemViewModel viewModel = new SelectMCodeItemViewModel(this, selectMCodeItemEntity);
            entity.get().observableList.add(viewModel);
        }
    }


    public void setHead(ImageView imageView){
        RequestOptions requestOptions =
                RequestOptions.circleCropTransform()
                ;
        Glide.with(getApplication().getApplicationContext())
                .load(avatar.get())
                .apply(requestOptions)
                .into(imageView);
    }

    public Drawable getDrawable(String mcode) {
        Drawable drawable = null;
        int type = Integer.valueOf(mcode.split("-")[1]);
        switch (type) {
            case 1:
                drawable = ContextCompat.getDrawable(getApplication(), R.drawable.mcode1);
                break;
            case 2:
                drawable = ContextCompat.getDrawable(getApplication(), R.drawable.mcode2);
                break;
            case 3:
                drawable = ContextCompat.getDrawable(getApplication(), R.drawable.mcode3);
                break;
            case 4:
                drawable = ContextCompat.getDrawable(getApplication(), R.drawable.mcode4);
                break;
            case 5:
                drawable = ContextCompat.getDrawable(getApplication(), R.drawable.mcode5);
                break;
            case 6:
                drawable = ContextCompat.getDrawable(getApplication(), R.drawable.mcode6);
                break;
            case 7:
                drawable = ContextCompat.getDrawable(getApplication(), R.drawable.mcode7);
                break;
            case 8:
                drawable = ContextCompat.getDrawable(getApplication(), R.drawable.mcode8);
                break;
            case 99:
                drawable = ContextCompat.getDrawable(getApplication(), R.drawable.mcode9);
                break;
        }
        return drawable;
    }

    public void login(String code) {
        List<AllCategoryModel> mAllCategoryModelList = HttpDataUtil.getAllCategoryList(getApplication());
        if (mAllCategoryModelList==null||mAllCategoryModelList.size()==0){
            ToastUtils.showLong("数据下载失败请到登入界面重新下载");
            return;
        }
        model.saveCode(code);
        ARouter.getInstance().build(RouterActivityPath.Check.PAGER_CHECK).navigation();
    }

    /**
     * 注册网络监听的广播
     */
    public void initReceiver(Context context) {
        IntentFilter timeFilter = new IntentFilter();
        timeFilter.addAction("android.net.ethernet.ETHERNET_STATE_CHANGED");
        timeFilter.addAction("android.net.ethernet.STATE_CHANGE");
        timeFilter.addAction("android.net.conn.CONNECTIVITY_CHANGE");
        timeFilter.addAction("android.net.wifi.WIFI_STATE_CHANGED");
        timeFilter.addAction("android.net.wifi.STATE_CHANGE");
        timeFilter.addAction(WifiManager.NETWORK_STATE_CHANGED_ACTION);
        context.registerReceiver(netReceiver, timeFilter);
    }

    public void relese(Context context){
        if (netReceiver != null) {
            context.unregisterReceiver(netReceiver);
            netReceiver = null;
        }
    }


    BroadcastReceiver netReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (action.equals(ConnectivityManager.CONNECTIVITY_ACTION)) {
                ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(
                        Context.CONNECTIVITY_SERVICE);
                NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
                if (networkInfo != null && networkInfo.isAvailable()) {
                    int type2 = networkInfo.getType();
//                    String typeName = networkInfo.getTypeName();
                    switch (type2) {
                        case 0://移动 网络    2G 3G 4G 都是一样的 实测 mix2s 联通卡
//                            ToastUtils.showShort("移动");
                        case 1: //wifi网络
//                            ToastUtils.showShort("wifi网络");
                            checkModelList=getCheckModel();
                            if (checkModelList.size()>0){
                                isUploading.set(true);
                                Messenger.getDefault().send(isUploading.get(),ConstantMessage.TOKEN_SELECTMCODEVIEWMODEL_ISUPLOAD);
                                upload(checkModelList.get(upLodePos.get()));
                            }
                            break;
                        case 9:  //网线连接
//                            ToastUtils.showShort("网线连接");
                            break;
                    }
                } else {// 无网络
                    clearSubscribe();
//                    ToastUtils.showShort("无网络");
                }
            }
        }
    };

    /**
     * 获取选中的条目并转换成 CheckModel
     */
    public List<CheckModel> getCheckModel(){
        ids.clear();
        upLodePos.set(0);
        List<CheckModel> checkModelList=new ArrayList<>();
        List<ChecksModel> checksModelList=ChecksModelService.getInstance().getNoUpdateChecksModelList();
        for (ChecksModel checksModel:checksModelList){
            checkModelList.add(HttpDataUtil.getCheckModel(checksModel));
            ids.add(checksModel.getId());
        }
        return checkModelList;
    }


    public void upload(CheckModel checkModel){
        checkModel.setPhotos(HttpDataUtil.getPhotoBase64(checkModel.getPhotos()));
        RequestBody body = RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"), new Gson().toJson(checkModel));
        addSubscribe(model.createCheck(model.getToken(),body)
                .compose(RxUtils.bindToLifecycle(getLifecycleProvider())) //请求与View周期同步（过度期，尽量少使用）
                .compose(RxUtils.schedulersTransformer()) //线程调度
                .compose(RxUtils.exceptionTransformer()) // 网络错误的异常转换, 这里可以换成自己的ExceptionHandle);
                .subscribe(new Consumer<BaseResponse>() {
                    @Override
                    public void accept(BaseResponse response) throws Exception {
                        if (response.isOk()){//更新数据库
                            ChecksModel checksModel= ChecksModelService.getInstance().getChecksModel(ids.get(upLodePos.get()));
                            checksModel.setIsUpdate(true);
                            boolean isUpdate=ChecksModelService.getInstance().updateChecksModel(checksModel);
                        }
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        if (upLodePos.get()<checkModelList.size()-1){
                            KLog.e(upLodePos.get());
                            upLodePos.set(upLodePos.get()+1);
                            upload(checkModelList.get(upLodePos.get()));
                        }else {
                            isUploading.set(false);
                            Messenger.getDefault().send(isUploading.get(),ConstantMessage.TOKEN_SELECTMCODEVIEWMODEL_ISUPLOAD);
                        }
                    }
                }, new Action() {
                    @Override
                    public void run() throws Exception {
                        if (upLodePos.get()<checkModelList.size()-1){
                            KLog.e(upLodePos.get());
                            upLodePos.set(upLodePos.get()+1);
                            upload(checkModelList.get(upLodePos.get()));
                        }else {
                            isUploading.set(false);
                            Messenger.getDefault().send(isUploading.get(),ConstantMessage.TOKEN_SELECTMCODEVIEWMODEL_ISUPLOAD);
                        }
                    }
                }));
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Glide.with(getApplication().getApplicationContext()).pauseRequests();
    }
}
