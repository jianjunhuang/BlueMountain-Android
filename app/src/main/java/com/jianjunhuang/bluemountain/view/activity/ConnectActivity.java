package com.jianjunhuang.bluemountain.view.activity;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputEditText;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import com.demo.jianjunhuang.mvptools.integration.BaseActivity;
import com.demo.jianjunhuang.mvptools.utils.ToastUtils;
import com.jianjunhuang.bluemountain.R;
import com.jianjunhuang.bluemountain.application.UserInfo;
import com.jianjunhuang.bluemountain.contact.ConnectContact;
import com.jianjunhuang.bluemountain.presenter.ConnectPresenter;
import com.uuzuche.lib_zxing.activity.CaptureActivity;
import com.uuzuche.lib_zxing.activity.CodeUtils;

import java.io.IOException;

public class ConnectActivity extends BaseActivity
        implements View.OnClickListener, TextWatcher, ConnectContact.View {

    private TextInputEditText machineIdEdt;
    private ImageButton sendMachineIdBtn;
    private Button scanQRBtn;
    private Button scanQRImgBtn;
    private Button configMachineBtn;

    private ConnectContact.Presenter mPresenter;

    private static final int SCAN_QR_CODE = 100;
    private static final int GET_CAMERA_PERMISSION_RESULT = 101;
    private static final int GET_FILE_PERMISSION_RESULT = 103;
    private static final int GET_IMAGE_RESULT = 102;

    private AlertDialog mTipsDialog;

    @Override
    protected int getLayoutId() {
        return R.layout.connect_activiyt;
    }

    @Override
    protected void initView() {
        mPresenter = new ConnectPresenter(this);
        machineIdEdt = findView(R.id.connect_machine_id_edt);
        sendMachineIdBtn = findView(R.id.connect_send_machine_id_btn);
        scanQRBtn = findView(R.id.connect_scan_qr_btn);
        scanQRImgBtn = findView(R.id.connect_scan_qr_img_btn);
        configMachineBtn = findView(R.id.connect_config_machine_btn);
        mTipsDialog = new AlertDialog.Builder(this, R.style.TipsDialogTheme)
                .setTitle("Tips")
                .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                }).setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                }).create();
    }

    @Override
    protected void initListener() {
        sendMachineIdBtn.setOnClickListener(this);
        scanQRBtn.setOnClickListener(this);
        scanQRImgBtn.setOnClickListener(this);
        machineIdEdt.addTextChangedListener(this);
        configMachineBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.connect_send_machine_id_btn: {
                mPresenter.connect(UserInfo.getUser().getUserId(),
                        machineIdEdt.getText().toString());
                break;
            }
            case R.id.connect_scan_qr_btn: {
                scanQR();
                break;
            }
            case R.id.connect_scan_qr_img_btn: {
                scanQRFromFile();
                break;
            }
            case R.id.connect_config_machine_btn: {
                Intent intent = new Intent(
                        ConnectActivity.this,
                        SmartConfigActivity.class);
                startActivity(intent);
                break;
            }
        }
    }


    private void scanQR() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) !=
                PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.CAMERA},
                    GET_CAMERA_PERMISSION_RESULT);
        } else {
            Intent intent = new Intent(ConnectActivity.this, CaptureActivity.class);
            startActivityForResult(intent, SCAN_QR_CODE);
        }
    }

    private void scanQRFromFile() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) !=
                PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.READ_EXTERNAL_STORAGE,
                            Manifest.permission.WRITE_EXTERNAL_STORAGE}, GET_FILE_PERMISSION_RESULT);
        } else {
            Intent intent = new Intent();
            intent.setAction(Intent.ACTION_PICK);
            intent.setType("image/*");
            startActivityForResult(intent, GET_IMAGE_RESULT);
        }
    }

    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {
        if (s.length() > 0) {
            sendMachineIdBtn.setVisibility(View.VISIBLE);
        } else {
            sendMachineIdBtn.setVisibility(View.GONE);
        }
    }

    @Override
    public void afterTextChanged(Editable s) {

    }

    @Override
    public void onConnectedSuccess() {
        ToastUtils.show("connected success!");
        finish();
    }

    @Override
    public void onConnectedFailed(String reason) {
        showTips(reason);
    }

    private static final String TAG = "ConnectActivity";

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        switch (requestCode) {
            case GET_CAMERA_PERMISSION_RESULT: {
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    scanQR();
                }
                break;
            }
            case GET_FILE_PERMISSION_RESULT: {
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    scanQRFromFile();
                }
                break;
            }
        }
    }

    private void showTips(String tips) {
        mTipsDialog.setMessage(tips);
        if (!mTipsDialog.isShowing()) {
            mTipsDialog.show();
        }
    }

    @Override
    protected void onActivityResult(final int requestCode, int resultCode, Intent data) {
        switch (requestCode) {
            case SCAN_QR_CODE: {
                if (null == data) {
                    return;
                }
                Bundle bundle = data.getExtras();
                if (null == bundle) {
                    return;
                }
                if (bundle.getInt(CodeUtils.RESULT_TYPE) == CodeUtils.RESULT_SUCCESS) {
                    String result = bundle.getString(CodeUtils.RESULT_STRING);
                    mPresenter.connect(UserInfo.getUser().getUserId(), result);
                } else {
                    showTips("二维码解析失败");
                }
                break;
            }
            case GET_IMAGE_RESULT: {
                if (data != null) {
                    Uri uri = data.getData();
                    try {
                        Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    CodeUtils.analyzeBitmap(getRealPathFromURI(uri), new CodeUtils.AnalyzeCallback() {
                        @Override
                        public void onAnalyzeSuccess(Bitmap mBitmap, String result) {
                            mPresenter.connect(UserInfo.getUser().getUserId(), result);
                        }

                        @Override
                        public void onAnalyzeFailed() {
                            showTips("二维码解析失败");
                        }
                    });
                }
                break;
            }
        }
    }

    private String getRealPathFromURI(Uri contentURI) {
        String result;
        Cursor cursor = getContentResolver().query(contentURI, null, null, null, null);
        if (cursor == null) { // Source is Dropbox or other similar local file path
            result = contentURI.getPath();
        } else {
            cursor.moveToFirst();
            int idx = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
            result = cursor.getString(idx);
            cursor.close();
        }
        return result;
    }
}
