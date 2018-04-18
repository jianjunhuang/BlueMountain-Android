package com.jianjunhuang.bluemountain.view.fragment;

import android.content.Context;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;

import com.demo.jianjunhuang.mvptools.adapter.RecyclerAdapter;
import com.demo.jianjunhuang.mvptools.adapter.RecyclerViewHolder;
import com.demo.jianjunhuang.mvptools.integration.BaseFragment;
import com.jianjunhuang.bluemountain.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jianjunhuang on 18-3-24.
 */

public class CommunityFragment extends BaseFragment {

    private List<String> list = new ArrayList<>();
    private RecyclerView communityRv;
    private FloatingActionButton addFab;

    @Override
    protected int getLayoutId() {
        return R.layout.commiunty_fragment;
    }

    @Override
    protected void initView(View view) {
        communityRv = findView(R.id.community_rv);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        communityRv.setLayoutManager(layoutManager);
        addFab = findView(R.id.community_fab);
        for (int i = 0; i < 20; i++) {
            list.add(null);
        }

        communityRv.setAdapter(new CommunityAdapter(getContext(), list, R.layout.community_item_layout));

    }

    private static final String TAG = "CommunityFragment";

    @Override
    protected void initListener() {

    }

    class CommunityAdapter extends RecyclerAdapter<String> {


        public CommunityAdapter(Context context, List<String> list, int layoutId) {
            super(context, list, layoutId);
        }

        @Override
        public void convert(RecyclerViewHolder viewHolder, String s) {

        }
    }

}
