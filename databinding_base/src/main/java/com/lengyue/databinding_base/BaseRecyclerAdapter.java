package com.lengyue.databinding_base;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.LayoutRes;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public abstract class BaseRecyclerAdapter<T, B extends ViewDataBinding> extends RecyclerView.Adapter {

    protected OnBindingItemListener<T, B> itemListener;

    private Context mContext;
    private List<T> mList;

    public BaseRecyclerAdapter(Context context) {
        this.mContext = context;
    }

    public BaseRecyclerAdapter(Context context, List<T> list) {
        this.mContext = context;
        this.mList = list;
    }
    @Override
    public int getItemCount() {
        return mList == null ? 0 : mList.size();
    }

    public void refreshData(List<T> list){
        if (mList == null){
            mList = new ArrayList<>();
        }else{
            mList.clear();
        }
        mList.addAll(list);
        notifyDataSetChanged();
    }

    public List<T> getData(){
        return mList;
    }

    public void setOnItemClickListener(OnBindingItemListener<T, B> bindingItemListener){
        this.itemListener = bindingItemListener;
    }

    public Context getContext(){
        return mContext;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        B bing = DataBindingUtil.inflate(LayoutInflater.from(mContext), getLayoutResId(viewType), parent, false);
        return new RecyclerHolder(bing.getRoot());
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, final int position) {
        final B binding = DataBindingUtil.getBinding(holder.itemView);
        final T t = mList.get(position);
        if (binding != null) {
            binding.getRoot().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (itemListener != null) {
                        itemListener.onItemClick(binding, t, position);
                    }
                }
            });
        }
        onBindItem(binding, t, position);
    }

    public void removeItem(int position){
        mList.remove(position);
        notifyItemRemoved(position);
        notifyItemRangeChanged(position, mList.size() - position);
    }

    public void addItem(T t, int position){
        mList.add(position,t);
        notifyItemInserted(position);
        notifyItemRangeChanged(position, mList.size() - position);
    }

    public void removeItems(int position, int count){
        for (int i = 0; i < count; i++) {
            mList.remove(position);
        }
        notifyItemRangeRemoved(position, count);
        notifyItemRangeChanged(position, mList.size() - position);
    }

    public void addItems(List<T> list, int position){
        mList.addAll(position, list);
        notifyItemRangeInserted(position, list.size());
        notifyItemRangeChanged(position, mList.size() - position);
    }
    @LayoutRes
    protected abstract int getLayoutResId(int viewType);

    protected abstract void onBindItem(B binding, T t, int position);

    private static class RecyclerHolder extends RecyclerView.ViewHolder {

        private RecyclerHolder(View itemView) {
            super(itemView);
        }
    }

    @FunctionalInterface
    public interface OnBindingItemListener<T, B extends ViewDataBinding> {
        void onItemClick(B binding, T t, int position);
    }
}

