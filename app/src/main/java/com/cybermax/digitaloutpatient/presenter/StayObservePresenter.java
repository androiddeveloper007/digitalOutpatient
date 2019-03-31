package com.cybermax.digitaloutpatient.presenter;

import android.content.Context;

import com.cybermax.digitaloutpatient.bean.StayObserveScreenBean;
import com.cybermax.digitaloutpatient.constract.StayObserveScreenContract;
import com.cybermax.digitaloutpatient.model.ScreemModel;
import com.lib.http.HttpTaskCallBack;

import java.util.List;


public class StayObservePresenter extends BasePresenter {

    private ScreemModel mModel;
    private StayObserveScreenContract.View mView;

    public StayObservePresenter(Context context, StayObserveScreenContract.View view) {
        super(context);
        mModel = new ScreemModel(context);
        mContext = context;
        mView = view;
    }

    //留观大屏查询接口
    public void listObservers() {
        mModel.listObservers(new HttpTaskCallBack<List<StayObserveScreenBean>>() {
            @Override
            public void onSuccess(List<StayObserveScreenBean> object) {
                mView.reloadMainView(object);
            }

            @Override
            public void onFail(String str) {

            }
        });
    }
}
