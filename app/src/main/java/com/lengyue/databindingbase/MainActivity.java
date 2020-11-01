package com.lengyue.databindingbase;

import android.view.View;

import androidx.lifecycle.Observer;

import com.lengyue.databinding_base.BaseActivity;
import com.lengyue.databinding_base.bean.Resource;
import com.lengyue.databindingbase.databinding.ActivityMainBinding;

public class MainActivity extends BaseActivity<MainViewModel, ActivityMainBinding> {

    @Override
    protected int getContentViewId() {
        return R.layout.activity_main;
    }

    @Override
    protected void processLogic() {
        mViewModel.login(new UserBean()).observe(this, new Observer<Resource<UserBean>>() {
            @Override
            public void onChanged(Resource<UserBean> userBeanResource) {
                userBeanResource.handler(new OnCallback<UserBean>() {
                    @Override
                    public void onSuccess(UserBean data) {

                    }
                });
            }
        });
    }

    @Override
    protected void setListener() {

    }

    @Override
    public void onClick(View view) {

    }
}
