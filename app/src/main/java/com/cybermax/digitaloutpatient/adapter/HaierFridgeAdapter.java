package com.cybermax.digitaloutpatient.adapter;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.widget.Toast;

import com.bainuosoft.aidlInterface.IVimsAidlInterface;
import com.cybermax.digitaloutpatient.bean.FridgeInfo;
import com.cybermax.digitaloutpatient.constract.InoculateDeskContract;
import com.cybermax.digitaloutpatient.enums.FridgeTypeEnum;
import com.cybermax.digitaloutpatient.fragment.desk.InoculateDeskMainFragment;

/**
 * @author wurenqing
 * @description 海尔冰箱接口适配器
 */
public class HaierFridgeAdapter extends FridgeAdapter {

    private FragmentActivity fragmentActivity;

    private IVimsAidlInterface vimsAidlInterface;

    private String TAG = "HaierFridgeAdapter";



    public HaierFridgeAdapter(FragmentActivity fragmentActivity, IVimsAidlInterface vimsAidlInterface) {
        this.fragmentActivity = fragmentActivity;
        FridgeAdapter.fridgeType = FridgeTypeEnum.HAIER.getValue();
    }

    private void initService() {
        try {
            Intent bindIntent = new Intent();
            bindIntent.setComponent(new ComponentName("com.bainuosoft.vims.activitys","com.bainuosoft.vims.service.MyService"));
            fragmentActivity.bindService(bindIntent, new ServiceConnection() {
                public void onServiceConnected(ComponentName className, IBinder service) {
                    vimsAidlInterface = com.bainuosoft.aidlInterface.IVimsAidlInterface.Stub.asInterface(service);
                    fridgeType = FridgeTypeEnum.HAIER.getValue();
                }

                public void onServiceDisconnected(ComponentName className) {
                    vimsAidlInterface = null;
                }
            }, fragmentActivity.BIND_AUTO_CREATE);
        } catch (Exception ex) {
            Log.e(TAG, "冰箱服务初始化失败",ex);
        }
//        Bundle bundle =fragmentActivity.getAC.getArguments();//得到从Activity传来的数据
////        fridgeInfo = new FridgeInfo();
////        fridgeInfo.setDeviceId("1");
////        fridgeInfo.setDeviceType("2");
////        fridgeInfo.setEmpId("1");
////        fridgeInfo.setCorpId("1001");
////        fridgeInfo.setIceBoxNm("1");
////        fridgeInfo.setUserName("qing");
////        fridgeInfo.setKey("42E14AFC550249019AD2684BE95C45E2E40F42C4D08BCF75B81F8B341672852E2E47959D2626CEC394D2202F962FEACB269C48A4CB702EECEEC7733144819CD3620EF740E1549D4A958AACD223AFB6B1E6BB73EE2D38DECEC3C46D654B3B12B9");
////
//        if(bundle!=null){
//            fridgeInfo = new FridgeInfo();
//            fridgeInfo.setDeviceId(bundle.getString("deviceId"));
//            fridgeInfo.setDeviceType(bundle.getString("deviceType"));
//            fridgeInfo.setEmpId(bundle.getString("empId"));
//            fridgeInfo.setCorpId(bundle.getString("corpId"));
//            fridgeInfo.setIceBoxNm(bundle.getString("iceBoxNm"));
//            fridgeInfo.setUserName(bundle.getString("userName"));
//            fridgeInfo.setKey(bundle.getString("key"));
//        }
    }


    @Override
    public void openDoor() {
    }

    @Override
    public void scanChildBarCode() {

    }

    @Override
    public void scanEMCode() {

    }
}
