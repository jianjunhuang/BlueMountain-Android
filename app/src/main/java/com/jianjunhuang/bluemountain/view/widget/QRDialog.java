package com.jianjunhuang.bluemountain.view.widget;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;

import com.jianjunhuang.bluemountain.R;

public class QRDialog extends Dialog {

    private TextView machineIdTv;
    private ImageView qrImg;
    private View view;

    public QRDialog(@NonNull Context context) {
        super(context);
        view = LayoutInflater.from(getContext()).inflate(R.layout.qr_dialog_layout, null);
        machineIdTv = view.findViewById(R.id.qr_machine_id_tv);
        qrImg = view.findViewById(R.id.qr_img);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);

        setContentView(view);

    }

    public void setMachineId(String machineId) {
        machineIdTv.setText(machineId);
    }

    public void setQRImage(Bitmap bitmap) {
        qrImg.setImageBitmap(bitmap);
    }
}
