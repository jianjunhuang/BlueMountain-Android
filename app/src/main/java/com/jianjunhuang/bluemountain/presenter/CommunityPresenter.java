package com.jianjunhuang.bluemountain.presenter;

import com.jianjunhuang.bluemountain.contact.CommunityContact;
import com.jianjunhuang.bluemountain.model.CommunityModel;

import java.util.List;

public class CommunityPresenter implements CommunityContact.Presenter {

    private CommunityContact.View mView;
    private CommunityContact.Model mModel;

    public CommunityPresenter(final CommunityContact.View mView) {
        this.mView = mView;
        mModel = new CommunityModel();
        mModel.setCallback(new CommunityContact.Callback() {
            @Override
            public void onGetSuccess(List list) {
                mView.onGetSuccess(list);
            }

            @Override
            public void onGetFailed(String reason) {
                mView.onGetFailed(reason);
            }

            @Override
            public void onSendSuccess() {
                mView.onSendSuccess();
            }

            @Override
            public void onSendFailed(String reason) {
                mView.onSendFailed(reason);
            }
        });
    }

    @Override
    public void getCommunity(String userId, String machineId) {
        if (null == userId || "".equals(userId)) {
            mView.onGetFailed("userId is null or equals ''");
            return;
        }
        if (null == machineId || "".equals(machineId)) {
            mView.onGetFailed("machineId is null or equals ''");
            return;
        }
        mModel.getCommunity(userId, machineId);
    }

    @Override
    public void sendPosition(String userId, String communityId, int isAgree) {
        if (null == userId || "".equals(userId)) {
            mView.onSendFailed("userId is null or equals ''");
            return;
        }
        if (null == communityId || "".equals(communityId)) {
            mView.onSendFailed("communityId is null or equals ''");
            return;
        }
        if(isAgree == 0){
            mView.onSendFailed("please make a choice");
            return;
        }
        mModel.sendPosition(userId, communityId, isAgree);
    }

    @Override
    public void onStart() {

    }

    @Override
    public void onDestroy() {

    }
}
