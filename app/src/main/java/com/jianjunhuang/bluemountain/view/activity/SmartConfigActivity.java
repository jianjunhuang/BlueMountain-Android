package com.jianjunhuang.bluemountain.view.activity;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.NetworkInfo;
import android.net.wifi.WifiManager;
import android.os.AsyncTask;
import android.support.design.widget.TextInputEditText;
import android.view.View;
import android.widget.TextView;

import com.demo.jianjunhuang.mvptools.integration.BaseActivity;
import com.espressif.iot.esptouch.EsptouchTask;
import com.espressif.iot.esptouch.IEsptouchListener;
import com.espressif.iot.esptouch.IEsptouchResult;
import com.espressif.iot.esptouch.IEsptouchTask;
import com.espressif.iot.esptouch.util.EspAES;
import com.jianjunhuang.bluemountain.R;
import com.jianjunhuang.bluemountain.utils.EspWifiAdminSimple;
import com.jinjunhuang.loadingcirclebtn.LoadingCircleBtn;

import java.util.List;

public class SmartConfigActivity extends BaseActivity {

    private TextInputEditText wifiSsidEdt;
    private TextInputEditText wifiPwdEdt;
    private LoadingCircleBtn loadingCircleBtn;
    private TextView tipsTv;
    private EspWifiAdminSimple mWifiAdmin;
    private EspTouchAsyncTask espTouchAsyncTask;
    private IEsptouchListener mListener = new IEsptouchListener() {
        @Override
        public void onEsptouchResultAdded(IEsptouchResult result) {
            onEsptoucResultAddedPerform(result);
        }
    };

    @Override
    protected int getLayoutId() {
        return R.layout.smart_config_activity;
    }

    @Override
    protected void initView() {
        wifiSsidEdt = findView(R.id.smart_config_wifi_ssid_edt);
        wifiPwdEdt = findView(R.id.smart_config_wifi_pwd_edt);
        loadingCircleBtn = findView(R.id.smart_config_loading_circle_btn);
        tipsTv = findView(R.id.smart_config_tips_tv);
        mWifiAdmin = new EspWifiAdminSimple(this);
        IntentFilter filter = new IntentFilter(WifiManager.NETWORK_STATE_CHANGED_ACTION);
        registerReceiver(mReceiver, filter);
    }

    @Override
    protected void initListener() {
        loadingCircleBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (loadingCircleBtn.getStatus()) {
                    case LoadingCircleBtn.STATUS_LOAD_FAILED:
                    case LoadingCircleBtn.STATUS_DEFAULT:

                        String apSsid = wifiSsidEdt.getText().toString();
                        String apPwd = wifiPwdEdt.getText().toString();
                        String apBssid = mWifiAdmin.getWifiConnectedBssid();

                        if (espTouchAsyncTask != null) {
                            espTouchAsyncTask.cancelEsptouch();
                        }
                        espTouchAsyncTask = new EspTouchAsyncTask();
                        espTouchAsyncTask.execute(apSsid, apBssid, apPwd, "1");

                        break;
                    case LoadingCircleBtn.STATUS_LOADING: {


                        break;
                    }
                    case LoadingCircleBtn.STATUS_LOAD_SUCCESS: {
                        finish();
                        break;
                    }
                }
            }
        });
    }

    private void enableEdt(boolean isEnable) {
        wifiPwdEdt.setEnabled(isEnable);
    }


    @Override
    protected void onResume() {
        super.onResume();
        String apSSid = mWifiAdmin.getWifiConnectedSsid();
        if (null == apSSid || "".equals(apSSid)) {
            wifiSsidEdt.setText("please connected wifi first");
        } else {
            wifiSsidEdt.setText(apSSid);
        }
    }

    private void onEsptoucResultAddedPerform(final IEsptouchResult result) {
        runOnUiThread(new Runnable() {

            @Override
            public void run() {
                String text = result.getBssid() + " is connected to the wifi";
                tipsTv.setText(text);
                enableEdt(true);
            }

        });
    }

    private class EspTouchAsyncTask extends AsyncTask<String, Void, List<IEsptouchResult>> {

        private final Object mLock = new Object();
        private IEsptouchTask esptouchTask;

        public void cancelEsptouch() {
            cancel(true);
            if (esptouchTask != null) {
                esptouchTask.interrupt();
            }
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            loadingCircleBtn.setStatus(LoadingCircleBtn.STATUS_LOADING);
            tipsTv.setText("is connecting ,please wait for a moment...");
            enableEdt(false);
        }

        @Override
        protected List<IEsptouchResult> doInBackground(String... params) {
            synchronized (mLock) {
                String apSsid = mWifiAdmin.getWifiConnectedSsidAscii(params[0]);
                String apBssid = params[1];
                String apPsw = params[2];
                boolean useAes = false;
                if (useAes) {
                    byte[] secretKey = "1234567890123456".getBytes();
                    EspAES aes = new EspAES(secretKey);
                    esptouchTask = new EsptouchTask(apSsid, apBssid, apPsw, aes, SmartConfigActivity.this);
                } else {
                    esptouchTask = new EsptouchTask(apSsid, apBssid, apPsw, null, SmartConfigActivity.this);
                }
                esptouchTask.setEsptouchListener(mListener);
            }
            List<IEsptouchResult> results = esptouchTask.executeForResults(1);
            return results;
        }

        @Override
        protected void onPostExecute(List<IEsptouchResult> results) {
            if (results == null) {
                loadingCircleBtn.setStatus(LoadingCircleBtn.STATUS_LOAD_FAILED);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        tipsTv.setText("Create Esptouch task failed, the esptouch port could be used by other thread");
                        enableEdt(true);
                    }
                });
                return;
            }
            IEsptouchResult firstResult = results.get(0);
            if (!firstResult.isCancelled()) {
                int count = 0;
                final int maxDisplayCount = 5;
                if (firstResult.isSuc()) {
                    final StringBuilder sb = new StringBuilder();
                    for (IEsptouchResult resultInList : results) {
                        sb.append("Esptouch success, bssid = "
                                + resultInList.getBssid()
                                + ",InetAddress = "
                                + resultInList.getInetAddress()
                                .getHostAddress() + "\n");
                        count++;
                        if (count >= maxDisplayCount) {
                            break;
                        }
                    }
                    if (count < results.size()) {
                        sb.append("\nthere's " + (results.size() - count)
                                + " more result(s) without showing\n");
                    }
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            tipsTv.setText(sb.toString());
                            enableEdt(true);
                            loadingCircleBtn.setStatus(LoadingCircleBtn.STATUS_LOAD_SUCCESS);
                        }
                    });
                } else {
                    loadingCircleBtn.setStatus(LoadingCircleBtn.STATUS_LOAD_FAILED);
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            tipsTv.setText("connect failed");
                            enableEdt(true);
                        }
                    });
                }
            }
        }
    }

    private BroadcastReceiver mReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (action == null) {
                return;
            }

            switch (action) {
                case WifiManager.NETWORK_STATE_CHANGED_ACTION:
                    NetworkInfo ni = intent.getParcelableExtra(WifiManager.EXTRA_NETWORK_INFO);
                    if (ni != null && !ni.isConnected()) {
                        if (espTouchAsyncTask != null) {
                            espTouchAsyncTask.cancelEsptouch();
                            espTouchAsyncTask = null;
                            tipsTv.setText("Wifi disconnected or changed");
                        }
                    }
                    break;
            }
        }
    };

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(mReceiver);
    }
}
