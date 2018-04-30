package com.jianjunhuang.bluemountain.view.fragment;

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
    }

    @Override
    protected void onLazyLoad() {
        super.onLazyLoad();
        mPresenter.getMachineInfo();
        mPresenter.getUserInfo();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.setting_invite_btn: {
                mPresenter.invite(
                        UserInfo.getMachine().getMachineId(),
                        BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher));
                break;
            }
            case R.id.setting_disconnect_btn: {
                mPresenter.disconnect(UserInfo.getUser().getUserId(),
                        UserInfo.getMachine().getMachineId());
                break;
            }
            case R.id.setting_name_btn: {
                nameEdt.setFocusable(false);
                nameBtn.setVisibility(View.GONE);
                mPresenter.updateUserName(UserInfo.getUser().getUserId(),
                        nameEdt.getText().toString());
                break;
            }
            case R.id.setting_name_edt: {
                nameEdt.setFocusableInTouchMode(true);
                nameBtn.setVisibility(View.VISIBLE);
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
                mPresenter.updateUserCupCapacity(UserInfo.getUser().getUserId(),
                        seekBar.getProgress());
                break;
            }
            case R.id.setting_temperature_seek: {
                mPresenter.updateMachineHoldingTemperature(
                        UserInfo.getMachine().getMachineId(),
                        seekBar.getProgress()
                );
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
    }

    @Override
    public void onDisconnectedFailed(String reason) {
        ToastUtils.show(reason);
    }
}
