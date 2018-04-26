package com.jianjunhuang.bluemountain.presenter;

import com.jianjunhuang.bluemountain.contact.CommunityAddContact;
import com.jianjunhuang.bluemountain.model.CommunityAddModel;
import com.jianjunhuang.bluemountain.model.bean.Community;

public class CommunityAddPresenter implements CommunityAddContact.Presenter {

    private CommunityAddContact.View mView;
    private CommunityAddContact.Model mModel;

    public CommunityAddPresenter(final CommunityAddContact.View mView) {
        this.mView = mView;
        mModel = new CommunityAddModel();
        mModel.setCallback(new CommunityAddContact.Callback() {
            @Override
            public void onAddSuccess() {
                mView.onAddSuccess();
            }

            @Override
            public void onAddFailed(String reason) {
                mView.onAddFailed(reason);
            }
        });
    }

    @Override
    public void addCommunity(String title, String content, String userId, String machineId) {
        if (null == userId || "".equals(userId)) {
            mView.onAddFailed("userId is null or equals ''");
            return;
        }
        if (null == machineId || "".equals(machineId)) {
            mView.onAddFailed("machineId is null or equals ''");
            return;
        }
        if (null == title || "".equals(title)) {
            mView.onAddFailed("please input title");
            return;
        }
        if (null == content || "".equals(content)) {
            mView.onAddFailed("please input content");
            return;
        }
        mModel.addCommunity(title, content, userId, machineId);
    }

    @Override
    public void onStart() {

    }

    @Override
    public void onDestroy() {

    }
}
