package com.cybermax.digitaloutpatient.dialog;

import android.content.Context;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;

import com.cybermax.digitaloutpatient.R;
import com.lib.dialog.BaseDialog;
import com.lib.tool.RxToast;
import com.lib.tool.ScreenSizeUtil;

public class AAAADialog extends BaseDialog {
    private Context mContext;

    public AAAADialog(Context context) {
        super(context, R.style.CustomDialogStyle);
        mContext = context;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_aaa);
        getWindow().getAttributes().width = ScreenSizeUtil.getScreenWidth() * 9 / 10;
        getWindow().getAttributes().height = ScreenSizeUtil.getScreenHeight() / 2;
        getWindow().getAttributes().gravity = Gravity.CENTER;
        setCanceledOnTouchOutside(false);
        findViewById(R.id.root).setOnClickListener(this);
        initView();
    }

    private void initView() {
    }

    @Override
    public void onClick(View v) {
//        if(v.getId()==R.id.card_title_tv){
//            RxToast.error("aaa");
//        }
        if(v.getId()==R.id.root){
            RxToast.error("aaa");
        }
    }
}
