package com.jianjunhuang.bluemountain.view.adapter;

import android.content.Context;
import android.support.v4.content.ContextCompat;

import com.demo.jianjunhuang.mvptools.adapter.RecyclerAdapter;
import com.demo.jianjunhuang.mvptools.adapter.RecyclerViewHolder;
import com.jianjunhuang.bluemountain.R;
import com.jianjunhuang.bluemountain.model.bean.User;
import com.jianjunhuang.bluemountain.view.widget.AvatarView;

import java.util.List;

public class CoffeeAdapter extends RecyclerAdapter<User> {

    public CoffeeAdapter(Context context, List<User> list, int layoutId) {
        super(context, list, layoutId);
    }

    @Override
    public void convert(RecyclerViewHolder viewHolder, User user) {

        viewHolder.setText(R.id.coffee_item_name_tv, user.getName());

        AvatarView avatarView = viewHolder.getView(R.id.coffee_item_avatar);
        if (user.getStatus() == User.WAITING) {
            avatarView.setColor(ContextCompat.getColor(context, R.color.colorOrange));
            viewHolder.setText(R.id.coffee_item_status_tv, "正在等待咖啡");
        } else if (user.getStatus() == User.OUTLINE) {
            avatarView.setColor(ContextCompat.getColor(context, R.color.colorHint));
            viewHolder.setText(R.id.coffee_item_status_tv, "离线中");
        } else if (user.getStatus() == User.GETINT) {
            avatarView.setColor(ContextCompat.getColor(context, R.color.colorGreen));
            viewHolder.setText(R.id.coffee_item_status_tv, "倒咖啡中");
        } else if (user.getStatus() == User.ONLINE) {
            avatarView.setColor(ContextCompat.getColor(context, R.color.colorBlue));
            viewHolder.setText(R.id.coffee_item_status_tv, "在线上");
        }

    }
}
