package com.fire.test;

import android.content.Context;

import com.fire.adapter.RecyclerCommonAdapter;
import com.fire.adapter.ViewHolder;

import java.util.List;

/**
 * Created by Administrator on 2017/7/1.
 */
public class TypeListAdapter extends RecyclerCommonAdapter<MultiEntity>{

    public TypeListAdapter(Context context, List<MultiEntity> data) {
        super(context, data,R.layout.item_one);
    }

    @Override
    protected void convert(ViewHolder holder, MultiEntity data, int position) {
        holder.setText(R.id.text1, data.getType()+"")
                .setText(R.id.text,data.getName())
                .setImageResource(R.id.image1,R.mipmap.ic_launcher);;
    }
}
