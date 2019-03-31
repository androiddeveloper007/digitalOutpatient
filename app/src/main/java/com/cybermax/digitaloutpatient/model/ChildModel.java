package com.cybermax.digitaloutpatient.model;

import android.content.Context;

import com.alibaba.fastjson.JSON;
import com.cybermax.digitaloutpatient.bean.CallNumber;
import com.cybermax.digitaloutpatient.bean.Child;
import com.cybermax.digitaloutpatient.bean.Inoculation;
import com.cybermax.digitaloutpatient.bean.QueueInfo;
import com.cybermax.digitaloutpatient.bean.Ticket;
import com.cybermax.digitaloutpatient.enums.HttpCodeEnum;
import com.cybermax.digitaloutpatient.httpapi.BaseCallBack;
import com.cybermax.digitaloutpatient.httpapi.HttpResult;
import com.cybermax.digitaloutpatient.httpapi.RetrofitFactory;
import com.lib.dialog.LoadingDialog;
import com.lib.http.HttpTaskCallBack;
import com.lib.tool.RxToast;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;

/**
 * 接种台接口model
 */
public class ChildModel extends BaseModel {
    private LoadingDialog dialog;
    private final String TAG = "ChildModel";

    public ChildModel(Context mContext) {
        super(mContext);
        dialog = new LoadingDialog(mContext);
    }


    public void listChildren(String keywords, HttpTaskCallBack<List<Child>> mListener) {
        Call<HttpResult<List<Child>>> responseBodyCall = RetrofitFactory.getRetrofit().queryFuzzy(keywords);
        responseBodyCall.enqueue(new BaseCallBack<HttpResult<List<Child>>>() {
            @Override
            public void onSuccess(HttpResult<List<Child>> httpResult) {
                if(httpResult.getEcode().equals(HttpCodeEnum.SUCCESS.getCode()))
                    mListener.onSuccess(httpResult.getData());
                else
                    mListener.onFail(httpResult.getData());
            }
        });
    }

    public void bindChild(Integer ticketId, String chilNo, String chilCardNo,Integer wostId, HttpTaskCallBack<CallNumber> mListener) {
        Call<HttpResult<CallNumber>> responseBodyCall = RetrofitFactory.getRetrofit().bindChild(ticketId,chilNo,chilCardNo,wostId);
        responseBodyCall.enqueue(new BaseCallBack<HttpResult<CallNumber>>() {
            @Override
            public void onSuccess(HttpResult<CallNumber> httpResult) {
                if(httpResult.getEcode().equals(HttpCodeEnum.SUCCESS.getCode()))
                    mListener.onSuccess(httpResult.getData());
                else {
                    mListener.onFail(httpResult.getData());
                }
            }
        });
    }
}