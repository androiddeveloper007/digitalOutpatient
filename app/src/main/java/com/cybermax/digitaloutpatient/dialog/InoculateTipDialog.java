package com.cybermax.digitaloutpatient.dialog;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.view.Gravity;
import android.widget.TextView;

import com.cybermax.digitaloutpatient.R;
import com.lib.tool.ScreenSizeUtil;

/**
 * 接种提示弹框
 */
public class InoculateTipDialog extends BaseDialog {

    private TextView tv1,tv2;
    private Context mContext;

    public InoculateTipDialog(Context context) {
        super(context);
        mContext = context;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_inoculate_tip);
        getWindow().getAttributes().width = ScreenSizeUtil.getScreenWidth() / 7 * 3;
        getWindow().getAttributes().height = ScreenSizeUtil.getScreenHeight() / 7 * 3;
        getWindow().getAttributes().gravity = Gravity.CENTER;
        setCanceledOnTouchOutside(false);
        initView();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                dismiss();
            }
        },10*1000);
    }

    private void initView() {
        tv1 = findViewById(R.id.tv1);
        tv2 = findViewById(R.id.tv2);
        tv1.setText("党的建设");
        tv2.setText("请A001号朱芝芝到1号接种台接种");
    }


}
