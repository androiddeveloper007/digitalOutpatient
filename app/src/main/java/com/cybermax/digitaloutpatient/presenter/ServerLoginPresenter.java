package com.cybermax.digitaloutpatient.presenter;

import android.content.Context;

import com.cybermax.digitaloutpatient.bean.share.ServerInfo;
import com.cybermax.digitaloutpatient.constract.ServerLoginContract;
import com.cybermax.digitaloutpatient.model.SystemLoginModel;
import com.cybermax.digitaloutpatient.tool.SharedPreferenceUtil;
import com.lib.http.HttpTaskCallBack;
import com.lib.util.EmptyUtils;


public class ServerLoginPresenter extends BasePresenter {

    //private ServerLoginModel mModel;
    private SystemLoginModel mModel;
    private ServerLoginContract.View mView;

    public ServerLoginPresenter(Context context, ServerLoginContract.View view) {
        super(context);
        mModel = new SystemLoginModel(context);
        mContext = context;
        mView = view;
    }

    public void login(String ip, String port, String pwd) {
        if(checkStrEmpty(ip, port, pwd)){
            return;
        }
        String url = "http://"+ip+":"+port+"/";
        mModel.serverLogin(url, pwd, new HttpTaskCallBack<Object>() {
            @Override
            public void onSuccess(Object result) {
                //保存数据
                ServerInfo  serverInfo = new ServerInfo();
                serverInfo.setIp(ip);
                serverInfo.setPort(port);
                serverInfo.setNettyPort("10010");
                boolean isSuccess= SharedPreferenceUtil.getInstance().putObject(serverInfo);
                if(isSuccess){
                    mView.onLoginSuccess();
                }
            }
            @Override
            public void onFail(String result) {
                mView.makeShortToast("登录失败");
            }
        });
    }

    private boolean checkStrEmpty(String ip, String port, String pwd) {
        if(EmptyUtils.isEmpty(ip)) {
            mView.makeShortToast("服务器地址不能为空");
            return true;
        }
        if(EmptyUtils.isEmpty(port)) {
            mView.makeShortToast("服务器端口不能为空");
            return true;
        }
        if(EmptyUtils.isEmpty(pwd)) {
            mView.makeShortToast("密码不能为空");
            return true;
        }
        return false;
    }

}
