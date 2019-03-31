package com.lib.dialog;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.view.Gravity;

import com.flyco.tablayout.R;
import com.lib.dialog.BaseDialog;
import com.lib.tool.ScreenSizeUtil;

public class LoadingDialog extends BaseDialog {

    public LoadingDialog(Context context) {
        super(context, R.style.dialog_transparent_bg_style);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_loading);
        getWindow().getAttributes().width = ScreenSizeUtil.getScreenWidth();
        getWindow().getAttributes().height = ScreenSizeUtil.getScreenHeight();
        getWindow().getAttributes().gravity = Gravity.CENTER;
        setCanceledOnTouchOutside(false);
    }

    @Override
    public void show() {
        super.show();
    }

}
