package com.cybermax.digitaloutpatient.presenter;

import android.content.Context;
import android.util.Log;

import com.cybermax.digitaloutpatient.bean.CallNumber;
import com.cybermax.digitaloutpatient.bean.Child;
import com.cybermax.digitaloutpatient.bean.Inoculation;
import com.cybermax.digitaloutpatient.bean.QueueInfo;
import com.cybermax.digitaloutpatient.bean.Ticket;
import com.cybermax.digitaloutpatient.bean.share.User;
import com.cybermax.digitaloutpatient.bean.share.Workstation;
import com.cybermax.digitaloutpatient.constract.BaseWorkstationContract;
import com.cybermax.digitaloutpatient.constract.InoculateDeskContract;
import com.cybermax.digitaloutpatient.enums.HttpCodeEnum;
import com.cybermax.digitaloutpatient.model.TicketModel;
import com.lib.http.HttpTaskCallBack;
import com.lib.tool.ClickFilter;
import com.lib.tool.RxToast;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class TicketPresenter extends BasePresenter {

    private TicketModel mModel;
    private BaseWorkstationContract.View mView;
    private Workstation workstation;
    private User user;
    private String TAG = "InoculateDeskPresenter";

    public TicketPresenter(Context context, BaseWorkstationContract.View mView , Workstation workstation, User user ) {
        super(context);
        this.mView = mView;
        mModel = new TicketModel(context);
        this.workstation = workstation;
        this.user = user;
    }


    /**
     *
     */
    public void  getProcessingTicket(Integer wostId){
        try {
            mModel.getProcessingTicket(wostId, new HttpTaskCallBack<CallNumber>() {
                @Override
                public void onSuccess(CallNumber data) {
                    if(null != data)
                        mView.showCallingTicket(data);
                    getQueueCount(wostId);
                }
            });
        }catch (Exception e) {
            RxToast.error(HttpCodeEnum.ERROR.getMsg());
            Log.e(TAG,"",e);
        }
    }



    /**
     * 呼叫
     * @param wostId
     */
    public void callNumber(int wostId,Integer tiacId) {
        try {
            Map<String,Object> operator = new HashMap();
            operator.put("prtyCode", workstation.getPrtyCode());
            operator.put("tiacDoctor", user.getUserTrueName());
            operator.put("wostId", wostId);
            if(null != tiacId)
                operator.put("tiacId", tiacId);

            mModel.callNumber(operator, new HttpTaskCallBack<CallNumber>() {
                @Override
                public void onSuccess(CallNumber data) {
                    if(null != data)
                        mView.showCallingTicket(data);
                    getQueueCount(wostId);
                }

                @Override
                public void onFail(CallNumber data) {
                    getQueueCount(wostId);
                }
            });
        }catch (Exception e) {
            RxToast.error(HttpCodeEnum.ERROR.getMsg());
            Log.e(TAG,"",e);
        }
    }

    /**
     * 重新呼叫
     * @param wostId
     * @param tiacId
     */
    public void reCallNumber(Integer wostId,Integer tiacId) {
        try {
            Map<String,Object> operator = new HashMap();
            operator.put("prtyCode", workstation.getPrtyCode());
            operator.put("tiacDoctor", user.getUserTrueName());
            operator.put("wostId", wostId);
            operator.put("tiacId", tiacId);
            mModel.reCallNumber(operator, new HttpTaskCallBack<Object>() {
                @Override
                public void onSuccess(Object result) {
                    getQueueCount(wostId);
                }

                @Override
                public void onFail(Object result) {
                    getQueueCount(wostId);
                }
            });
        }catch (Exception e) {
            RxToast.error(HttpCodeEnum.ERROR.getMsg());
            Log.e(TAG,"",e);
        }
    }

    //确认接种
    public void confirmInoculate(Integer wostId,Integer tiacId,List<Inoculation>  inoculations) {
        try {
            HashMap<String, Object> operator = new HashMap<>();
            operator.put("prtyCode", workstation.getPrtyCode());
            operator.put("tiacDoctor", user.getUserTrueName());
            operator.put("wostId", wostId);
            operator.put("tiacId", tiacId);
            operator.put("inoculations", inoculations);
            mModel.confirmInoculate(operator,new HttpTaskCallBack<Object>() {
                @Override
                public void onSuccess(Object data) {
                    mView.clearCallingTicket();
                    if(mView.getSwitchCallChecked()) {
                        callNumber(wostId,null);
                    }
                    getQueueCount(wostId);
                }

                @Override
                public void onFail(Object data) {
                    getQueueCount(wostId);
                }
            });
        }catch (Exception e) {
            RxToast.error(HttpCodeEnum.ERROR.getMsg());
            Log.e(TAG,"",e);
        }

    }








    // 获取接种，排队，叫号数
    public void getQueueCount(Integer wostId) {
        try {
            mModel.getQueueCount(wostId,new HttpTaskCallBack<QueueInfo>() {
                @Override
                public void onSuccess(QueueInfo data) {
                    mView.showQueueCount(data);
                }
            });
        }catch (Exception e) {
            RxToast.error(HttpCodeEnum.ERROR.getMsg());
            Log.e(TAG,"",e);
        }
    }

    /**
     * 过号
     * @param tiacId
     * @param wostId
     */
    public void passNumber(Integer tiacId, Integer wostId) {
        try {
            Map<String,Object> operator = new HashMap<>();
            operator.put("prtyCode", workstation.getPrtyCode());
            operator.put("tiacDoctor", user.getUserTrueName());
            operator.put("tiacId", tiacId);
            operator.put("wostId", wostId);
            mModel.passNumber(operator, new HttpTaskCallBack<Object>() {
                @Override
                public void onSuccess(Object data) {
                    mView.clearCallingTicket();
                    // 是否自动顺呼
                    if(mView.getSwitchCallChecked()) {
                        callNumber(wostId,null);
                    }
                    getQueueCount(wostId);
                }

                @Override
                public void onFail(Object data) {
                    getQueueCount(wostId);
                }
            });
        }catch (Exception e) {
            RxToast.error(HttpCodeEnum.ERROR.getMsg());
            Log.e(TAG,"",e);
        }
    }


    //确认接种
    public void complete(Map<String,Object> operator) {
        try {
            Integer wostId = (int)operator.get("wostId");
            mModel.complete(operator,new HttpTaskCallBack<Object>() {
                @Override
                public void onSuccess(Object data) {
                    mView.clearCallingTicket();
                    if(mView.getSwitchCallChecked()) {
                        callNumber(wostId,null);
                    }
                    getQueueCount(wostId);
                }
                @Override
                public void onFail(Object data) {
                    getQueueCount(wostId);
                }
            });
        }catch (Exception e) {
            RxToast.error(HttpCodeEnum.ERROR.getMsg());
            Log.e(TAG,"",e);
        }

    }

    /**
     *
     */
    public void  reloadTicketQueue(int tiacStatus){
        try {
            mModel.listTicketQueue(workstation.getPrtyCode(), tiacStatus, new HttpTaskCallBack<List<Ticket>>() {
                @Override
                public void onSuccess(List<Ticket> data) {
                    mView.initTicketQueue(data,tiacStatus);
                    getQueueCount(workstation.getId());
                }
            });
        }catch (Exception e) {
            RxToast.error(HttpCodeEnum.ERROR.getMsg());
            Log.e(TAG,"",e);
        }
    }
    //完成
    public void complete(Integer wostId,Integer tiacId,String nextPrtyCode) {
        try {
            Map<String,Object> operator = new HashMap();
            operator.put("prtyCode", workstation.getPrtyCode());
            operator.put("tiacDoctor", user.getUserTrueName());
            operator.put("wostId", wostId);
            if(null != tiacId){
                operator.put("tiacId", tiacId);
            }
            if(nextPrtyCode!=null){
                operator.put("nextPrtyCode", nextPrtyCode);
            }
            mModel.complete(operator,new HttpTaskCallBack<Object>() {
                @Override
                public void onSuccess(Object data) {
                    mView.clearCallingTicket();
                    if(mView.getSwitchCallChecked()) {
                        callNumber(wostId,tiacId);
                    }
                    getQueueCount(wostId);
                }
                @Override
                public void onFail(Object data) {
                    getQueueCount(wostId);
                }
            });
        }catch (Exception e) {
            RxToast.error(HttpCodeEnum.ERROR.getMsg());
            Log.e(TAG,"",e);
        }
    }
}
