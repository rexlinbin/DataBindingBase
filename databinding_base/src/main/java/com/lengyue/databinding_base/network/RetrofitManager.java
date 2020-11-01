package com.lengyue.databinding_base.network;


import com.lengyue.databinding_base.network.Interceptor.HttpLogInterceptor;

import java.lang.reflect.ParameterizedType;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitManager<T> {
    public static String DEFAULT_SERVER = "http://127.0.0.1/";
    private static RetrofitManager retrofitManager;
    private T retrofitApiService;
    private OkHttpClient okHttpClient;

    private RetrofitManager() {

    }

    public static <T> RetrofitManager getInstance() {
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

    public void initRetrofit(String url, Class classz) {
        DEFAULT_SERVER = url;
        okHttpClient = new OkHttpClient.Builder()
                //设置日志打印
                .addInterceptor(new HttpLogInterceptor())
                //失败重连
                .retryOnConnectionFailure(true)
                //网络请求超时时间单位为秒
                .connectTimeout(30, TimeUnit.SECONDS)
                .build();

        Retrofit retrofit = new Retrofit.Builder()
                .client(okHttpClient)
                .baseUrl(Urls.DEFAULT_SERVER)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
        retrofitApiService = (T) retrofit.create(classz);
    }

}
