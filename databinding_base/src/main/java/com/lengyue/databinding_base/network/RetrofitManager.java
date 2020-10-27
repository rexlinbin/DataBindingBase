package com.lengyue.databinding_base.network;


import com.lengyue.databinding_base.network.Interceptor.HttpLogInterceptor;

import java.lang.reflect.ParameterizedType;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitManager<T extends RetrofitApiService> {
    private static RetrofitManager retrofitManager;
    private T retrofitApiService;
    private OkHttpClient okHttpClient;

    private RetrofitManager() {
        initRetrofit();
    }

    public static <T extends RetrofitApiService> RetrofitManager getInstance() {
        if (retrofitManager == null) {
            synchronized (RetrofitManager.class) {
                if (retrofitManager == null) {
                    retrofitManager = new RetrofitManager<T>();
                }
            }
        }
        return retrofitManager;
    }

    public T getApiService() {
        return (T) retrofitManager.retrofitApiService;
    }

    private void initRetrofit() {
        okHttpClient = new OkHttpClient.Builder()
                .addInterceptor(new HttpLogInterceptor())//设置日志打印
                .retryOnConnectionFailure(true)//失败重连
                .connectTimeout(30, TimeUnit.SECONDS)//网络请求超时时间单位为秒
                .build();

        Retrofit retrofit = new Retrofit.Builder()
                .client(okHttpClient)
                .baseUrl(Urls.DEFAULT_SERVER)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
        retrofitApiService = retrofit.create(getTClass());
    }

    public Class<T> getTClass() {
        return (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
    }

}
