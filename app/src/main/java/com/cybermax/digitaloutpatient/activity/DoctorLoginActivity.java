package com.cybermax.digitaloutpatient.activity;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.InputType;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Toast;

import com.cybermax.digitaloutpatient.R;
import com.cybermax.digitaloutpatient.constract.DoctorLoginContract;
import com.cybermax.digitaloutpatient.databinding.ActivityDoctorLoginBinding;
import com.cybermax.digitaloutpatient.presenter.DoctorLoginPresenter;
import com.lib.tool.DeviceTypeUtil;
import com.lib.views.bordereffect.BorderView;

/**
 * 医生登录
 */
public class DoctorLoginActivity extends AppCompatActivity implements DoctorLoginContract.View {
    ActivityDoctorLoginBinding binding;
    private DoctorLoginPresenter doctorLoginPresenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_doctor_login);
        doctorLoginPresenter = new DoctorLoginPresenter(this, this);
        initView();
    }

    /*初始化页面*/
    private void initView() {
        if (DeviceTypeUtil.checkScreenIsTV(this)) {
            BorderView border = new BorderView(this);
            border.setBackgroundResource(R.drawable.login_focuse_border);
            border.attachTo(binding.loginRoot);
        } else {
            binding.loginRoot.setOnClickListener(view -> {
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(view.getWindowToken(), 0); //强制隐藏键盘
            });
        }
        binding.ipEt0.setInputType(InputType.TYPE_CLASS_TEXT);
        binding.pwdEt.setInputType(InputType.TYPE_CLASS_TEXT);
        try {
            String version = getPackageManager().getPackageInfo(this.getPackageName(), 0).versionName;
            binding.loginVersionTip.setText("v" + version);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
     * 点击登录按钮调用登录方法
     *
     * @param v
     */
    public void login(View v) {
        String username = binding.ipEt0.getText().toString();
        String pwd = binding.pwdEt.getText().toString();
        doctorLoginPresenter.login(username, pwd);
    }

    @Override
    public void makeShortToast(String text) {
        Toast.makeText(this, text, Toast.LENGTH_SHORT).show();
    }

    /**
     * 登录成功
     */
    @Override
    public void onLoginSuccess() {
        Intent intent = new Intent();
        // 获取用户计算后的结果+
        setResult(1, intent);
        finish();
    }
}
