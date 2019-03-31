package com.lib.views.progressbar;

import android.content.Context;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;

import com.flyco.tablayout.R;
import com.lib.dialog.BaseDialog;
import com.lib.tool.ScreenSizeUtil;

/**
 * 提示侧滑弹出抽屉
 */
public class TipsDialog extends BaseDialog {

    public TipsDialog(Context context) {
        super(context, R.style.dialog_transparent_bg_style);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_tips);
        getWindow().getAttributes().width = ScreenSizeUtil.getScreenWidth();
        getWindow().getAttributes().height = ScreenSizeUtil.getScreenHeight();
        getWindow().getAttributes().gravity = Gravity.CENTER;
        findViewById(R.id.tipDialogRoot).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dismiss();
            }
        });
    }
}
