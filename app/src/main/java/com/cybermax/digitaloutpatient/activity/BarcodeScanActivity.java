package com.cybermax.digitaloutpatient.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Vibrator;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;

import com.cybermax.digitaloutpatient.bean.CallNumber;
import com.cybermax.digitaloutpatient.bean.Child;
import com.cybermax.digitaloutpatient.bean.share.User;
import com.cybermax.digitaloutpatient.bean.share.Workstation;
import com.cybermax.digitaloutpatient.constract.BaseWorkstationContract;
import com.cybermax.digitaloutpatient.fragment.desk.BaseWorkstationFragment;
import com.cybermax.digitaloutpatient.presenter.ChildPresenter;
import com.cybermax.digitaloutpatient.tool.SharedPreferenceUtil;
import com.flyco.tablayout.R;
import com.lib.qrcodescan.QRCodeView;
import com.lib.qrcodescan.ZXingView;
import com.lib.tool.RxToast;

import java.util.List;

public class BarcodeScanActivity extends AppCompatActivity implements BaseWorkstationContract.ChildView, QRCodeView.Delegate {
    private static final String TAG = BarcodeScanActivity.class.getSimpleName();
    private ZXingView mZXingView;
    private ChildPresenter childPresenter;
    private Workstation workstation;
    private User user;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_barcode_scan);
        setSupportActionBar((Toolbar) findViewById(R.id.toolbar));

        mZXingView = findViewById(R.id.zxingview);
        mZXingView.setDelegate(this);
        mZXingView.changeToScanBarcodeStyle();
        workstation = (Workstation) SharedPreferenceUtil.getInstance().getObject(Workstation.class);
        user = (User)SharedPreferenceUtil.getInstance().getObject(User.class);
        childPresenter = new ChildPresenter(this,this,workstation,user);
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        mZXingView.startCamera(); // 打开后置摄像头开始预览，但是并未开始识别
//        mZXingView.startCamera(Camera.CameraInfo.CAMERA_FACING_FRONT); // 打开前置摄像头开始预览，但是并未开始识别
        mZXingView.startSpotAndShowRect(); // 显示扫描框，并开始识别
    }

    @Override
    protected void onStop() {
        mZXingView.stopCamera(); // 关闭摄像头预览，并且隐藏扫描框
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        mZXingView.onDestroy(); // 销毁二维码扫描控件
        super.onDestroy();
    }

    private void vibrate() {
        Vibrator vibrator = (Vibrator) getSystemService(VIBRATOR_SERVICE);
        vibrator.vibrate(200);
    }

    @Override
    public void onScanQRCodeSuccess(String result) {
        int ticketId = getIntent().getIntExtra("ticketId", -1);
        if(ticketId == -1){
            RxToast.error("请重新开启扫码");
            return;
        }
        childPresenter.bindChild(ticketId,null, result, workstation.getId());

    }

    @Override
    public void onCameraAmbientBrightnessChanged(boolean isDark) {
        // 这里是通过修改提示文案来展示环境是否过暗的状态，接入方也可以根据 isDark 的值来实现其他交互效果
        String tipText = mZXingView.getScanBoxView().getTipText();
        String ambientBrightnessTip = "\n环境过暗，请打开闪光灯";
        if (isDark) {
            if (!tipText.contains(ambientBrightnessTip)) {
                mZXingView.getScanBoxView().setTipText(tipText + ambientBrightnessTip);
            }
        } else {
            if (tipText.contains(ambientBrightnessTip)) {
                tipText = tipText.substring(0, tipText.indexOf(ambientBrightnessTip));
                mZXingView.getScanBoxView().setTipText(tipText);
            }
        }
    }

    @Override
    public void onScanQRCodeOpenCameraError() {
        Log.e(TAG, "打开相机出错");
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        mZXingView.startSpotAndShowRect(); // 显示扫描框，并开始识别
    }

    @Override
    public void showChildren(List<Child> children) {}

    @Override
    public void showBindTicket(CallNumber callNumber) {
        Intent intent = new Intent(this,BaseWorkstationFragment.class);
        intent.putExtra("callNumber",callNumber);
        setResult(1000, intent);
        finish();
    }

    @Override
    public void stopScan(CallNumber data) {
//        vibrate();
//        mZXingView.startSpot(); // 开始识别
        finish();
    }
}