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
import com.cybermax.digitaloutpatient.enums.HttpCodeEnum;
import com.cybermax.digitaloutpatient.model.ChildModel;
import com.cybermax.digitaloutpatient.model.TicketModel;
import com.lib.http.HttpTaskCallBack;
import com.lib.tool.RxToast;

import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class ChildPresenter extends BasePresenter {

    private ChildModel mModel;
    private BaseWorkstationContract.ChildView mView;
    private Workstation workstation;
    private User user;
    private String TAG = "InoculateDeskPresenter";

    public ChildPresenter(Context context, BaseWorkstationContract.ChildView mView , Workstation workstation, User user ) {
        super(context);
        this.mView = mView;
        mModel = new ChildModel(context);
        this.workstation = workstation;
        this.user = user;
    }


    //绑定儿童
    public void bindChild(Integer ticketId,String chilNo,String chilCardNo, Integer wostId) {
        try {
            mModel.bindChild(ticketId, chilNo, chilCardNo,wostId,new HttpTaskCallBack<CallNumber>() {
                @Override
                public void onSuccess(CallNumber data) {
                    mView.showBindTicket(data);
                }
                @Override
                public void onFail(CallNumber data) {
                    mView.stopScan(data);
                }
            });
        }catch (Exception e) {
            RxToast.error(HttpCodeEnum.ERROR.getMsg());
            Log.e(TAG,"",e);
        }
    }


    //显示儿童列表
    public void listChildren(String keywords) {
        try {
            mModel.listChildren(keywords,new HttpTaskCallBack<List<Child>>() {
                @Override
                public void onSuccess(List<Child> data) {
                    mView.showChildren(data);
                }
                @Override
                public void onFail(List<Child> data) {
                    mView.showChildren(data);
                }
            });
        }catch (Exception e) {
            RxToast.error(HttpCodeEnum.ERROR.getMsg());
            Log.e(TAG,"",e);
        }
    }
//
//
//    public void decryptBarCode(String barCode) {
//        try {
//            mModel.decryptBarCode(barCode,new HttpTaskCallBack<String>() {
//                @Override
//                public void onSuccess(String data) {
//                    //绑定儿童
//                    bindChild();
//                }
//
//                @Override
//                public void onFail(String data) {
//                    RxToast.error("无法解析条码");
//                }
//            });
//        }catch (Exception e) {
//            RxToast.error(HttpCodeEnum.ERROR.getMsg());
//            Log.e(TAG,"",e);
//        }
//    }



}
