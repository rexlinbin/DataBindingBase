package com.lengyue.databindingbase;

import android.app.Application;

import com.lengyue.databinding_base.network.RetrofitManager;

public class MyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        RetrofitManager.getInstance().initRetrofit("https://www.fiplusafrica.com/fipluskenyaApp/", MyRetrofitApiService.class);
    }
}
