package com.cybermax.digitaloutpatient.httpapi;

import android.util.Log;

import com.cybermax.digitaloutpatient.enums.HttpCodeEnum;
import com.lib.tool.RxToast;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class BaseCallBack<T> implements Callback<T> {

    private boolean showTip = true;
    public BaseCallBack() {}

    public BaseCallBack(boolean showTip) {
        this.showTip = showTip;
    }

    @Override
    public void onResponse(Call<T> call, Response<T> response) {
        if(showTip) {
            HttpResult result= (HttpResult)response.body();
            if(null == result) {
                RxToast.error("请求失败");
                return;
            }
            if(result.getEcode().equals(HttpCodeEnum.SUCCESS.getCode())) {
                RxToast.success(result.getMsg());
            }else {
                RxToast.error(result.getMsg());
            }

        }
        onSuccess(response.body());
    }

    public void onSuccess(T httpResult) {}


    @Override
    public void onFailure(Call<T> call, Throwable t) {
        RxToast.error("服务异常");
        Log.e("BaseCallBack","请求失败",t);
    }
}
