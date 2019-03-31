package com.cybermax.digitaloutpatient.model;

import android.content.Context;

import com.alibaba.fastjson.JSON;
import com.cybermax.digitaloutpatient.bean.CallNumber;
import com.cybermax.digitaloutpatient.bean.Child;
import com.cybermax.digitaloutpatient.bean.Inoculation;
import com.cybermax.digitaloutpatient.bean.Operator;
import com.cybermax.digitaloutpatient.bean.QueueInfo;
import com.cybermax.digitaloutpatient.bean.Ticket;
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
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * 接种台接口model
 */
public class TicketModel extends BaseModel {
    private LoadingDialog dialog;
    private final String TAG = "TicketModel";

    public TicketModel(Context mContext) {
        super(mContext);
        dialog = new LoadingDialog(mContext);
    }

    //获取接种等待、过号、完成接口
    public void listTicketQueue(String prtyCode, int tiacStatus, final HttpTaskCallBack<List<Ticket>> mListener) {
        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("prtyCode", prtyCode);
        hashMap.put("tiacStatus", tiacStatus);
        Call<HttpResult<List<Ticket>>> responseBodyCall = RetrofitFactory.getRetrofit().listTicketQueue(tiacStatus, prtyCode);
        responseBodyCall.enqueue(new BaseCallBack<HttpResult<List<Ticket>>>(false) {
            @Override
            public void onSuccess(HttpResult<List<Ticket>> httpResult) {
                mListener.onSuccess(httpResult.getData());
            }
        });
    }

    //叫号
    public void callNumber(Map<String,Object> operator, final HttpTaskCallBack<CallNumber> mListener) {

        Call<HttpResult<CallNumber>> responseBodyCall = RetrofitFactory.getRetrofit().callNumber(operator);
        responseBodyCall.enqueue(new BaseCallBack<HttpResult<CallNumber>>() {

            @Override
            public void onSuccess(HttpResult<CallNumber> httpResult) {
                try {
                    mListener.onSuccess(httpResult.getData());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    //重呼（叫号）
    public void reCallNumber(Map<String,Object> operator, final HttpTaskCallBack<Object> mListener) {
        Call<HttpResult<Object>> responseBodyCall = RetrofitFactory.getRetrofit().recallNumber(operator);
        responseBodyCall.enqueue(new BaseCallBack<HttpResult<Object>>());
    }

    //过号
    public void passNumber(Map<String,Object> operator, final HttpTaskCallBack<Object> mListener) {
        Call<HttpResult<Object>> responseBodyCall = RetrofitFactory.getRetrofit().passNumber(operator);
        responseBodyCall.enqueue(new BaseCallBack<HttpResult<Object>>() {
            @Override
            public void onSuccess(HttpResult<Object> httpResult) {
                if(httpResult.getEcode().equals(HttpCodeEnum.SUCCESS.getCode())) {
                    mListener.onSuccess(httpResult.getData());
                } else {
                    mListener.onFail(httpResult.getData());
                }
            }
        });
    }


    //确认接种
    public void confirmInoculate(Map<String,Object> operator, final HttpTaskCallBack<Object> mListener) {

        RequestBody body = RequestBody.create(MediaType.parse("application/json; charset=utf-8"),JSON.toJSONString(operator));
        Call<HttpResult<Object>> responseBodyCall = RetrofitFactory.getRetrofit().completeInoc(body);
        responseBodyCall.enqueue(new BaseCallBack<HttpResult<Object>>() {
            @Override
            public void onSuccess(HttpResult<Object> httpResult) {
                if(httpResult.getEcode().equals(HttpCodeEnum.SUCCESS.getCode())) {
                    mListener.onSuccess(httpResult.getData());
                }
            }
        });
    }

    public void getQueueCount(Integer wostId, HttpTaskCallBack<QueueInfo> mListener) {
        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("wostId", wostId);
        Call<HttpResult<QueueInfo>> responseBodyCall = RetrofitFactory.getRetrofit().countQueue(wostId);
        responseBodyCall.enqueue(new BaseCallBack<HttpResult<QueueInfo>>(false) {
            @Override
            public void onSuccess(HttpResult<QueueInfo> httpResult) {
                if(httpResult.getEcode().equals(HttpCodeEnum.SUCCESS.getCode())) {
                    mListener.onSuccess(httpResult.getData());
                }
            }
        });
    }

    public void getProcessingTicket(Integer wostId, HttpTaskCallBack<CallNumber> mListener) {
        Call<HttpResult<CallNumber>> responseBodyCall = RetrofitFactory.getRetrofit().getProcessingTicket(wostId);
        responseBodyCall.enqueue(new BaseCallBack<HttpResult<CallNumber>>() {
            @Override
            public void onSuccess(HttpResult<CallNumber> httpResult) {
            mListener.onSuccess(httpResult.getData());
            }
        });
    }

    public void confirmSingleInoc(Inoculation inoculation, HttpTaskCallBack<Inoculation> mListener) {
        Call<HttpResult<Inoculation>> responseBodyCall = RetrofitFactory.getRetrofit().confirmSingleInoc(inoculation);
        responseBodyCall.enqueue(new BaseCallBack<HttpResult<Inoculation>>() {
            @Override
            public void onSuccess(HttpResult<Inoculation> httpResult) {
                if(httpResult.getEcode().equals(HttpCodeEnum.SUCCESS.getCode()))
                    mListener.onSuccess(httpResult.getData());
                else
                    mListener.onFail(httpResult.getData());
            }
        });
    }

    /**
     * 完成
     * @param operator
     * @param mListener
     */
    public void complete(Map<String,Object> operator, HttpTaskCallBack<Object> mListener) {
        Call<HttpResult<Object>> responseBodyCall = RetrofitFactory.getRetrofit().complete(operator);
        responseBodyCall.enqueue(new BaseCallBack<HttpResult<Object>>() {
            @Override
            public void onSuccess(HttpResult<Object> httpResult) {
                if(httpResult.getEcode().equals(HttpCodeEnum.SUCCESS.getCode())) {
                    mListener.onSuccess(httpResult.getData());
                }
            }
        });
    }

    /**
     *
     * @param inoculation
     * @param mListener
     */
    public void cancel(Inoculation inoculation, HttpTaskCallBack<Inoculation> mListener) {
        Call<HttpResult<Inoculation>> responseBodyCall = RetrofitFactory.getRetrofit().cancel(inoculation);
        responseBodyCall.enqueue(new BaseCallBack<HttpResult<Inoculation>>() {
            @Override
            public void onSuccess(HttpResult<Inoculation> httpResult) {
                if(httpResult.getEcode().equals(HttpCodeEnum.SUCCESS.getCode()))
                    mListener.onSuccess(httpResult.getData());
                else
                    mListener.onFail(httpResult.getData());
            }
        });
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

}