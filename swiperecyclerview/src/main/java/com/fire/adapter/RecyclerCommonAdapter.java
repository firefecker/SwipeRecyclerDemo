package com.fire.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

/**
 * Created by Administrator on 2017/6/24.
 * Description : RecyclerView的通用adapter
 */

public abstract class RecyclerCommonAdapter<T> extends RecyclerView.Adapter<ViewHolder>{

    //条目Id不一样那么只能通过参数传递
    protected int mLayoutId;

    //参数通用那么就只能用泛型
    protected List<T> mData;

    //实例化View的LayoutInflate
    protected LayoutInflater mInflater;

    //上下文
    protected Context mContext;

    //item的点击事件 只能利用接口回调设置点击事件
    protected ItemClickListener itemClickListener;

    //Item的长按事件
    protected ItemLongClickListener itemLongClickListener;

    private MulitiYTypeSupport mulitiYTypeSupport;

    public RecyclerCommonAdapter(Context context, List<T> data, MulitiYTypeSupport mulitiYTypeSupport) {
        this(context,data,-1);
        this.mulitiYTypeSupport = mulitiYTypeSupport;
    }

    public RecyclerCommonAdapter(Context context, List<T> data) {
        this(context,data,-1);
    }

    public RecyclerCommonAdapter(Context context,List<T> data, int layoutId) {
        this.mContext = context;
        mInflater = LayoutInflater.from(context);
        this.mData = data;
        this.mLayoutId = layoutId;

    }

    public void setMulitiYTypeSupport(MulitiYTypeSupport mulitiYTypeSupport) {
        this.mulitiYTypeSupport = mulitiYTypeSupport;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        if (mulitiYTypeSupport != null) {
            //需要多布局
            mLayoutId = viewType;
        }

        //创建View context上下文是必不可少的。
        View itemView = mInflater.inflate(mLayoutId, parent, false);
        //实例化View的方式有好多种,大概有三种
        //1.上面的一种方式
        //2.View.inflate(mContext,mLayoutId,null);
        //3.LayoutInflater.from(mContext).inflate(mLayoutId,parent);
        return new ViewHolder(itemView);
    }

    @Override
    public int getItemViewType(int position) {
        //多布局问题
        if (mulitiYTypeSupport != null) {
            return mulitiYTypeSupport.getLayoutId(mData.get(position));
        }
        return super.getItemViewType(position);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        //ViewHolder的优化
        convert(holder,mData.get(position),position);

        //Item的条目点击事件
        if (itemClickListener != null) {
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    itemClickListener.onItemClick(position);
                }
            });
        }

        //条目的长按事件
        if (itemLongClickListener != null) {
            holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    itemLongClickListener.onLongClick(position);
                    return true;
                }
            });
        }
    }

    /**
     * 把必要参数传出去
     * @param holder ViewHolder
     * @param data  当前位置的条目数据
     * @param position 当前的位置
     */
    protected abstract void convert(ViewHolder holder, T data,int position);

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public void setItemClickListener(ItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    public void setItemLongClickListener(ItemLongClickListener itemLongClickListener) {
        this.itemLongClickListener = itemLongClickListener;
    }
}
