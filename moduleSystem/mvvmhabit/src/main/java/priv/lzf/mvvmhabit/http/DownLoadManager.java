package priv.lzf.mvvmhabit.http;

import android.util.Log;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import priv.lzf.mvvmhabit.http.download.DownLoadSubscriber;
import priv.lzf.mvvmhabit.http.download.ProgressCallBack;
import priv.lzf.mvvmhabit.http.interceptor.ProgressInterceptor;
import okhttp3.OkHttpClient;
import okhttp3.ResponseBody;
import priv.lzf.mvvmhabit.utils.KLog;
import priv.lzf.mvvmhabit.utils.ToastUtils;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.http.GET;
import retrofit2.http.Streaming;
import retrofit2.http.Url;

/**
 * Created by goldze on 2017/5/11.
 * 文件下载管理，封装一行代码实现下载
 */

public class DownLoadManager {
    private static DownLoadManager instance;

    private static Retrofit retrofit;

    private DownLoadManager() {
        buildNetWork();
    }

    /**
     * 单例模式
     *
     * @return DownLoadManager
     */
    public static DownLoadManager getInstance() {
        if (instance == null) {
            instance = new DownLoadManager();
        }
        return instance;
    }

    //下载
    public void load(String downUrl, final ProgressCallBack callBack) {
        retrofit.create(ApiService.class)
                .download(downUrl)
                .subscribeOn(Schedulers.io())//请求网络 在调度者的io线程
                .observeOn(Schedulers.io()) //指定线程保存文件
                .doOnNext(new Consumer<ResponseBody>() {
                    @Override
                    public void accept(ResponseBody responseBody) throws Exception {
                        callBack.saveFile(responseBody);
                    }
                })
                .observeOn(AndroidSchedulers.mainThread()) //在主线程中更新ui
                .subscribe(new DownLoadSubscriber<ResponseBody>(callBack));
    }

    //下载
    public void load(final List<String> downUrls, final String destFileDir, final ProgressCallBack callBack) {
        //注意：此处是保存多张图片，可以采用异步线程
        ArrayList<Observable<Boolean>> observables = new ArrayList<>();
        final AtomicInteger count = new AtomicInteger();
        for (final String image : downUrls){
            observables.add(retrofit.create(ApiService.class)
                    .download(image)
                            .subscribeOn(Schedulers.io())
                            .map(new Function<ResponseBody, Boolean>() {
                                @Override
                                public Boolean apply(ResponseBody responseBody) throws Exception {
                                    callBack.saveFile(responseBody,destFileDir,image.split("PhotoFile/")[1]);
                                    return true;
                                }
                            })

            );
        }
        // observable的merge 将所有的observable合成一个Observable，所有的observable同时发射数据
        Observable.merge(observables)
                .subscribeOn(Schedulers.io())
                .observeOn(Schedulers.io()) //指定线程保存文件
                .doOnNext(new Consumer<Boolean>() {
                    @Override
                    public void accept(Boolean aBoolean) throws Exception {

                    }
                })
                .observeOn(AndroidSchedulers.mainThread()) //在主线程中更新ui
                .subscribe(new DownLoadSubscriber<Boolean>(callBack));
    }



    private void buildNetWork() {
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .addInterceptor(new ProgressInterceptor())
                .connectTimeout(20, TimeUnit.SECONDS)
                .build();

        retrofit = new Retrofit.Builder()
                .client(okHttpClient)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .baseUrl(NetworkUtil.url)
                .build();
    }

    private interface ApiService {
        @Streaming
        @GET
        Observable<ResponseBody> download(@Url String url);
    }
}
