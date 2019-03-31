package com.cybermax.digitaloutpatient.dialog;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;

import com.cybermax.digitaloutpatient.R;
import com.cybermax.digitaloutpatient.activity.PickWorkstationActivity;
import com.cybermax.digitaloutpatient.activity.SplashActivity;
import com.cybermax.digitaloutpatient.bean.share.ServerInfo;
import com.cybermax.digitaloutpatient.bean.share.User;
import com.cybermax.digitaloutpatient.tool.SharedPreferenceUtil;
import com.lib.dialog.BaseDialog;
import com.lib.tool.ScreenSizeUtil;
import com.lib.views.bordereffect.BorderView;

/**
 * 盒子设置弹框
 */
public class ScreenSetDialog extends BaseDialog implements View.OnClickListener {
    private SharedPreferenceUtil sp;

    public ScreenSetDialog(Context context) {
        super(context, R.style.CustomDialogStyle);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_tv_box_set);
        getWindow().getAttributes().width = ScreenSizeUtil.getScreenWidth() / 3 * 2;
        getWindow().getAttributes().height = ScreenSizeUtil.getScreenHeight() / 5 * 4;
        getWindow().getAttributes().gravity = Gravity.CENTER;
        setCanceledOnTouchOutside(true);
        initView();
    }

    private void initView() {
        BorderView border = new BorderView(mContext);
//        border.setBackgroundResource(R.drawable.logout_focuse_border);
        border.attachTo(findViewById(R.id.rootLayout));
        findViewById(R.id.logout).setOnClickListener(this);
        findViewById(R.id.confirmClean).setOnClickListener(this);
        ServerInfo info = (ServerInfo) SharedPreferenceUtil.getInstance().getObject(ServerInfo.class);
        ((TextView) findViewById(R.id.serverIpText)).setText(info.getIp());
        ((TextView) findViewById(R.id.portText)).setText(info.getPort());
        ((TextView) findViewById(R.id.connectStateText)).setText("正常连接");
    }

    @Override
    public void onClick(View view) {
        if (sp == null) sp = SharedPreferenceUtil.getInstance();
        switch (view.getId()) {
            case R.id.logout:
                sp.removeObject(User.class);
                mContext.startActivity(new Intent(mContext, PickWorkstationActivity.class));
                dismiss();
                break;
            case R.id.confirmClean:
                sp.clearAll(mContext);
                mContext.startActivity(new Intent(mContext, SplashActivity.class));
                dismiss();
                break;
        }
    }
}
