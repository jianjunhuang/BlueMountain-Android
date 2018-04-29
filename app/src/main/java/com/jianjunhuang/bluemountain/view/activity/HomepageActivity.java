package com.jianjunhuang.bluemountain.view.activity;

import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;

import com.demo.jianjunhuang.mvptools.integration.BaseActivity;
import com.jianjunhuang.bluemountain.R;
import com.jianjunhuang.bluemountain.utils.WaveHelper;
import com.jianjunhuang.bluemountain.view.fragment.CoffeeFragment;
import com.jianjunhuang.bluemountain.view.fragment.CommunityFragment;
import com.jianjunhuang.bluemountain.view.fragment.MineFragment;
import com.jianjunhuang.lib.waveview.Wave;
import com.jianjunhuang.lib.waveview.WaveView;

import java.util.ArrayList;
import java.util.List;

public class HomepageActivity extends BaseActivity {

    private ViewPager viewPager;
    private Toolbar toolbar;
    private TabLayout tabLayout;
    private WaveView coffeeInfoView;
    private WaveHelper waveHelper;

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
        tabLayout.getTabAt(2).setIcon(R.drawable.ic_settings_white_24dp);
        tabLayout.getTabAt(2).setText("setting");
        initInfoView();
    }

    private void initInfoView() {
        coffeeInfoView = findView(R.id.coffee_info_view);
        coffeeInfoView.setBackgroundColor(ContextCompat.getColor(this, R.color.colorPrimaryLight));
        coffeeInfoView.setShape(WaveView.SHAPE_SQUARE);
        Wave wave1 = new Wave();
        Wave wave2 = new Wave();
        Wave wave3 = new Wave();
        wave1.setColor(ContextCompat.getColor(this, R.color.colorPrimary));
        wave2.setColor(ContextCompat.getColor(this, R.color.colorPrimary));
        wave3.setColor(ContextCompat.getColor(this, R.color.colorPrimary));

        wave2.setSpaceRatio(100);
        wave3.setSpaceRatio(200);

        wave1.setAlphaRatio(0.3f);
        wave2.setAlphaRatio(0.5f);
        wave3.setAlphaRatio(0.1f);

        coffeeInfoView.addWave(wave1);
        coffeeInfoView.addWave(wave2);
        coffeeInfoView.addWave(wave3);

        waveHelper = new WaveHelper(coffeeInfoView);
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

    @Override
    protected void onResume() {
        super.onResume();
        waveHelper.start();
    }

    @Override
    protected void onPause() {
        super.onPause();
        waveHelper.cancel();
    }
}
