package com.jianjunhuang.bluemountain.view.fragment;

import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffColorFilter;
import android.graphics.drawable.Drawable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.demo.jianjunhuang.mvptools.integration.BaseFragment;
import com.jianjunhuang.bluemountain.R;
import com.jianjunhuang.bluemountain.model.bean.User;
import com.jianjunhuang.bluemountain.view.adapter.CoffeeAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jianjunhuang on 18-3-24.
 */

public class CoffeeFragment extends BaseFragment {

    private RecyclerView userRv;
    private List<User> mList = new ArrayList<>();
    private RelativeLayout reserveRl;
    private LinearLayout reserveInfoLl;
    private FloatingActionButton reserveFab;
    private TextView temperatureTv;
    private TextView statusTv;

    @Override
    protected int getLayoutId() {
        return R.layout.coffee_fragment;
    }

    @Override
    protected void initView(View view) {
        userRv = findView(R.id.coffee_info_rv);
        reserveRl = findView(R.id.coffee_reserve_rl);
        reserveInfoLl = findView(R.id.coffee_reserve_info_ll);
        reserveFab = findView(R.id.coffee_reserve_fab);
        temperatureTv = findView(R.id.coffee_reserve_temperature_tv);
        statusTv = findView(R.id.coffee_reserve_status_tv);

        LinearLayoutManager mManager = new LinearLayoutManager(getContext());
        mManager.setOrientation(LinearLayoutManager.VERTICAL);
        userRv.setLayoutManager(mManager);
        for (int i = 0; i < 20; i++) {
            User user = new User();
            user.setName(i + "");
            mList.add(user);
        }
        userRv.setAdapter(new CoffeeAdapter(getContext(), mList, R.layout.coffee_item_layout));

    }

    private static final String TAG = "CoffeeFragment";


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

        reserveFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                reserveInfoLl.setBackgroundResource(R.drawable.reserve_making_background);
                temperatureTv.setTextColor(Color.WHITE);
                statusTv.setTextColor(Color.WHITE);
            }
        });
    }


    @Override
    protected void onLazyLoad() {
        super.onLazyLoad();

    }

    @Override
    public void onPause() {
        super.onPause();
    }
}
