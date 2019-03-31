package com.cybermax.digitaloutpatient.presenter;

import android.content.Context;

import com.cybermax.digitaloutpatient.bean.share.User;
import com.cybermax.digitaloutpatient.constract.DoctorLoginContract;
import com.cybermax.digitaloutpatient.model.SystemLoginModel;
import com.cybermax.digitaloutpatient.tool.SharedPreferenceUtil;
import com.lib.http.HttpTaskCallBack;
import com.lib.util.EmptyUtils;


public class DoctorLoginPresenter extends BasePresenter {

    private SystemLoginModel mModel;
    private DoctorLoginContract.View mView;

    public DoctorLoginPresenter(Context context, DoctorLoginContract.View view) {
        super(context);
        mModel = new SystemLoginModel(context);
        mContext = context;
        mView = view;
    }

    public void login(String userName, String password) {
        if(checkStrEmpty(userName, password)){
            return;
        }
        mModel.sysUserLogin(userName, password, new HttpTaskCallBack<User>() {
            @Override
            public void onSuccess(User user) {
                try {
                    if(user!=null&& SharedPreferenceUtil.getInstance().putObject(user)){
                        mView.makeShortToast("登录成功");
                        mView.onLoginSuccess();
                    }else {
                        mView.makeShortToast("登录失败，账号或者密码错误");
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    mView.makeShortToast("登录失败，未知异常");
                }
            }
            @Override
            public void onFail(String result) {
                mView.makeShortToast("登录失败，未知异常");
            }
        });
    }
    private boolean checkStrEmpty(String uerName, String pwd) {
        if(EmptyUtils.isEmpty(uerName)) {
            mView.makeShortToast("账号不能为空");
            return true;
        }
        if(EmptyUtils.isEmpty(pwd)) {
            mView.makeShortToast("密码不能为空");
            return true;
        }
        return false;
    }

}
