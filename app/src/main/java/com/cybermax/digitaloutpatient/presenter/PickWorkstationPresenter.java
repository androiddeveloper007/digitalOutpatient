package com.cybermax.digitaloutpatient.presenter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.GridLayoutManager;
import android.view.View;

import com.cybermax.digitaloutpatient.bean.share.Workstation;
import com.cybermax.digitaloutpatient.constract.CommonContract;
import com.cybermax.digitaloutpatient.constract.PicketWorkstationContract;
import com.cybermax.digitaloutpatient.model.PickWorkstationModel;
import com.cybermax.digitaloutpatient.tool.SharedPreferenceUtil;
import com.google.gson.reflect.TypeToken;
import com.lib.http.HttpTaskCallBack;
import com.lib.util.GsonUtil;
import com.lib.views.recyclerview.BaseQuickAdapter;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.List;


public class PickWorkstationPresenter extends BasePresenter {

    private PickWorkstationModel mModel;
    private PicketWorkstationContract.View mView;

    public PickWorkstationPresenter(Context context, PicketWorkstationContract.View view) {
        super(context);
        mModel = new PickWorkstationModel(context);
        mContext = context;
        mView = view;
    }

    public void getWorkstation(String userGuid, String wstyType) {
        mModel.getWorkstation(userGuid,wstyType, new HttpTaskCallBack<List<Workstation>>() {
            @Override
            public void onSuccess( List<Workstation> beanList) {
                mView.initWorkstaionChooseView(beanList);
            }

            @Override
            public void onFail(String result) {
                mView.makeShortToast("请求服务器异常!");
                mView.finishActiviyAsFailed();
            }
        });
    }

}
