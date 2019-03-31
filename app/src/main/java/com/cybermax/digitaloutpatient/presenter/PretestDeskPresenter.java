package com.cybermax.digitaloutpatient.presenter;

import android.content.Context;

import com.cybermax.digitaloutpatient.bean.PretestHistory;
import com.cybermax.digitaloutpatient.bean.Question;
import com.cybermax.digitaloutpatient.bean.share.User;
import com.cybermax.digitaloutpatient.bean.share.Workstation;
import com.cybermax.digitaloutpatient.constract.PretestDeskContract;
import com.cybermax.digitaloutpatient.constract.PretestHistoryContract;
import com.cybermax.digitaloutpatient.model.PretestDeskModel;
import com.lib.http.HttpTaskCallBack;

import java.util.List;

/**
 *
 */
public class PretestDeskPresenter extends BasePresenter {
    private PretestDeskModel mModel;
    private PretestDeskContract.View mView;
    private Workstation workstation;
    private User user;
    public PretestDeskPresenter(Context context, PretestDeskContract.View view, Workstation workstation,User user) {
        super(context);
        mModel = new PretestDeskModel(context);
        mContext = context;
        mView = view;
        this.workstation = workstation;
        this.user = user;
    }

    public void listQuestions(Integer queType) {
        mModel.listQuestions(queType, new HttpTaskCallBack<List<Question>>() {
            @Override
            public void onSuccess(List<Question> questions) {
                mView.showQuestions(questions);
            }
        });
    }



}
