package com.cybermax.digitaloutpatient.model;

import android.content.Context;

import com.cybermax.digitaloutpatient.bean.share.Workstation;
import com.cybermax.digitaloutpatient.httpapi.HttpResult;
import com.cybermax.digitaloutpatient.httpapi.RetrofitFactory;
import com.lib.http.HttpTaskCallBack;
import com.lib.util.EmptyUtils;

import java.util.HashMap;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PickWorkstationModel extends BaseModel {

    public PickWorkstationModel(Context mContext) {
        super(mContext);
    }

    public void getWorkstation(String userGuid, String wstyType, final HttpTaskCallBack<List<Workstation>> mListener) {
        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("wstyType", wstyType);
        //无网络时提示
        showConnectError();
        if (EmptyUtils.isNotEmpty(userGuid)) {
            hashMap.put("userGuid", userGuid);
        }
        Call<HttpResult<List<Workstation>>> responseBodyCall = RetrofitFactory.getRetrofit().getWorkstation(hashMap);
        responseBodyCall.enqueue(new Callback<HttpResult<List<Workstation>>>() {
            @Override
            public void onResponse(Call<HttpResult<List<Workstation>>> call, Response<HttpResult<List<Workstation>>> response) {
                HttpResult<List<Workstation>> result = response.body();
                if ("1000".equals(result.getEcode())) {
                    mListener.onSuccess(result.getData());
                } else {
                    mListener.onFail(result.getMsg());
                }
            }

            @Override
            public void onFailure(Call<HttpResult<List<Workstation>>> call, Throwable t) {
                mListener.onFail(t.getMessage());
            }
        });
    }
}
