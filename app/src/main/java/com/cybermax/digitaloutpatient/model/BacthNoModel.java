package com.cybermax.digitaloutpatient.model;

import android.content.Context;

import com.cybermax.digitaloutpatient.bean.BatchInfo;
import com.cybermax.digitaloutpatient.httpapi.BaseCallBack;
import com.cybermax.digitaloutpatient.httpapi.HttpResult;
import com.cybermax.digitaloutpatient.httpapi.RetrofitFactory;
import com.lib.http.HttpTaskCallBack;

import java.util.HashMap;
import java.util.List;

import retrofit2.Call;

public class BacthNoModel extends  BaseModel{
    public BacthNoModel(Context mContext) {
        super(mContext);
    }

    /**
     * 获取批号
     * @param instId
     * @param mListener
     */
    public void getBatchNo(String instId,  final HttpTaskCallBack<List<BatchInfo>> mListener) {
        HashMap<String, Object> hashMap = new HashMap<String, Object>();
        hashMap.put("instId", instId);
        Call<HttpResult<List<BatchInfo>>> responseBodyCall =
                RetrofitFactory.getRetrofit().getBatchNo( hashMap);
        responseBodyCall.enqueue(new BaseCallBack<HttpResult<List<BatchInfo>>>(false){
            @Override
            public void onSuccess(HttpResult<List<BatchInfo>> httpResult) {
                mListener.onSuccess(httpResult.getData());
            }
        });
    }
}
