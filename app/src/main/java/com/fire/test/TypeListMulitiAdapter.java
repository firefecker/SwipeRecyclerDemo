package com.fire.test;

import android.content.Context;

import com.fire.adapter.MulitiYTypeSupport;
import com.fire.adapter.RecyclerCommonAdapter;
import com.fire.adapter.ViewHolder;

import java.util.List;

/**
 * Created by Administrator on 2017/7/1.
 */
public class TypeListMulitiAdapter extends RecyclerCommonAdapter<MultiEntity> implements MulitiYTypeSupport<MultiEntity> {

    public TypeListMulitiAdapter(Context mContext, List<MultiEntity> results) {
        super(mContext,results);
        setMulitiYTypeSupport(this);
    }

    @Override
    protected void convert(ViewHolder holder, MultiEntity data, int position) {
        holder.setText(R.id.text1, data.getType()+"")
                .setText(R.id.text,data.getName());
        switch (data.getType()) {
            case 0:
                holder.setImageResource(R.id.image1,R.mipmap.ic_launcher);
                break;
            case 1:
                holder.setImageResource(R.id.image2,R.mipmap.ic_launcher);
                break;
            case 2:
                holder.setImageResource(R.id.image3,R.mipmap.ic_launcher);
                break;
            default:
                holder.setImageResource(R.id.image1,R.mipmap.ic_launcher);
                break;
        }
    }

    @Override
    public int getLayoutId(MultiEntity item) {
        switch (item.getType()) {
            case 0:
                return R.layout.item_one;
            case 1:
                return R.layout.item_two;
            case 2:
                return R.layout.item_three;
            default:
                return R.layout.item_one;
        }
    }

}
