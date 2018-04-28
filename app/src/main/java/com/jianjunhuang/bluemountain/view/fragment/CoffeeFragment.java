package com.jianjunhuang.bluemountain.view.fragment;

import android.content.Intent;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ListView;

import com.demo.jianjunhuang.mvptools.adapter.CommonAdapter;
import com.demo.jianjunhuang.mvptools.adapter.CommonViewHolder;
import com.demo.jianjunhuang.mvptools.adapter.RecyclerAdapter;
import com.demo.jianjunhuang.mvptools.adapter.RecyclerViewHolder;
import com.demo.jianjunhuang.mvptools.integration.BaseFragment;
import com.demo.jianjunhuang.mvptools.utils.ToastUtils;
import com.jianjunhuang.bluemountain.R;
import com.jianjunhuang.bluemountain.application.UserInfo;
import com.jianjunhuang.bluemountain.model.bean.CoffeUserBean;
import com.jianjunhuang.bluemountain.model.bean.User;
import com.jianjunhuang.bluemountain.utils.WaveHelper;
import com.jianjunhuang.bluemountain.view.activity.ConnectActivity;
import com.jianjunhuang.bluemountain.view.activity.SignInUpActivity;
import com.jianjunhuang.bluemountain.view.adapter.CoffeeAdapter;
import com.jianjunhuang.lib.waveview.Wave;
import com.jianjunhuang.lib.waveview.WaveView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jianjunhuang on 18-3-24.
 */

public class CoffeeFragment extends BaseFragment {

    private RecyclerView userRv;
    private List<User> mList = new ArrayList<>();


    @Override
    protected int getLayoutId() {
        return R.layout.coffee_fragment;
    }

    @Override
    protected void initView(View view) {
        userRv = findView(R.id.coffee_info_rv);
        LinearLayoutManager mManager = new LinearLayoutManager(getContext());
        mManager.setOrientation(LinearLayoutManager.VERTICAL);
        userRv.setLayoutManager(mManager);
        for (int i = 0; i < 20; i++) {
            User user = new User();
            user.setName(i + "");
            mList.add(user);
        }
        userRv.setAdapter(new CoffeeAdapter(getContext(), mList, R.layout.coffee_item_layout));

//        initInfoView(view);

    }



    @Override
    protected void initListener() {
//        coffeeInfoView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                //jude if login
//                //no -> jump to login activity
//                //jude if connected
//                //no -> jump to connect activity
//                if (null == UserInfo.getUser()) {
//                    Intent intent = new Intent(getActivity(), SignInUpActivity.class);
//                    startActivity(intent);
//                    return;
//                }
//                if (null == UserInfo.getUser().getMachineId()) {
//                    Intent intent = new Intent(getActivity(), ConnectActivity.class);
//                    startActivity(intent);
//                    return;
//                }
//                ToastUtils.show("click");
//            }
//        });
    }

    private static final String TAG = "CoffeeFragment";

    @Override
    protected void onLazyLoad() {
        super.onLazyLoad();

    }

    @Override
    public void onPause() {
        super.onPause();
    }
}
