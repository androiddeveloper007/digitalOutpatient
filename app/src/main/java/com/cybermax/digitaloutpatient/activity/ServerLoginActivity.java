package com.cybermax.digitaloutpatient.activity;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import com.cybermax.digitaloutpatient.R;
import com.cybermax.digitaloutpatient.bean.share.ServerInfo;
import com.cybermax.digitaloutpatient.constract.ServerLoginContract;
import com.cybermax.digitaloutpatient.databinding.ActivityLoginBinding;
import com.cybermax.digitaloutpatient.dialog.InoculateTipDialog;
import com.cybermax.digitaloutpatient.presenter.ServerLoginPresenter;
import com.cybermax.digitaloutpatient.tool.SharedPreferenceUtil;
import com.lib.tool.DeviceTypeUtil;
import com.lib.views.bordereffect.BorderView;

/**
 * 登录服务器
 */
public class ServerLoginActivity extends AppCompatActivity implements ServerLoginContract.View {
    ActivityLoginBinding binding;
    private ServerLoginPresenter presenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_login);
        initView();
        presenter = new ServerLoginPresenter(this, this);
    }

    private void initView() {
        if (DeviceTypeUtil.checkScreenIsTV(this)) {
            BorderView border = new BorderView(this);
//            border.setBackgroundResource(R.drawable.login_focuse_border);//border_highlight
            border.attachTo(binding.loginRoot);
            binding.ipEt0.setBackgroundResource(R.drawable.login_bg_tv_default);
            binding.portEt.setBackgroundResource(R.drawable.login_bg_tv_default);
            binding.pwdEt.setBackgroundResource(R.drawable.login_bg_tv_default);
        } else {
            binding.loginRoot.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(view.getWindowToken(), 0); //强制隐藏键盘
                }
            });
        }
        ServerInfo serverInfo = (ServerInfo) SharedPreferenceUtil.getInstance().getObject(ServerInfo.class);
        if (serverInfo != null) {
            binding.ipEt0.setText(serverInfo.getIp());
            binding.portEt.setText(serverInfo.getPort());
        }

        try {
            String version = getPackageManager().getPackageInfo(this.getPackageName(), 0).versionName;
            binding.loginVersionTip.setText("v" + version);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
     * 点击登录按钮开始登录
     *
     * @param v
     */
    public void serverLogin(View v) {
        String ip = binding.ipEt0.getText().toString();
        String port = binding.portEt.getText().toString();
        String pwd = binding.pwdEt.getText().toString();
        presenter.login(ip, port, pwd);
    }

    @Override
    public void makeShortToast(String text) {
        Toast.makeText(this, text, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onLoginSuccess() {
        Intent intent = new Intent();
        // 获取用户计算后的结果+
        setResult(1, intent);
        finish();
    }

    @Override
    public void onCheckUpdateSuccess(Object object) {

    }

    @Override
    public void onCheckUpdateFail(String str) {

    }
}
