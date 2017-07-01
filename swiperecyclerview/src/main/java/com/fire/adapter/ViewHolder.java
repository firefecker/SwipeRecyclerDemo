package com.fire.adapter;

import android.support.annotation.DrawableRes;
import android.support.annotation.IdRes;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.SparseArray;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * Created by Administrator on 2017/6/24.
 * Description : RecyclerView的通用ViewHolder
 */

public class ViewHolder extends RecyclerView.ViewHolder{

    //用于缓存已经找到的View，减少findViewById的次数。SpaceArray比HashMap更节省内存，更加高效
    private SparseArray<View> mView;

    public ViewHolder(View itemView) {
        super(itemView);
        mView = new SparseArray<>();
    }

    /**
     * 从ItemView中获取里面的View
     * @param viewId  ItemView里面包含的一个ViewId
     * @param <T> View 泛型减少强制类型转换的操作
     * @return
     */
    public <T extends View> T getView (@IdRes int viewId) {
        //多次findViewById，对已有的找到的View做缓存
        View view = mView.get(viewId);

        //使用缓存的方式减少findViewById的次数
        if (view == null) {
            view = itemView.findViewById(viewId);
            mView.put(viewId,view);
        }
        return (T)view;
    }

    //通用功能的封装，实现链式调用

    /**
     * 设置文本
     * @param viewId
     * @param text
     * @return
     */
    public ViewHolder setText(@IdRes int viewId,String text) {
        TextView t = getView(viewId);
        if (TextUtils.isEmpty(text)) {
            t.setText("");
        } else {
            t.setText(text);
        }
        return this;
    }

    /**
     * 设置一个View显示或者隐藏
     * @param viewId
     * @param isVisible
     * @return
     */
    public ViewHolder setVisible(@IdRes int viewId,boolean isVisible) {
        View view = getView(viewId);
        if (isVisible) {
            view.setVisibility(View.VISIBLE);
        } else {
            view.setVisibility(View.GONE);
        }
        return this;
    }

    /**
     * 设置图片资源
     * @param viewId
     * @param resourceId
     * @return
     */
    public ViewHolder setImageResource (@IdRes int viewId, @DrawableRes int resourceId) {

        ImageView imageView = getView(viewId);
        imageView.setImageResource(resourceId);
        return this;
    }

    /**
     * ItemView条目的点击事件
     * @param onItemClickListener
     * @return
     */
    public ViewHolder setOnItemClickListener (View.OnClickListener onItemClickListener) {
        itemView.setOnClickListener(onItemClickListener);
        return this;
    }

    /**
     * 设置条目里面某一个View的点击事件
     * @param viewId
     * @param onClickListener
     * @return
     */
    public ViewHolder setOnClickListener(@IdRes int viewId, View.OnClickListener onClickListener) {
        getView(viewId).setOnClickListener(onClickListener);
        return this;
    }

    //图片处理问题 路径问题 用到第三方的 ImageLoader    Glide   Picasso

    public ViewHolder setImagePath (int viewId,HolderImageLoader imageLoader) {
        ImageView imageView = getView(viewId);
        imageLoader.loadImage(imageView,imageLoader.getPath());
        return this;
    }


    public abstract static class HolderImageLoader {

        private String mPath;

        public HolderImageLoader(String path) {
            this.mPath = path;
        }

        /**
         * 需要去重写这个方法，去加载网络或者本地图片
         * @param imageView
         * @param path
         */
        public abstract void loadImage(ImageView imageView,String path);

        public String getPath() {
            return mPath;
        }
    }

}
