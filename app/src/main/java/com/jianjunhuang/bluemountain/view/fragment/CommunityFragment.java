package com.jianjunhuang.bluemountain.view.fragment;

import android.content.Context;
import android.content.Intent;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;

import com.demo.jianjunhuang.mvptools.adapter.RecyclerAdapter;
import com.demo.jianjunhuang.mvptools.adapter.RecyclerViewHolder;
import com.demo.jianjunhuang.mvptools.integration.BaseFragment;
import com.jianjunhuang.bluemountain.R;
import com.jianjunhuang.bluemountain.application.UserInfo;
import com.jianjunhuang.bluemountain.contact.CommunityContact;
import com.jianjunhuang.bluemountain.model.bean.Community;
import com.jianjunhuang.bluemountain.presenter.CommunityPresenter;
import com.jianjunhuang.bluemountain.view.activity.CommunityAddActivity;
import com.jianjunhuang.bluemountain.view.adapter.CommunityAdapter;
import com.jianjunhuang.bluemountain.view.widget.EmptyView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jianjunhuang on 18-3-24.
 */

public class CommunityFragment extends BaseFragment implements CommunityContact.View<Community> {

    private List<Community> list = new ArrayList<>();
    private RecyclerView communityRv;
    private FloatingActionButton addFab;
    private CommunityAdapter mAdapter;
    private EmptyView emptyView;
    private CommunityPresenter mPresenter;

    @Override
    protected int getLayoutId() {
        return R.layout.commiunty_fragment;
    }

    @Override
    protected void initView(View view) {
        mPresenter = new CommunityPresenter(this);
        communityRv = findView(R.id.community_rv);
        emptyView = new EmptyView(findView(R.id.empty_layout));
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        communityRv.setLayoutManager(layoutManager);
        addFab = findView(R.id.community_fab);
        mAdapter = new CommunityAdapter(getContext(), list, R.layout.community_item_layout);
        communityRv.setAdapter(mAdapter);

    }

    private static final String TAG = "CommunityFragment";

    @Override
    protected void initListener() {
        addFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), CommunityAddActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public void onGetSuccess(List<Community> list) {
        if (list.size() == 0) {
            emptyView.setTips(EmptyView.DEFALT_TIPS);
            emptyView.show();
        } else {
            this.list = list;
            emptyView.hide();
            mAdapter.setOnDataChange(list);
        }
    }

    @Override
    public void onGetFailed(String reason) {
        emptyView.setTips(reason);
        emptyView.show();
    }

    @Override
    public void onSendSuccess() {

    }

    @Override
    public void onSendFailed(String reason) {

    }

    @Override
    protected void onLazyLoad() {
        super.onLazyLoad();
        mPresenter.getCommunity(UserInfo.getUser().getUserId(),
                UserInfo.getMachine().getMachineId());
        if (list.size() == 0) {
            emptyView.setTips(EmptyView.DEFALT_TIPS);
            emptyView.show();
        } else {
            emptyView.hide();
        }

    }
}
