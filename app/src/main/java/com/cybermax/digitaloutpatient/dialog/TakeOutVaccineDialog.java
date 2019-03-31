package com.cybermax.digitaloutpatient.dialog;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.KeyboardShortcutGroup;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.cybermax.digitaloutpatient.R;
import com.cybermax.digitaloutpatient.bean.Inoculation;
import com.cybermax.digitaloutpatient.bean.VacccrkBarcode;
import com.cybermax.digitaloutpatient.constract.BaseWorkstationContract;
import com.cybermax.digitaloutpatient.constract.InoculateDeskContract;
import com.cybermax.digitaloutpatient.presenter.InoculateDeskPresenter;
import com.cybermax.digitaloutpatient.tool.ScanGunKeyEventHelper;
import com.lib.tool.RxToast;
import com.lib.tool.ScreenSizeUtil;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 取出疫苗弹框
 */
public class TakeOutVaccineDialog extends BaseDialog implements InoculateDeskContract.DialogView, View.OnClickListener
        , ScanGunKeyEventHelper.OnScanSuccessListener {
    private TextView tv;
    private InoculateDeskPresenter inocPresenter;
    private Inoculation inoculation;
    private BaseWorkstationContract.OnClickListener inoculationClickListener;
    private final int SCAN_EM_CODE = 2;
    private final int INTERVAL = 500; //输入时间间隔为300毫秒
    private Handler mHandler;
    private EditText vaccineCode;
    private ScanGunKeyEventHelper mScanGunKeyEventHelper;

    public TakeOutVaccineDialog(Context context, InoculateDeskPresenter inocPresenter, Inoculation inoculation) {
        super(context);
        getContext().setTheme(R.style.dialogDisableSoftInput);
        mScanGunKeyEventHelper = new ScanGunKeyEventHelper(context, this);
        this.inocPresenter = inocPresenter;
        this.inoculation = inoculation;
        inocPresenter.setDialogView(this);
    }

    @Override
    public boolean dispatchKeyEvent(KeyEvent event) {
        mScanGunKeyEventHelper.analysisKeyEvent(event);
        return super.dispatchKeyEvent(event);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_take_out_vaccine);
        getWindow().getAttributes().width = ScreenSizeUtil.getScreenWidth() / 2;
        getWindow().getAttributes().height = ScreenSizeUtil.getScreenHeight() / 3 * 2;
        getWindow().getAttributes().gravity = Gravity.CENTER;
        setCanceledOnTouchOutside(false);
        initView();
    }

    private void initView() {
        findViewById(R.id.ivClose).setOnClickListener(this);
        vaccineCode = findViewById(R.id.vaccineCode);
        vaccineCode.requestFocus();
        mHandler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                if (msg.what == SCAN_EM_CODE) {
                    String val = vaccineCode.getText().toString().trim();
                    if (val.length() > 0)
                        scanSuccessShowTip(val);//扫码成功，调服务验证二维码
                }
            }
        };
        /*vaccineCode.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                Log.e("ZZP",editable.toString());
                if (mHandler.hasMessages(SCAN_EM_CODE)) {
                    mHandler.removeMessages(SCAN_EM_CODE);
                }
                mHandler.sendEmptyMessageDelayed(SCAN_EM_CODE, INTERVAL);
            }
        });*/
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.ivClose) {
            dismiss();
        }
    }

    public void scanSuccessShowTip(String code) {
        if (inoculation == null) return;
        Map<String, Object> params = new HashMap<>();
        params.put("barCode", code);
        params.put("bactCode", inoculation.getBactCode());
        params.put("corpCode", inoculation.getInstCorporation());
        params.put("batchNo", inoculation.getInstBatchNo());
        params.put("key", inoculation.getFridgeInfo().getKey());
        params.put("empId", inoculation.getFridgeInfo().getEmpId());
        params.put("deviceType", inoculation.getFridgeInfo().getDeviceType());
        params.put("corpId", inoculation.getFridgeInfo().getCorpId());
        inocPresenter.scanVaccineBarCode(inoculation, code);
//        validateCode(params);
    }

    @Override
    public void dismiss() {
        super.dismiss();
    }


    @Override
    public void validateCode(VacccrkBarcode vacccrkBarcode) {
        //显示验证成功的提示倒计时
        findViewById(R.id.layoutTip).setVisibility(View.VISIBLE);
        tv = findViewById(R.id.tipText);
        // 验证成功，关闭验证框，开启接种框
        dismiss();
        // 显示接种信息
        // 写入电子监管码
        inoculation.setBarCode(vaccineCode.getText().toString());
        ChooseBodyPartDialog dialog = new ChooseBodyPartDialog(getContext(), inoculation);
        dialog.show();
        dialog.setOnCommitListener(new BaseWorkstationContract.OnClickListener() {
            @Override
            public void onClick(Inoculation inoculation) {
                if (inoculationClickListener != null) {
                    inoculationClickListener.onClick(inoculation);
                    dialog.dismiss();
                }
            }
        });
    }

    @Override
    public void verifyFailed(String msg) {
        //当输入框有文字时，先将原文字清除
        RxToast.error(msg);
        if (vaccineCode.getText().length() > 0) {
            vaccineCode.setText("");
        }
    }

    public void setInoculationClickListener(BaseWorkstationContract.OnClickListener inoculationClickListener) {
        this.inoculationClickListener = inoculationClickListener;
    }

    @Override
    public void onScanSuccess(String barcode) {
        vaccineCode.setText(barcode);
        if (mHandler.hasMessages(SCAN_EM_CODE)) {
            mHandler.removeMessages(SCAN_EM_CODE);
        }
        mHandler.sendEmptyMessageDelayed(SCAN_EM_CODE, INTERVAL);
    }

    @Override
    public void onProvideKeyboardShortcuts(List<KeyboardShortcutGroup> data, @Nullable Menu menu, int deviceId) {

    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }
}
