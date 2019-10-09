package com.lzf.login.viewModel;

import android.app.Application;
import android.databinding.ObservableField;
import android.support.annotation.NonNull;
import android.text.TextUtils;

import com.alibaba.android.arouter.launcher.ARouter;
import com.google.gson.Gson;
import com.lzf.greendao.entity.UserModel;
import com.lzf.greendao.service.UserService;
import com.lzf.http.data.Injection;
import com.lzf.http.data.Repository;
import com.lzf.http.entity.CheckModel;
import com.lzf.http.entity.LoginModel;
import com.lzf.login.entity.LoginEntity;
import com.nhsoft.base.router.RouterActivityPath;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import priv.lzf.mvvmhabit.base.BaseViewModel;
import priv.lzf.mvvmhabit.binding.command.BindingAction;
import priv.lzf.mvvmhabit.binding.command.BindingCommand;
import priv.lzf.mvvmhabit.http.BaseResponse;
import priv.lzf.mvvmhabit.http.ResponseThrowable;
import priv.lzf.mvvmhabit.utils.KLog;
import priv.lzf.mvvmhabit.utils.RxUtils;
import priv.lzf.mvvmhabit.utils.ToastUtils;

/**
 * 作者：Created by 45703
 * 时间：Created on 2019/10/3.
 */
public class LoginViewModel extends BaseViewModel<Repository> {

    public ObservableField<LoginEntity> entity=new ObservableField<>();

    public LoginViewModel(@NonNull Application application) {
        super(application);
        this.entity.set(new LoginEntity());
        //RaJava模拟登录
        model=Injection.provideDemoRepository();
        entity.get().username.set(model.getUserName());
        entity.get().password.set(model.getPassword());
        bindingCommand();
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
                                checkObject();
//                                ARouter.getInstance().build(RouterActivityPath.Check.PAGER_CHECK).navigation();
//                                finish();
                            }else {
                                ToastUtils.showShort("保存失败");
                            }
                        }else {
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
                }, new Action() {
                    @Override
                    public void run() throws Exception {
                        //关闭对话框
//                        dismissDialog();
                    }
                }));

    }


    private void checkObject(){
        addSubscribe(model.checkObject(model.getToken())
                .compose(RxUtils.bindToLifecycle(getLifecycleProvider())) //请求与View周期同步（过度期，尽量少使用）
                .compose(RxUtils.schedulersTransformer()) //线程调度
                .compose(RxUtils.exceptionTransformer()) // 网络错误的异常转换, 这里可以换成自己的ExceptionHandle
                .subscribe(new Consumer<BaseResponse<List<CheckModel>>>() {
                    @Override
                    public void accept(BaseResponse<List<CheckModel>> response) throws Exception {
                        KLog.e(new Gson().toJson(response.getData()));
                        if (response.isOk()){
                            List<CheckModel> checkModelList=response.getData();
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
                        dismissDialog();
                    }
                }));
    }

}
