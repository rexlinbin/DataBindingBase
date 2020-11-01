package com.lengyue.databindingbase;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.lengyue.databinding_base.BaseViewModel;
import com.lengyue.databinding_base.bean.Resource;
import com.lengyue.databindingbase.common.GlobalParams;

public class MainViewModel extends BaseViewModel<MyRetrofitApiService> {
    public MainViewModel(@NonNull Application application) {
        super(application);
    }

    public MutableLiveData<Resource<UserBean>> login(UserBean bean){
        MutableLiveData<Resource<UserBean>> liveData = new MutableLiveData<>();
        return observeGo(getApiService().login(GlobalParams.headers, bean), liveData);
    }
}
