package com.cybermax.digitaloutpatient.presenter;

import android.content.Context;

import com.cybermax.digitaloutpatient.bean.PretestHistory;
import com.cybermax.digitaloutpatient.bean.share.User;
import com.cybermax.digitaloutpatient.constract.DoctorLoginContract;
import com.cybermax.digitaloutpatient.constract.PretestHistoryContract;
import com.cybermax.digitaloutpatient.model.PretestDeskModel;
import com.cybermax.digitaloutpatient.model.SystemLoginModel;
import com.cybermax.digitaloutpatient.tool.SharedPreferenceUtil;
import com.lib.http.HttpTaskCallBack;
import com.lib.util.EmptyUtils;

/**
 * 预检历史presenter
 */
public class PretestHistoryPresenter extends BasePresenter {
    private PretestDeskModel mModel;
    private PretestHistoryContract.View mView;

    public PretestHistoryPresenter(Context context, PretestHistoryContract.View view) {
        super(context);
        mModel = new PretestDeskModel(context);
        mContext = context;
        mView = view;
    }

    public void pretestHistory(Integer pageNumber, Integer pageSize,
                      String keyword, Integer wostId) {
        mModel.pretestHistory(pageNumber, pageSize,
                keyword, wostId, new HttpTaskCallBack<PretestHistory>() {
            @Override
            public void onSuccess(PretestHistory bean) {
                mView.onLoadSuccess(bean);
            }
            @Override
            public void onFail(String result) {
                mView.makeShortToast("登录失败，未知异常");
            }
        });
    }
}
