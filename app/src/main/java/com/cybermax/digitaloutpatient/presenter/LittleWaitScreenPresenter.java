package com.cybermax.digitaloutpatient.presenter;

import android.content.Context;

import com.cybermax.digitaloutpatient.bean.Ticket;
import com.cybermax.digitaloutpatient.constract.CommonContract;
import com.cybermax.digitaloutpatient.model.ScreemModel;
import com.lib.http.HttpTaskCallBack;


public class LittleWaitScreenPresenter extends BasePresenter {

    private ScreemModel mModel;
    private CommonContract.View mView;

    public LittleWaitScreenPresenter(Context context, CommonContract.View view) {
        super(context);
        mModel = new ScreemModel(context);
        mContext = context;
        mView = view;
    }

    public void getCallingTicket(Integer wostId) {
        mModel.getCallingTicket(wostId, new HttpTaskCallBack<Ticket>() {
            @Override
            public void onSuccess(Ticket object) {
                    mView.onLoadSuccess(object);
            }

            @Override
            public void onFail(String str) {

            }
        });
    }

}
