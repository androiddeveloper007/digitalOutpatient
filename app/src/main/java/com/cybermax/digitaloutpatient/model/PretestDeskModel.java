package com.cybermax.digitaloutpatient.model;

import android.content.Context;

import com.cybermax.digitaloutpatient.bean.PretestHistory;
import com.cybermax.digitaloutpatient.bean.Question;
import com.cybermax.digitaloutpatient.bean.share.User;
import com.cybermax.digitaloutpatient.httpapi.BaseCallBack;
import com.cybermax.digitaloutpatient.httpapi.HttpResult;
import com.cybermax.digitaloutpatient.httpapi.RetrofitFactory;
import com.lib.dialog.LoadingDialog;
import com.lib.http.HttpTaskCallBack;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * 预检台model
 */
public class PretestDeskModel extends BaseModel {
    public PretestDeskModel(Context mContext) {
        super(mContext);
    }

    /**
     * 预检台历史
     */
    public void pretestHistory(Integer pageNumber, Integer pageSize,
                               String keyword, Integer wostId,
                               final HttpTaskCallBack<PretestHistory> mListener) {
        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("pageNumber", pageNumber);
        hashMap.put("pageSize", pageSize);
        hashMap.put("keyword", keyword);
        hashMap.put("wostId", wostId);
        Call<HttpResult<PretestHistory>> responseBodyCall = RetrofitFactory.getRetrofit().pretestHistory(hashMap);

        responseBodyCall.enqueue(new BaseCallBack<HttpResult<PretestHistory>>(false){
            @Override
            public void onSuccess(HttpResult<PretestHistory> httpResult) {
                mListener.onSuccess(httpResult.getData());
            }

            @Override
            public void onFailure(Call<HttpResult<PretestHistory>> call, Throwable t) {
                super.onFailure(call, t);
            }
        });
    }


    /**
     * 预检台历史
     */
    public void listQuestions(Integer queType, final HttpTaskCallBack<List<Question>> mListener) {

        Call<HttpResult<List<Question>>> responseBodyCall = RetrofitFactory.getRetrofit().listQuestions(queType);
        responseBodyCall.enqueue(new BaseCallBack<HttpResult<List<Question>>>(false){
            @Override
            public void onSuccess(HttpResult<List<Question>> httpResult) {
                mListener.onSuccess(httpResult.getData());
            }
        });
    }
}
