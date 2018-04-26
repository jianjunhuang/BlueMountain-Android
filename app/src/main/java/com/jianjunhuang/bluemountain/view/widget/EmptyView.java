package com.jianjunhuang.bluemountain.view.widget;

import android.view.View;
import android.widget.TextView;

import com.jianjunhuang.bluemountain.R;

public class EmptyView {
    private View emptyView;

    private TextView textView;

    public static final String DEFALT_TIPS = "当前没有数据 ┑(￣Д ￣)┍";

    public EmptyView(View emptyView) {
        this.emptyView = emptyView;
        textView = emptyView.findViewById(R.id.empty_tips_tv);
    }

    public void setTips(String tips) {
        textView.setText(tips);
    }

    public void show() {
        emptyView.setVisibility(View.VISIBLE);
    }

    public void hide() {
        emptyView.setVisibility(View.GONE);
    }
}
