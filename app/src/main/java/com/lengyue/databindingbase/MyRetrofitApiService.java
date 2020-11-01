package com.lengyue.databindingbase;

import com.lengyue.databinding_base.bean.ResponModel;
import com.lengyue.databinding_base.network.RetrofitApiService;

import java.util.Map;

import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.HeaderMap;
import retrofit2.http.POST;

public interface MyRetrofitApiService {
    @POST("customer/login")
    Observable<ResponModel<UserBean>> login(@HeaderMap Map<String, Object> headerMap, @Body UserBean bean);
}
