package com.cybermax.digitaloutpatient.presenter;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.cybermax.digitaloutpatient.bean.FridgeInfo;
import com.cybermax.digitaloutpatient.bean.Inoculation;
import com.cybermax.digitaloutpatient.bean.Operator;
import com.cybermax.digitaloutpatient.bean.VacccrkBarcode;
import com.cybermax.digitaloutpatient.bean.share.User;
import com.cybermax.digitaloutpatient.bean.share.Workstation;
import com.cybermax.digitaloutpatient.constract.InoculateDeskContract;
import com.cybermax.digitaloutpatient.enums.HttpCodeEnum;
import com.cybermax.digitaloutpatient.model.FridgeApiModel;
import com.cybermax.digitaloutpatient.model.TicketModel;
import com.lib.http.HttpTaskCallBack;
import com.lib.tool.ClickFilter;
import com.lib.tool.RxToast;
import java.util.HashMap;
import java.util.Map;


public class InoculateDeskPresenter extends TicketPresenter {

    private TicketModel mModel;
    private FridgeApiModel fridgeApiModel;
    private InoculateDeskContract.View mView;
    private InoculateDeskContract.DialogView dialogView;
    private Workstation workstation;
    private User user;
    private String TAG = "InoculateDeskPresenter";
    private com.bainuosoft.aidlInterface.IVimsAidlInterface vi;

    public InoculateDeskContract.DialogView getDialogView() {
        return dialogView;
    }

    public void setDialogView(InoculateDeskContract.DialogView dialogView) {
        this.dialogView = dialogView;
    }

    public InoculateDeskPresenter(Context context, InoculateDeskContract.View mView , Workstation workstation, User user ) {
        super(context, mView, workstation, user);
        this.mView = mView;
        mModel = new TicketModel(context);
        fridgeApiModel = new FridgeApiModel(context);
        this.workstation = workstation;
        this.user = user;
    }

    //确认接种
    public void confirmSingleInoc(Inoculation inoculation) {
        inoculation.setInstInocDoctor(user.getUserTrueName());
        try {
            mModel.confirmSingleInoc(inoculation,new HttpTaskCallBack<Inoculation>() {
                @Override
                public void onSuccess(Inoculation inoculation) {
                    // 修改接种状态
                    mView.refreshInoculations(inoculation,true);
                }

                @Override
                public void onFail(Inoculation inoculations) {

                }
            });
        }catch (Exception e) {
            RxToast.error(HttpCodeEnum.ERROR.getMsg());
            Log.e(TAG,"",e);
        }

    }



    //取消接种
    public void cancel(Inoculation inoculation) {
        inoculation.setInstInocDoctor(user.getUserTrueName());
        try {
            if(ClickFilter.isMultiClick()) return;
            mModel.cancel(inoculation,new HttpTaskCallBack<Inoculation>() {
                @Override
                public void onSuccess(Inoculation inoculation) {
                    // 修改接种状态
                    mView.refreshInoculations(inoculation,false);
                }

                @Override
                public void onFail(Inoculation inoculations) {

                }
            });
        }catch (Exception e) {
            RxToast.error(HttpCodeEnum.ERROR.getMsg());
            Log.e(TAG,"",e);
        }
    }

    public void scanVaccineBarCode(Inoculation inoculation,String code) {
        Map<String,Object> params = new HashMap<>();
//        params.put("barCode", "12345");
//        params.put("bactCode","12");
//        params.put("corpCode", "12");
//        params.put("batchNo", "123");
        params.put("barCode", code);
        params.put("bactCode",inoculation.getBactCode());
        params.put("corpCode", inoculation.getInstCorporation());
        params.put("batchNo", inoculation.getInstBatchNo());
        FridgeInfo fridgeInfo = inoculation.getFridgeInfo();
        params.put("key", fridgeInfo.getKey());
        params.put("empId", fridgeInfo.getEmpId());
        params.put("deviceType",fridgeInfo.getDeviceType());
        params.put("corpId",fridgeInfo.getCorpId());
        params.put("deviceId",fridgeInfo.getDeviceId());

        fridgeApiModel.scanVaccineBarCode(params,new HttpTaskCallBack<VacccrkBarcode>() {
            @Override
            public void onSuccess(VacccrkBarcode data) {
                dialogView.validateCode(data);
            }

            @Override
            public void onFail(String str) {
                dialogView.verifyFailed(str);
            }
        });
    }
}
