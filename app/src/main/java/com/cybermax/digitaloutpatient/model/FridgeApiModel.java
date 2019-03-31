package com.cybermax.digitaloutpatient.model;

import android.content.Context;

import com.alibaba.fastjson.JSON;
import com.cybermax.digitaloutpatient.bean.CallNumber;
import com.cybermax.digitaloutpatient.bean.Child;
import com.cybermax.digitaloutpatient.bean.Inoculation;
import com.cybermax.digitaloutpatient.bean.QueueInfo;
import com.cybermax.digitaloutpatient.bean.Ticket;
import com.cybermax.digitaloutpatient.bean.VacccrkBarcode;
import com.cybermax.digitaloutpatient.enums.HttpCodeEnum;
import com.cybermax.digitaloutpatient.httpapi.BaseCallBack;
import com.cybermax.digitaloutpatient.httpapi.HttpResult;
import com.cybermax.digitaloutpatient.httpapi.RetrofitFactory;
import com.lib.dialog.LoadingDialog;
import com.lib.http.HttpTaskCallBack;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;

/**
 * 接种台接口model
 */
public class FridgeApiModel extends BaseModel {
    private final String TAG = "FridgeApiModel";

    public FridgeApiModel(Context mContext) {
        super(mContext);
    }

    public void scanVaccineBarCode(Map<String,Object> params, HttpTaskCallBack<VacccrkBarcode> mListener) {
        Call<HttpResult<VacccrkBarcode>> responseBodyCall = RetrofitFactory.getRetrofit().scanVaccineBarCode(params);
        responseBodyCall.enqueue(new BaseCallBack<HttpResult<VacccrkBarcode>>() {
            @Override
            public void onSuccess(HttpResult<VacccrkBarcode> httpResult) {
                if(httpResult.getEcode().equals(HttpCodeEnum.SUCCESS.getCode()))
                    mListener.onSuccess(httpResult.getData());
                else
                    mListener.onFail(httpResult.getMsg());
            }
        });
    }

}