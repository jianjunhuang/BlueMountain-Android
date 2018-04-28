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
        AvatarView avatarView = viewHolder.getView(R.id.coffee_item_avatar);
        avatarView.setColor(ContextCompat.getColor(context, R.color.colorBlue));

        viewHolder.setText(R.id.coffee_item_name_tv, user.getName());

    }
}
