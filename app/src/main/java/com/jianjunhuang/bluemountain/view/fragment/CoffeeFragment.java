package com.jianjunhuang.bluemountain.view.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;

import com.demo.jianjunhuang.mvptools.adapter.CommonAdapter;
import com.demo.jianjunhuang.mvptools.adapter.CommonViewHolder;
import com.demo.jianjunhuang.mvptools.integration.BaseFragment;
import com.demo.jianjunhuang.mvptools.utils.ToastUtils;
import com.jianjunhuang.bluemountain.R;
import com.jianjunhuang.bluemountain.model.bean.CoffeUserBean;
import com.jianjunhuang.bluemountain.utils.CheckPermissionUtils;
import com.uuzuche.lib_zxing.activity.CaptureActivity;
import com.uuzuche.lib_zxing.activity.CodeUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by jianjunhuang on 18-3-24.
 */

public class CoffeeFragment extends BaseFragment {

    private ListView userLv;
    private List<CoffeUserBean> mList = new ArrayList<>();
    private ImageView coffeeInfoView;


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
        coffeeInfoView = findView(R.id.coffee_info_view);

    }

    @Override
    protected void initListener() {
        coffeeInfoView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String[] permissions = CheckPermissionUtils.checkPermission(getContext());
                if (permissions.length == 0) {
                    Intent intent = new Intent(getActivity(), CaptureActivity.class);
                    startActivityForResult(intent, 200);
                } else {
                    ActivityCompat.requestPermissions(getActivity(), permissions, 100);
                }
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
                } else if (bundle.getInt(CodeUtils.RESULT_TYPE) == CodeUtils.RESULT_FAILED) {
                    ToastUtils.show("解析失败");
                }
            }
        }
    }


    private static final String TAG = "CoffeeFragment";

}
