package com.lzf.login.viewModel;

import android.app.Application;
import android.content.Intent;
import android.databinding.ObservableField;
import android.databinding.ObservableInt;
import android.databinding.ObservableLong;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.widget.Button;

import com.alibaba.android.arouter.launcher.ARouter;
import com.google.gson.Gson;
import com.lzf.greendao.service.ChecksModelService;
import com.lzf.http.data.Injection;
import com.lzf.http.data.Repository;
import com.lzf.http.data.RetrofitClient;
import com.lzf.http.entity.AllCategoryModel;
import com.lzf.http.entity.AppListModel;
import com.lzf.http.entity.FloorModel;
import com.lzf.http.entity.HeadModel;
import com.lzf.http.entity.LoginModel;
import com.lzf.http.entity.SycnListModel;
import com.lzf.login.entity.LoginEntity;
import com.lzf.login.service.DownPicService;
import com.lzf.login.ui.activity.RegisterActivity;
import com.lzf.login.ui.activity.SelectMCodeActivity;
import com.nhsoft.base.base.ConstantMessage;
import com.nhsoft.base.router.RouterActivityPath;
import com.nhsoft.pxview.constant.Constant;
import com.nhsoft.utils.utils.FileUtil;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import okhttp3.ResponseBody;
import priv.lzf.mvvmhabit.base.AppManager;
import priv.lzf.mvvmhabit.base.BaseViewModel;
import priv.lzf.mvvmhabit.binding.command.BindingAction;
import priv.lzf.mvvmhabit.binding.command.BindingCommand;
import priv.lzf.mvvmhabit.bus.Messenger;
import priv.lzf.mvvmhabit.http.BaseResponse;
import priv.lzf.mvvmhabit.http.DownLoadManager;
import priv.lzf.mvvmhabit.http.NetworkUtil;
import priv.lzf.mvvmhabit.http.ResponseThrowable;
import priv.lzf.mvvmhabit.http.download.ProgressCallBack;
import priv.lzf.mvvmhabit.utils.KLog;
import priv.lzf.mvvmhabit.utils.RxUtils;
import priv.lzf.mvvmhabit.utils.SPUtils;
import priv.lzf.mvvmhabit.utils.ToastUtils;

/**
 * 作者：Created by 45703
 * 时间：Created on 2019/10/3.
 */
public class LoginViewModel extends BaseViewModel<Repository> {

    public ObservableField<LoginEntity> entity=new ObservableField<>();

    //是否请求检查对象
    public boolean isCheckObject=false;

    //是否请求检查类别
    public boolean isCheckCategory=false;

    //是否请求头像
    public boolean isAvatars=false;

    public boolean isDown=false;


    public List<AppListModel> appListModelList=new ArrayList<>();

    public List<HeadModel> mHeadModelList=new ArrayList<>();//头像网络路径

    //点击的时间
    public ObservableLong time=new ObservableLong(0);

    //点击的次数
    public ObservableInt num=new ObservableInt(0);

    //下载失败的图片
    public List<String> errorPicture=new ArrayList<>();

    public LoginViewModel(@NonNull Application application) {
        super(application);
        model=Injection.provideDemoRepository(Constant.baseUrl);
//        RetrofitClient.baseUrl= model.getBaseUrl();
        this.entity.set(new LoginEntity());
        entity.get().username.set(model.getUserName());
        entity.get().password.set(model.getPassword());
        bindingCommand();
        initMessenger();
    }

    @Override
    public void bindingCommand() {
        super.bindingCommand();
        entity.get().login=new BindingCommand(new BindingAction() {
            @Override
            public void call() {
                login();
            }
        });

        entity.get().down=new BindingCommand<Button>(new BindingAction() {
            @Override
            public void call() {
                isDown=true;
                login();
            }
        });

        entity.get().register=new BindingCommand(new BindingAction() {
            @Override
            public void call() {
               register();
            }
        });
    }

