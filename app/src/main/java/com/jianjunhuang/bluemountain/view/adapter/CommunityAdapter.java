package com.jianjunhuang.bluemountain.view.adapter;

import android.content.Context;

import com.demo.jianjunhuang.mvptools.adapter.RecyclerAdapter;
import com.demo.jianjunhuang.mvptools.adapter.RecyclerViewHolder;
import com.jianjunhuang.bluemountain.model.bean.Community;

import java.util.List;

public class CommunityAdapter extends RecyclerAdapter<Community> {


    public CommunityAdapter(Context context, List<Community> list, int layoutId) {
        super(context, list, layoutId);
    }

    @Override
    public void convert(RecyclerViewHolder viewHolder, Community community) {

    }
}
