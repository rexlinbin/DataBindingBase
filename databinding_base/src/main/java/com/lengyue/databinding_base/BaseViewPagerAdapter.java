package com.lengyue.databinding_base;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.viewpager.widget.PagerAdapter;

import java.util.List;

public abstract class BaseViewPagerAdapter<T, B extends ViewDataBinding> extends PagerAdapter {

    private Context mContext;
    private List<T> mList;

    public BaseViewPagerAdapter(Context context) {
        this.mContext = context;
    }

    public BaseViewPagerAdapter(Context context, List<T> list) {
        this.mContext = context;
        this.mList = list;
    }

    public Context getContext(){
        return mContext;
    }

    @Override
    public int getCount() {
        return mList == null ? 0 : mList.size();
    }

    @Override
    public boolean isViewFromObject(@NonNull View view, @NonNull Object object) {
        ViewDataBinding binding = (ViewDataBinding)object;
        return view == binding.getRoot();
    }

    @NonNull
    @Override
    public Object instantiateItem(@NonNull ViewGroup container, int position) {
        B binding = DataBindingUtil.inflate(LayoutInflater.from(mContext), getLayoutResId(), container, false);
        final T t = mList.get(position);
        onBindItem(binding, t, position);
        container.addView(binding.getRoot());
        return binding;
    }

    /*
     * 解决viewpager在刷新调用notifyDataSetChanged不起作用 使用懒加载后没有效果?
     */
    public int getItemPosition(Object object) {
        return POSITION_NONE;
    }

    @Override
    public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
        ViewDataBinding binding = (ViewDataBinding)object;
        container.removeView(binding.getRoot());

    }

    @LayoutRes
    protected abstract int getLayoutResId();

    protected abstract void onBindItem(B binding, T t, int position);
}
