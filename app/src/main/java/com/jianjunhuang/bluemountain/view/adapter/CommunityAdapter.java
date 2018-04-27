package com.jianjunhuang.bluemountain.view.adapter;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.demo.jianjunhuang.mvptools.adapter.RecyclerAdapter;
import com.demo.jianjunhuang.mvptools.adapter.RecyclerViewHolder;
import com.jianjunhuang.bluemountain.R;
import com.jianjunhuang.bluemountain.model.bean.Community;

import java.text.SimpleDateFormat;
import java.util.List;

public class CommunityAdapter extends RecyclerAdapter<Community> {

    private static final String TAG = "CommunityAdapter";

    private SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm");

    public CommunityAdapter(Context context, List<Community> list, int layoutId) {
        super(context, list, layoutId);
    }

    @Override
    public void convert(RecyclerViewHolder viewHolder, final Community community) {
        viewHolder.setText(R.id.community_item_title_tv, community.getTitle());
        viewHolder.setText(R.id.community_item_content_tv, community.getContent());
        viewHolder.setText(R.id.community_item_time_tv, dateFormat.format(community.getDate()));
        Button positiveBtn = viewHolder.getView(R.id.community_item_agree_btn);
        positiveBtn.setText("赞同 " + community.getAgreeNum());

        Button negativeBtn = viewHolder.getView(R.id.community_item_disagree_btn);
        negativeBtn.setText("不赞同 " + community.getDisagreeNum());

        Drawable positiveDrawable = null, negativeDrawable = null;
        if (community.getIsAgree() == 1) {
            positiveDrawable = ContextCompat.getDrawable(context, R.drawable.up_selected);
            negativeDrawable = ContextCompat.getDrawable(context, R.drawable.down);
        } else if (community.getIsAgree() == -1) {
            positiveDrawable = ContextCompat.getDrawable(context, R.drawable.up);
            negativeDrawable = ContextCompat.getDrawable(context, R.drawable.down_selected);
        } else {
            positiveDrawable = ContextCompat.getDrawable(context, R.drawable.up);
            negativeDrawable = ContextCompat.getDrawable(context, R.drawable.down);
        }
        Log.i(TAG, "convert: " + positiveDrawable + " " + negativeDrawable);
        positiveDrawable.setBounds(0, 0, positiveDrawable.getIntrinsicWidth(), positiveDrawable.getIntrinsicHeight());
        negativeDrawable.setBounds(0, 0, negativeDrawable.getIntrinsicWidth(), negativeDrawable.getIntrinsicHeight());
        positiveBtn.setCompoundDrawables(positiveDrawable, null, null, null);
        negativeBtn.setCompoundDrawables(negativeDrawable, null, null, null);

        if (null != listener) {
            positiveBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onChoose(community.getCommunityId(), 1, community.getIsAgree() != 0);
                }
            });

            negativeBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listener.onChoose(community.getCommunityId(), -1, community.getIsAgree() != 0);
                }
            });
        }
    }


    public interface OnDisPositionChooseListener {
        void onChoose(String communityId, int isAgree, boolean choose);
    }

    private OnDisPositionChooseListener listener;

    public void setListener(OnDisPositionChooseListener listener) {
        this.listener = listener;
    }
}
