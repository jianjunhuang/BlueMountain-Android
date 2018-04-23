package com.jianjunhuang.bluemountain.view.fragment;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;

import com.demo.jianjunhuang.mvptools.adapter.CommonAdapter;
import com.demo.jianjunhuang.mvptools.adapter.CommonViewHolder;
import com.demo.jianjunhuang.mvptools.integration.BaseFragment;
import com.demo.jianjunhuang.mvptools.utils.ToastUtils;
import com.jianjunhuang.bluemountain.R;
import com.jianjunhuang.bluemountain.model.bean.CoffeUserBean;
import com.jianjunhuang.bluemountain.utils.WaveHelper;
import com.jianjunhuang.bluemountain.view.activity.ConnectActivity;
import com.jianjunhuang.lib.waveview.Wave;
import com.jianjunhuang.lib.waveview.WaveView;
import com.uuzuche.lib_zxing.activity.CodeUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jianjunhuang on 18-3-24.
 */

public class CoffeeFragment extends BaseFragment {

    private ListView userLv;
    private List<CoffeUserBean> mList = new ArrayList<>();
    private WaveView coffeeInfoView;
    private WaveHelper waveHelper;

    @Override
    protected int getLayoutId() {
        return R.layout.coffee_fragment;
    }

    @Override
    protected void initView(View view) {
        userLv = findView(R.id.coffee_info_lv);
        for (int i = 0; i < 20; i++) {
            mList.add(null);
        }
        userLv.setAdapter(new CommonAdapter<CoffeUserBean>(getContext(), mList, R.layout.coffee_item_layout) {
            @Override
            public void convert(CommonViewHolder viewHolder, CoffeUserBean bean, int position) {

            }
        });

        initInfoView(view);

    }

    private void initInfoView(View view) {
        coffeeInfoView = findView(R.id.coffee_info_view);
        coffeeInfoView.setBackgroundColor(Color.parseColor("#8C6E63"));
        coffeeInfoView.setBorderColor(Color.parseColor("#412D26"));
        coffeeInfoView.setBorderWidth(5);
        Wave wave1 = new Wave();
        Wave wave2 = new Wave();
        Wave wave3 = new Wave();
        wave1.setColor(Color.parseColor("#412D26"));
        wave2.setColor(Color.parseColor("#4E342E"));
        wave3.setColor(Color.parseColor("#412D26"));

        wave2.setSpaceRatio(150);
        wave3.setSpaceRatio(300);

        wave1.setAlphaRatio(0.1f);
        wave2.setAlphaRatio(0.5f);
        wave3.setAlphaRatio(0.2f);

        coffeeInfoView.addWave(wave1);
        coffeeInfoView.addWave(wave2);
        coffeeInfoView.addWave(wave3);

        waveHelper = new WaveHelper(coffeeInfoView);
    }

    @Override
    protected void initListener() {
        coffeeInfoView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                String[] permissions = CheckPermissionUtils.checkPermission(getContext());
//                if (permissions.length == 0) {
//                    Intent intent = new Intent(getActivity(), CaptureActivity.class);
//                    startActivityForResult(intent, 200);
//                } else {
//                    ActivityCompat.requestPermissions(getActivity(), permissions, 100);
//                }
                Intent intent = new Intent(getActivity(), ConnectActivity.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 200) {
            if (null != data) {
                Bundle bundle = data.getExtras();
                if (null == bundle) {
                    return;
                }
                if (bundle.getInt(CodeUtils.RESULT_TYPE) == CodeUtils.RESULT_SUCCESS) {
                    String result = bundle.getString(CodeUtils.RESULT_STRING);
                    ToastUtils.show(result);
                    //TODO check the result is the machineId
                    Intent intent = new Intent(getActivity(), ConnectActivity.class);
                    startActivity(intent);
                } else if (bundle.getInt(CodeUtils.RESULT_TYPE) == CodeUtils.RESULT_FAILED) {
                    ToastUtils.show("解析失败");
                }
            }
        }
    }


    private static final String TAG = "CoffeeFragment";

    @Override
    protected void onLazyLoad() {
        super.onLazyLoad();
        waveHelper.start();
    }

    @Override
    public void onPause() {
        super.onPause();
        waveHelper.cancel();
    }
}
