package com.jianjunhuang.bluemountain.view.activity;

import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.util.Log;

import com.demo.jianjunhuang.mvptools.integration.BaseActivity;
import com.jianjunhuang.bluemountain.R;
import com.jianjunhuang.bluemountain.view.fragment.CoffeeFragment;
import com.jianjunhuang.bluemountain.view.fragment.CommunityFragment;
import com.jianjunhuang.bluemountain.view.fragment.MineFragment;

import java.util.ArrayList;
import java.util.List;

public class HomepageActivity extends BaseActivity {

    private ViewPager viewPager;
    private Toolbar toolbar;
    private TabLayout tabLayout;

    @Override
    protected int getLayoutId() {
        return R.layout.homepage_activity;
    }

    @Override
    protected void initView() {
        tabLayout = findView(R.id.homepage_tablayout);
        viewPager = findView(R.id.homepage_vp);
//        toolbar = findView(R.id.toolbar);
//        setSupportActionBar(toolbar);

        List<Fragment> list = new ArrayList<>();
        list.add(new CoffeeFragment());
        list.add(new CommunityFragment());
        list.add(new MineFragment());
        viewPager.setAdapter(new ViewPagerAdapter(getSupportFragmentManager(), list));
        viewPager.setOffscreenPageLimit(3);
        tabLayout.setupWithViewPager(viewPager);
        tabLayout.getTabAt(0).setIcon(R.drawable.coffee_slected);
        tabLayout.getTabAt(0).setText("coffee");
        tabLayout.getTabAt(1).setIcon(R.drawable.community_selected);
        tabLayout.getTabAt(1).setText("community");
        tabLayout.getTabAt(2).setIcon(R.drawable.mine_selected);
        tabLayout.getTabAt(2).setText("mine");
    }

    @Override
    protected void initListener() {

    }

    static class ViewPagerAdapter extends FragmentPagerAdapter {

        private List<Fragment> list;

        public ViewPagerAdapter(FragmentManager fm, List<Fragment> mList) {
            super(fm);
            this.list = mList;
        }

        @Override
        public Fragment getItem(int position) {
            return list.get(position);
        }

        @Override
        public int getCount() {
            return list.size();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }

    private static final String TAG = "HomepageActivity";
}