    public void initMessenger() {
        Messenger.getDefault().register(this, ConstantMessage.TOKEN_REGISTERVIEWMODEL_NETWORK, new BindingAction() {
            @Override
            public void call() {
                model=Injection.provideDemoRepository();
            }
        });
    }

    /**
     * 到注册界面
     */
    public void register(){
        if (num.get()==0){
            time.set(System.currentTimeMillis());
            num.set(num.get()+1);
        }else if (num.get()>=4){
            if (System.currentTimeMillis()-time.get()>1000){
                num.set(0);
            }else {
                startActivity(RegisterActivity.class);
                num.set(0);
                finish();
            }
        }else {
            if (System.currentTimeMillis()-time.get()>1000){
                num.set(0);
            }else {
                time.set(System.currentTimeMillis());
                num.set(num.get()+1);
            }
        }
    }



    /**
     * 网络模拟一个登陆操作
     **/
    private void login() {
        if (TextUtils.isEmpty(entity.get().username.get())) {
            ToastUtils.showShort("请输入账号！");
            return;
        }
        if (TextUtils.isEmpty(entity.get().password.get())) {
            ToastUtils.showShort("请输入密码！");
            return;
        }
        if (!NetworkUtil.isNetworkAvailable(getApplication())){
            if (entity.get().username.get().equals(model.getUserName())&&entity.get().password.get().equals(model.getPassword())){
                startCheck();
            }else {
                ToastUtils.showShort("账号或者密码错误！");
            }
            return;
        }
        if (Constant.baseUrl==null){
            ToastUtils.showShort("机器未在后台注册！");
            return;
        }
        if (model.login(entity.get().username.get(),entity.get().password.get())==null){
            ToastUtils.showShort("服务器网址错误！");
            return;
        }
        addSubscribe(model.login(entity.get().username.get(),entity.get().password.get())
                .compose(RxUtils.bindToLifecycle(getLifecycleProvider())) //请求与View周期同步（过度期，尽量少使用）
                .compose(RxUtils.schedulersTransformer()) //线程调度
                .compose(RxUtils.exceptionTransformer()) // 网络错误的异常转换, 这里可以换成自己的ExceptionHandle
                .doOnSubscribe(new Consumer<Disposable>() {
                    @Override
                    public void accept(Disposable disposable) throws Exception {
                        showDialog();
                    }
                })
                .subscribe(new Consumer<BaseResponse<LoginModel>>() {
                    @Override
                    public void accept(BaseResponse<LoginModel> response) throws Exception {
                        KLog.e(new Gson().toJson(response.getData()));
                        if (response.isOk()) {
                            if (model.insertUser(response.getData())){
                                model.saveToken(response.getData().getToken().getAccess_token());
                                model.saveUserName(entity.get().username.get());
                                model.savePassword(entity.get().password.get());
                                getAppList();
                            }else {
                                ToastUtils.showShort("保存失败");
                                dismissDialog();
                            }
                        }else {
                            ToastUtils.showShort(response.getMsg());
                            dismissDialog();
                        }

                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        //关闭对话框
                        dismissDialog();
                        if (throwable instanceof ResponseThrowable) {
                            ToastUtils.showShort(((ResponseThrowable) throwable).message);
                        }
                    }
                }, new Action() {
                    @Override
                    public void run() throws Exception {
                        //关闭对话框
//                        dismissDialog();
                    }
                }));

    }


