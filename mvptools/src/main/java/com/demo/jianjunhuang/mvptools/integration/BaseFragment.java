package com.demo.jianjunhuang.mvptools.integration;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewStub;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.demo.jianjunhuang.mvptools.utils.ToastUtils;
import com.demo.jianjunhuang.mvptoos.R;

import java.lang.ref.WeakReference;

/**
 * @author jianjunhuang.me@foxmail.com
 * @since 2017/5/24.
 */

public abstract class BaseFragment extends Fragment {
    private View view;
    private View stubView;
    private boolean isLoad = false;
    private boolean isInit = false;
    private long waitTime = 500L;
    private Handler mHandler = new InnerHandler(this);
    private int WAIT = 0;
    private ViewStub viewStub;
    private ProgressBar basePb;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        isInit = true;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.base_layout, container, false);
        viewStub = (ViewStub) view.findViewById(R.id.fragment_vs);
        viewStub.setLayoutResource(getLayoutId());
        basePb = (ProgressBar) view.findViewById(R.id.base_pb);
        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    protected abstract int getLayoutId();

    protected abstract void initView(View view);

    protected abstract void initListener();

    protected <T extends View> T findView(int id) {
        View v = stubView.findViewById(id);
        return (T) v;
    }

    /**
     * 视图是否已经对用户可见，系统的方法
     */
    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (!isVisibleToUser && mHandler != null) {
            mHandler.removeMessages(WAIT);
        } else {
            mHandler.sendEmptyMessageDelayed(WAIT,waitTime);
        }
    }

    /**
     * 是否可以加载数据
     * 可以加载数据的条件：
     * 1.视图已经初始化
     * 2.视图对用户可见
     */
    private void isCanLoadData() {
        if (!isInit) {
            return;
        }

        if (getUserVisibleHint()) {
            onLazyLoad();
            isLoad = true;
        } else {
            if (isLoad) {
                onStopLoad();
            }
        }
    }

    protected void onStopLoad() {

    }

    protected void onLazyLoad() {
        basePb.setVisibility(View.GONE);
        if (stubView == null) {
            stubView = viewStub.inflate();
            initView(stubView);
            initListener();
        }
    }

    public static void showShort(String msg) {
        ToastUtils.show(msg);
    }

    public static void showLong(String msg) {
        ToastUtils.show(msg, Toast.LENGTH_LONG);
    }

    public static void showShort(int msg) {
        ToastUtils.show(msg);
    }

    public static void showLong(int msg) {
        ToastUtils.show(msg, Toast.LENGTH_LONG);
    }

    /**
     * 字符串是否为空
     *
     * @param str string
     * @return true - str is empty or null
     */
    public boolean isEmptyOrNull(String str) {
        return str == null || str.equals("");
    }

    /**
     * 跳转 带参数
     *
     * @param clz    activity class
     * @param bundle data
     */
    public void startActivity(Class<?> clz, Bundle bundle) {
        Intent intent = new Intent();
        intent.setClass(getActivity(), clz);
        if (bundle != null) {
            intent.putExtras(bundle);
        }
        startActivity(intent);
    }

    public void stopTask() {

    }

    /**
     * 跳转 不带参数
     *
     * @param clz activity class
     */
    public void startActivity(Class<?> clz) {
        startActivity(clz, null);
    }

    private static class InnerHandler extends Handler {

        private WeakReference<BaseFragment> fragmentWeakReference;

        public InnerHandler(BaseFragment baseFragment) {
            fragmentWeakReference = new WeakReference<BaseFragment>(baseFragment);
        }

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            fragmentWeakReference.get().isCanLoadData();
        }
    }

}
