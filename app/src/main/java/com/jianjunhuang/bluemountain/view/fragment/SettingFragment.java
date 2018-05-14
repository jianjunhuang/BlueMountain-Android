package com.jianjunhuang.bluemountain.view.fragment;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.SeekBar;
import android.widget.TextView;

import com.demo.jianjunhuang.mvptools.integration.BaseFragment;
import com.demo.jianjunhuang.mvptools.utils.ToastUtils;
import com.jianjunhuang.bluemountain.R;
import com.jianjunhuang.bluemountain.application.UserInfo;
import com.jianjunhuang.bluemountain.contact.SettingContact;
import com.jianjunhuang.bluemountain.model.bean.Machine;
import com.jianjunhuang.bluemountain.model.bean.User;
import com.jianjunhuang.bluemountain.presenter.SettingPresenter;
import com.jianjunhuang.bluemountain.view.activity.ConnectActivity;
import com.jianjunhuang.bluemountain.view.activity.SignInUpActivity;
import com.jianjunhuang.bluemountain.view.activity.SmartConfigActivity;
import com.jianjunhuang.bluemountain.view.widget.QRDialog;

/**
 * Created by jianjunhuang on 18-3-24.
 */

public class SettingFragment extends BaseFragment implements View.OnClickListener,
        SeekBar.OnSeekBarChangeListener, SettingContact.View {

    private EditText nameEdt;
    private TextView capacityTv;
    private SeekBar capacitySb;
    private SeekBar temperatureSb;
    private TextView temperatureTv;

    private Button inviteBtn;
    private Button disconnectBtn;
    private ImageButton nameBtn;
    private Button smartConfigBtn;

    private QRDialog qrDialog;

    private SettingPresenter mPresenter;

    @Override
    protected int getLayoutId() {
        return R.layout.setting_fragment;
    }

    @Override
    protected void initView(View view) {
        mPresenter = new SettingPresenter(this);
        nameEdt = findView(R.id.setting_name_edt);
        capacityTv = findView(R.id.setting_cup_capacity_tv);
        capacitySb = findView(R.id.setting_cup_capacity_seek);
        temperatureSb = findView(R.id.setting_temperature_seek);
        temperatureTv = findView(R.id.setting_temperature_tv);
        inviteBtn = findView(R.id.setting_invite_btn);
        disconnectBtn = findView(R.id.setting_disconnect_btn);
        nameBtn = findView(R.id.setting_name_btn);
        smartConfigBtn = findView(R.id.setting_smart_config_btn);
        qrDialog = new QRDialog(getContext());

    }

    @Override
    protected void initListener() {
        inviteBtn.setOnClickListener(this);
        disconnectBtn.setOnClickListener(this);
        nameBtn.setOnClickListener(this);
        nameEdt.setOnClickListener(this);
        temperatureSb.setOnSeekBarChangeListener(this);
        capacitySb.setOnSeekBarChangeListener(this);
        smartConfigBtn.setOnClickListener(this);
    }

    @Override
    protected void onLazyLoad() {
        super.onLazyLoad();
        update();
    }

    private void update() {
        mPresenter.getMachineInfo();
        mPresenter.getUserInfo();
        if (UserInfo.getUser() == null) {
            disconnectBtn.setText("Sign In");
        } else {
            if (UserInfo.getMachine() == null) {
                disconnectBtn.setText("Connect");
            } else {
                disconnectBtn.setText("Disconnect");
            }
        }
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.setting_invite_btn: {
                if (UserInfo.getUser() != null) {
                    if (UserInfo.getMachine() == null) {
                        ToastUtils.show("please connect coffee machine first!");
                        return;
                    }
                    mPresenter.invite(
                            UserInfo.getMachine().getMachineId(),
                            BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher));
                } else {
                    ToastUtils.show("please sign in first");
                }
                break;
            }
            case R.id.setting_disconnect_btn: {
                if (UserInfo.getUser() == null) {

                    Intent intent = new Intent(getActivity(), SignInUpActivity.class);
                    startActivity(intent);
                } else {
                    if (UserInfo.getMachine() == null) {

                        Intent intent = new Intent(getActivity(), ConnectActivity.class);
                        startActivity(intent);
                    } else {
                        mPresenter.disconnect(UserInfo.getUser().getUserId(),
                                UserInfo.getMachine().getMachineId());
                    }
                }
                break;
            }
            case R.id.setting_name_btn: {
                if (UserInfo.getUser() != null) {

                    nameEdt.setFocusable(false);
                    nameBtn.setVisibility(View.GONE);
                    mPresenter.updateUserName(UserInfo.getUser().getUserId(),
                            nameEdt.getText().toString());
                } else {
                    ToastUtils.show("please sign in first!");
                }
                break;
            }
            case R.id.setting_name_edt: {
                nameEdt.setFocusableInTouchMode(true);
                nameBtn.setVisibility(View.VISIBLE);
                break;
            }
            case R.id.setting_smart_config_btn: {
                Intent intent = new Intent(getActivity(), SmartConfigActivity.class);
                startActivity(intent);
                break;
            }
        }
    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
        switch (seekBar.getId()) {
            case R.id.setting_cup_capacity_seek: {
                capacityTv.setText(progress + "ml");
                break;
            }
            case R.id.setting_temperature_seek: {
                temperatureTv.setText(progress + " °C");
                break;
            }
        }
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {
        switch (seekBar.getId()) {
            case R.id.setting_cup_capacity_seek: {
                if (UserInfo.getUser() != null) {

                    mPresenter.updateUserCupCapacity(UserInfo.getUser().getUserId(),
                            seekBar.getProgress());
                } else {
                    ToastUtils.show("please sign in first!");
                }
                break;
            }
            case R.id.setting_temperature_seek: {
                if (UserInfo.getMachine() != null) {

                    mPresenter.updateMachineHoldingTemperature(
                            UserInfo.getMachine().getMachineId(),
                            seekBar.getProgress()
                    );
                } else {
                    ToastUtils.show("please connect to machine first！");
                }
                break;
            }
        }
    }

    @Override
    public void onGetUserInfoSuccess(User user) {
        if (null == user) {
            return;
        }
        nameEdt.setText(user.getName());
        capacitySb.setProgress((int) user.getCupSize());
        capacityTv.setText(user.getCupSize() + "ml");
    }

    @Override
    public void onGetUserInfoFailed(String reason) {
        ToastUtils.show(reason);
    }

    @Override
    public void onGetMachineInfoSuccess(Machine machine) {
        if (null == machine) {
            return;
        }
        temperatureSb.setProgress((int) machine.getInsulation());
        temperatureTv.setText(machine.getInsulation() + "°C");
    }

    @Override
    public void onGetMachineInfoFailed(String reason) {
        ToastUtils.show(reason);
    }

    @Override
    public void onUpdateUserCupCapacitySuccess() {
        ToastUtils.show("更新成功");
    }

    @Override
    public void onUpdateUserCupCapacityFailed(String reason) {
        ToastUtils.show(reason);
    }

    @Override
    public void onUpdateUserNameSuccess() {
        ToastUtils.show("更新成功");
    }

    @Override
    public void onUpdateUserNameFailed(String reason) {
        ToastUtils.show(reason);
    }

    @Override
    public void onUpdateMachineTemperatureSuccess() {
        ToastUtils.show("更新成功");
    }

    @Override
    public void onUpdateMachineTemperatureFailed(String reason) {
        ToastUtils.show(reason);
    }

    @Override
    public void onCreateQRSuccess(Bitmap bitmap, String machineId) {
        qrDialog.setQRImage(bitmap);
        qrDialog.setMachineId(machineId);
        qrDialog.show();
    }

    @Override
    public void onCreateQRFailed(String reason) {
        ToastUtils.show(reason);
    }

    @Override
    public void onDisconnectedSuccess() {
        ToastUtils.show("断开连接成功");
        update();
    }

    @Override
    public void onDisconnectedFailed(String reason) {
        ToastUtils.show(reason);
    }
}