    private void getAppList(){
        addSubscribe(model.getAppList(model.getToken())
                .compose(RxUtils.bindToLifecycle(getLifecycleProvider())) //请求与View周期同步（过度期，尽量少使用）
                .compose(RxUtils.schedulersTransformer()) //线程调度
                .compose(RxUtils.exceptionTransformer()) // 网络错误的异常转换, 这里可以换成自己的ExceptionHandle
                .subscribe(new Consumer<BaseResponse<List<AppListModel>>>() {
                    @Override
                    public void accept(BaseResponse<List<AppListModel>> response) throws Exception {
                        if (response.isOk()){
                            appListModelList=response.getData();
                            if (appListModelList!=null){
                                String codes="";
                                for (int i=0;i<appListModelList.get(0).getApps().size();i++){
                                    if (i==appListModelList.get(0).getApps().size()-1){
                                        codes=codes+appListModelList.get(0).getApps().get(i).getCode()+":"+appListModelList.get(0).getApps().get(i).getName();
                                    }else {
                                        codes=codes+appListModelList.get(0).getApps().get(i).getCode()+":"+appListModelList.get(0).getApps().get(i).getName()+",";
                                    }
                                }
                                model.saveCodes(codes);
                            }
                            getSyncList();
                        }else {
                            dismissDialog();
                            ToastUtils.showShort(response.getMsg());
                        }
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        //关闭对话框
                        dismissDialog();
                        if (throwable instanceof ResponseThrowable) {
                            ToastUtils.showShort(((ResponseThrowable) throwable).message);
                        }
                    }
                }));
    }


    private void getSyncList(){
        addSubscribe(model.getSyncList(model.getToken())
                .compose(RxUtils.bindToLifecycle(getLifecycleProvider())) //请求与View周期同步（过度期，尽量少使用）
                .compose(RxUtils.schedulersTransformer()) //线程调度
                .compose(RxUtils.exceptionTransformer()) // 网络错误的异常转换, 这里可以换成自己的ExceptionHandle
                .subscribe(new Consumer<BaseResponse<List<SycnListModel>>>() {
                    @Override
                    public void accept(BaseResponse<List<SycnListModel>> response) throws Exception {
                        if (response.isOk()) {
                            List<SycnListModel> sycnListModelList = response.getData();
                            if (sycnListModelList.size() >= 3) {
                                if (sycnListModelList.get(0).getVersion() > model.getCheckObjectVersion()) {
                                    isCheckObject = true;
                                    model.saveCheckObjectVersion(sycnListModelList.get(0).getVersion());
                                }
                                if (sycnListModelList.get(1).getVersion() > model.getCheckCategoryVersion()) {
                                    isCheckCategory = true;
                                    model.saveCheckCategoryVersion(sycnListModelList.get(1).getVersion());
                                }
                                if (sycnListModelList.get(2).getVersion()>model.getAvatarsVersion()){
                                    isAvatars=true;
                                    model.saveAvatarsVersion(sycnListModelList.get(2).getVersion());
                                }
                            }
                        }
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        //关闭对话框
                        dismissDialog();
                        if (throwable instanceof ResponseThrowable) {
                            ToastUtils.showShort(((ResponseThrowable) throwable).message);
                        }
                    }
                }, new Action() {
                    @Override
                    public void run() throws Exception {
                        if (isDown){
                            checkObject();
                        }else {
                            if (isCheckObject){
                                checkObject();
                            }else if (isCheckCategory){
                                getAllCategoryList();
                            }else if (isAvatars){
                                getAvatars();
                            }else {
                                startCheck();
                            }
                        }
                    }
                }));
    }


    private void checkObject(){
        addSubscribe(model.checkObject(model.getToken())
                .compose(RxUtils.bindToLifecycle(getLifecycleProvider())) //请求与View周期同步（过度期，尽量少使用）
                .compose(RxUtils.schedulersTransformer()) //线程调度
                .compose(RxUtils.exceptionTransformer()) // 网络错误的异常转换, 这里可以换成自己的ExceptionHandle
                .subscribe(new Consumer<BaseResponse<List<FloorModel>>>() {
                    @Override
                    public void accept(BaseResponse<List<FloorModel>> response) throws Exception {
                        if (response.isOk()){
                            List<FloorModel> floorModelList =response.getData();
                            String json=new Gson().toJson(floorModelList);
                            FileUtil.save(getApplication(),json, Constant.checkObjectFileName);
                        }
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        //关闭对话框
                        dismissDialog();
                        if (throwable instanceof ResponseThrowable) {
                            ToastUtils.showShort(((ResponseThrowable) throwable).message);
                        }
                    }
                }, new Action() {
                    @Override
                    public void run() throws Exception {
                        if (isDown){
                            getAllCategoryList();
                        }else {
                            if (isCheckCategory){
                                getAllCategoryList();
                            }else if (isAvatars){
                                getAvatars();
                            }else {
                                startCheck();
                            }
                        }
                    }
                }));
    }


