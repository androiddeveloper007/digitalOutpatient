package com.cybermax.digitaloutpatient.model;

import android.content.Context;

import com.cybermax.digitaloutpatient.bean.InoculatePlace;
import com.cybermax.digitaloutpatient.httpapi.BaseCallBack;
import com.cybermax.digitaloutpatient.httpapi.HttpResult;
import com.cybermax.digitaloutpatient.httpapi.RetrofitFactory;
import com.lib.http.HttpTaskCallBack;

import java.util.HashMap;
import java.util.List;

import retrofit2.Call;

public class InoculatePlaceModel extends  BaseModel{
    public InoculatePlaceModel(Context mContext) {
        super(mContext);
    }

    /**
     * 获取所有接种部位
     * @param mListener
     */
    public void getInoculatePlace(final HttpTaskCallBack<List<InoculatePlace>> mListener) {
        //无网络时提示
        showConnectError();
        HashMap<String, Object> hashMap = new HashMap<>();
        Call<HttpResult<List<InoculatePlace>>> responseBodyCall =
                RetrofitFactory.getRetrofit().getInoculatePlace( hashMap);

        responseBodyCall.enqueue(new BaseCallBack<HttpResult<List<InoculatePlace>>>(false){
            @Override
            public void onSuccess(HttpResult<List<InoculatePlace>> httpResult) {
                mListener.onSuccess(httpResult.getData());
            }
        });
    }
}
