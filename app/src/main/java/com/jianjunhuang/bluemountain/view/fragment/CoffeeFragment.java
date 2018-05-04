package com.jianjunhuang.bluemountain.view.fragment;

import android.app.AlertDialog;
import android.content.DialogInterface;
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
import com.demo.jianjunhuang.mvptools.utils.ToastUtils;
import com.jianjunhuang.bluemountain.R;
import com.jianjunhuang.bluemountain.application.UserInfo;
import com.jianjunhuang.bluemountain.contact.CoffeeContact;
import com.jianjunhuang.bluemountain.model.bean.Machine;
import com.jianjunhuang.bluemountain.model.bean.User;
import com.jianjunhuang.bluemountain.presenter.CoffeePresenter;
import com.jianjunhuang.bluemountain.view.adapter.CoffeeAdapter;
import com.jianjunhuang.bluemountain.view.widget.EmptyView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jianjunhuang on 18-3-24.
 */

public class CoffeeFragment extends BaseFragment implements CoffeeContact.View {

    private RecyclerView userRv;
    private List<User> mList = new ArrayList<>();
    private RelativeLayout reserveRl;
    private LinearLayout reserveInfoLl;
    private FloatingActionButton reserveFab;
    private TextView temperatureTv;
    private TextView statusTv;
    private EmptyView emptyView;

    private CoffeePresenter mPresenter;
    private CoffeeAdapter mAdapter;

    private AlertDialog alertDialog;

    @Override
    protected int getLayoutId() {
        return R.layout.coffee_fragment;
    }

    @Override
    protected void initView(View view) {
        mPresenter = new CoffeePresenter(this);
        userRv = findView(R.id.coffee_info_rv);
        reserveRl = findView(R.id.coffee_reserve_rl);
        reserveInfoLl = findView(R.id.coffee_reserve_info_ll);
        reserveFab = findView(R.id.coffee_reserve_fab);
        temperatureTv = findView(R.id.coffee_reserve_temperature_tv);
        statusTv = findView(R.id.coffee_reserve_status_tv);
        emptyView = new EmptyView(findView(R.id.empty_layout));

        LinearLayoutManager mManager = new LinearLayoutManager(getContext());
        mManager.setOrientation(LinearLayoutManager.VERTICAL);
        userRv.setLayoutManager(mManager);
        mAdapter = new CoffeeAdapter(getContext(), mList, R.layout.coffee_item_layout);
        userRv.setAdapter(mAdapter);
        initAlertDialog();
    }

    private static final String TAG = "CoffeeFragment";

    private void initAlertDialog() {
        alertDialog = new AlertDialog.Builder(getContext(), R.style.TipsDialogTheme)
                .setTitle("Tips")
                .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                }).setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                }).create();
        alertDialog.setMessage("快去倒咖啡啦！");
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

        reserveFab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPresenter.orderCoffee(UserInfo.getMachine().getMachineId(),
                        UserInfo.getUser().getUserId());


            }
        });
    }


    @Override
    protected void onLazyLoad() {
        super.onLazyLoad();
        mPresenter.connectByWebSocket(UserInfo.getUser().getUserId(),
                UserInfo.getMachine().getMachineId());
        mPresenter.getUsers(UserInfo.getUser().getUserId(),
                UserInfo.getMachine().getMachineId());
        mPresenter.getMachine(UserInfo.getMachine().getMachineId());
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onGetUsersSuccess(List<User> users) {
        if (users.size() == 0) {
            emptyView.show();
        } else {
            emptyView.hide();
        }
        this.mList = users;
        mAdapter.setOnDataChange(users);
    }

    @Override
    public void onGetUsersFailed(String reason) {
        ToastUtils.show(reason);
        emptyView.show();
    }

    @Override
    public void onGetMachineSuccess(Machine machine) {
        temperatureTv.setText("加热：" + machine.getTemperature());
        switch (machine.getStatus()) {
            case Machine.STATUS_DISCONNECTED: {
                statusTv.setText("断开连接");
                break;
            }
            case Machine.STATUS_KEEP_WARMING: {
                statusTv.setText("正在保温");
                temperatureTv.setText("保温：" + machine.getInsulation());
                break;
            }
            case Machine.STATUS_MAKING_COFFEE: {
                statusTv.setText("正在煮咖啡");
                temperatureTv.setText("加热：" + machine.getTemperature());
                break;
            }
            case Machine.STATUS_NO_WATER: {
                statusTv.setText("没有水了");
                break;
            }
            case Machine.STATUS_STANDBY: {
                statusTv.setText("准备就绪");
                break;
            }
        }
    }

    @Override
    public void onGetMachineFailed(String reason) {
        ToastUtils.show(reason);
    }

    @Override
    public void onOrderCoffeeSuccess() {
        reserveInfoLl.setBackgroundResource(R.drawable.reserve_making_background);
        temperatureTv.setTextColor(Color.WHITE);
        statusTv.setTextColor(Color.WHITE);
    }

    @Override
    public void onOrderCoffeeFailed(String reason) {
        ToastUtils.show(reason);
    }

    @Override
    public void onCoffeeFinish() {
        reserveInfoLl.setBackgroundResource(R.drawable.reserve_ordered_background);
        temperatureTv.setTextColor(Color.WHITE);
        statusTv.setTextColor(Color.WHITE);
        alertDialog.show();
    }
}
