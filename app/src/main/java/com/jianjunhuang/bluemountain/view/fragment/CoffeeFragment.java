package com.jianjunhuang.bluemountain.view.fragment;

import android.view.View;
import android.widget.ListView;

import com.demo.jianjunhuang.mvptools.adapter.CommonAdapter;
import com.demo.jianjunhuang.mvptools.adapter.CommonViewHolder;
import com.demo.jianjunhuang.mvptools.integration.BaseFragment;
import com.jianjunhuang.bluemountain.R;
import com.jianjunhuang.bluemountain.model.bean.CoffeUserBean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jianjunhuang on 18-3-24.
 */

public class CoffeeFragment extends BaseFragment {

    private ListView userLv;
    private List<CoffeUserBean> mList = new ArrayList<>();

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
    }

    @Override
    protected void initListener() {

    }
}
