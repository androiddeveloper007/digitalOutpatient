package com.cybermax.digitaloutpatient.model;

import android.content.Context;

import com.alibaba.fastjson.JSON;
import com.cybermax.digitaloutpatient.bean.StayObserveScreenBean;
import com.cybermax.digitaloutpatient.bean.Ticket;
import com.cybermax.digitaloutpatient.httpapi.HttpResult;
import com.cybermax.digitaloutpatient.httpapi.RetrofitFactory;
import com.lib.http.HttpTaskCallBack;

import java.util.HashMap;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ScreemModel extends  BaseModel{
    public ScreemModel(Context mContext) {
        super(mContext);
    }

    //获取接种等待接口
    public void getCallingTicket(Integer wostId, final HttpTaskCallBack<Ticket> mListener) {
        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("wostId", wostId);
        //无网络时提示
        showConnectError();
        Call<HttpResult<Ticket>> responseBodyCall = RetrofitFactory.getRetrofit().getCallingTicket(hashMap);
        responseBodyCall.enqueue(new Callback<HttpResult<Ticket>>() {
            @Override
            public void onResponse(Call<HttpResult<Ticket>> call, Response<HttpResult<Ticket>> response) {
                try {
                    assert response.body() != null;
                    if (mListener != null) {
                        mListener.onSuccess(response.body().getData());
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    mListener.onFail(e.getMessage());
                }
            }

            @Override
            public void onFailure(Call<HttpResult<Ticket>> call, Throwable t) {
                mListener.onFail(t.getMessage());
            }
        });
    }

    /**
     * 获取屏幕数据
     * @param prtyCodes
     * @param mListener
     */
    public void listSynQueues(List<String> prtyCodes, final HttpTaskCallBack<List<Ticket>> mListener) {
        RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"),JSON.toJSONString(prtyCodes));
        Call<HttpResult<List<Ticket>>> responseBodyCall =
                RetrofitFactory.getRetrofit().listSynQueues(body);
        responseBodyCall.enqueue(new Callback<HttpResult<List<Ticket>>>() {
            @Override
            public void onResponse(Call<HttpResult<List<Ticket>>> call, Response<HttpResult<List<Ticket>>> response) {
                assert response.body() != null;
                if(response.body().isBizSuccessFull()){
                    mListener.onSuccess(response.body().getData());
                }else {
                    mListener.onFail(response.body().getMsg());
                }
            }
            @Override
            public void onFailure(Call<HttpResult<List<Ticket>>> call, Throwable t) {
                mListener.onFail(t.getMessage());
            }
        });
    }

    /**
     * 获取留观屏幕数据
     * @param mListener
     */
    public void listObservers(final HttpTaskCallBack<List<StayObserveScreenBean>> mListener) {
        Call<HttpResult<List<StayObserveScreenBean>>> responseBodyCall =
                RetrofitFactory.getRetrofit().listObservers();
        responseBodyCall.enqueue(new Callback<HttpResult<List<StayObserveScreenBean>>>() {
            @Override
            public void onResponse(Call<HttpResult<List<StayObserveScreenBean>>> call, Response<HttpResult<List<StayObserveScreenBean>>> response) {
                if(response.body().isBizSuccessFull()){
                    mListener.onSuccess(response.body().getData());
                }else {
                    mListener.onFail(response.body().getMsg());
                }
            }
            @Override
            public void onFailure(Call<HttpResult<List<StayObserveScreenBean>>> call, Throwable t) {

            }
        });
    }


}