    private void getAllCategoryList(){
        addSubscribe(model.getAllCategoryList(model.getToken())
                .compose(RxUtils.bindToLifecycle(getLifecycleProvider())) //请求与View周期同步（过度期，尽量少使用）
                .compose(RxUtils.schedulersTransformer()) //线程调度
                .compose(RxUtils.exceptionTransformer()) // 网络错误的异常转换, 这里可以换成自己的ExceptionHandle
                .subscribe(new Consumer<BaseResponse<List<AllCategoryModel>>>() {
                    @Override
                    public void accept(BaseResponse<List<AllCategoryModel>> response) throws Exception {
                        if (response.isOk()){
                            List<AllCategoryModel> allCategoryModelList=response.getData();
                            String json=new Gson().toJson(allCategoryModelList);
                            FileUtil.save(getApplication(),json, Constant.checkCategoryFileName);
                        }
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        //关闭对话框
                        dismissDialog();
                        if (throwable instanceof ResponseThrowable) {
                            ToastUtils.showShort(((ResponseThrowable) throwable).message);
                        }
                    }
                }, new Action() {
                    @Override
                    public void run() throws Exception {
                        if (isAvatars){
                            getAvatars();
                        }else {
                            startCheck();
                        }
                    }
                }));
    }

    private void getAvatars(){
        addSubscribe(model.getStudentAvatars(model.getToken())
                .compose(RxUtils.bindToLifecycle(getLifecycleProvider())) //请求与View周期同步（过度期，尽量少使用）
                .compose(RxUtils.schedulersTransformer()) //线程调度
                .compose(RxUtils.exceptionTransformer()) // 网络错误的异常转换, 这里可以换成自己的ExceptionHandle
                .subscribe(new Consumer<BaseResponse<List<HeadModel>>>() {
                    @Override
                    public void accept(BaseResponse<List<HeadModel>> response) throws Exception {
                        if (response.isOk()){
                            mHeadModelList=response.getData();
                            List<String> paths=new ArrayList<>();
                            for (HeadModel headModel:mHeadModelList){
                                if (headModel.getAvatar().contains("PhotoFile")){
                                    paths.add(headModel.getAvatar());
                                }
                            }
                            if (paths.size()!=0){
                                String json=new Gson().toJson(paths);
                                FileUtil.save(getApplication(),json, Constant.avatarsFileName);
                            }
                        }
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        dismissDialog();
                        if (throwable instanceof ResponseThrowable) {
                            ToastUtils.showShort(((ResponseThrowable) throwable).message);
                        }
                    }
                }, new Action() {
                    @Override
                    public void run() throws Exception {
                        startCheck();
                    }
                }));
    }






    private void startCheck(){
        dismissDialog();
        String[] mCodes=model.getCodes().split(",");
        if (mCodes.length>1){
            startActivity(SelectMCodeActivity.class);
        }else if (mCodes.length==1){
            if (NetworkUtil.isNetworkAvailable(getApplication())){
                model.saveCode(appListModelList.get(0).getApps().get(0).getCode());
            }
            ARouter.getInstance().build(RouterActivityPath.Check.PAGER_CHECK).navigation();
        }
        if (!SPUtils.getInstance().getBoolean("isDownHeadPicSuccess",false)){
            startService();
        }
        finish();
    }

    public void startService(){
        Intent intent = new Intent(getApplication(), DownPicService.class);
        //开启服务
        AppManager.getAppManager().currentActivity().startService(intent);
    }

}
