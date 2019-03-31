package com.cybermax.digitaloutpatient.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.cybermax.digitaloutpatient.activity.screen.BigWaitScreenActivity;
import com.cybermax.digitaloutpatient.activity.screen.LittleWaitScreenActivity;
import com.cybermax.digitaloutpatient.activity.screen.StayObserveWaitScreenActivity;
import com.cybermax.digitaloutpatient.activity.workstation.ChargeDeskActivity;
import com.cybermax.digitaloutpatient.activity.workstation.GetNumberPaperActivity;
import com.cybermax.digitaloutpatient.activity.workstation.InoculateWorkstationActivity;
import com.cybermax.digitaloutpatient.activity.workstation.PhysicalExamDeskActivity;
import com.cybermax.digitaloutpatient.activity.workstation.PretestDeskActivity;
import com.cybermax.digitaloutpatient.activity.workstation.RegisterDeskActivity;
import com.cybermax.digitaloutpatient.bean.share.ServerInfo;
import com.cybermax.digitaloutpatient.bean.share.User;
import com.cybermax.digitaloutpatient.bean.share.Workstation;
import com.cybermax.digitaloutpatient.enums.DeviceTypeEnum;
import com.cybermax.digitaloutpatient.enums.ProcdureEnum;
import com.cybermax.digitaloutpatient.enums.WorkStationTypeEnum;
import com.cybermax.digitaloutpatient.netty.service.NetService;
import com.cybermax.digitaloutpatient.presenter.BigWaitScreenPresenter;
import com.cybermax.digitaloutpatient.tool.SharedPreferenceUtil;
import com.lib.tool.DeviceTypeUtil;

import java.util.Objects;

/**
 * 启动页
 */
public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        startSwitch();
    }

    private static final Integer SERVERLOGIN = 1;
    private static final Integer PICKETWORKSTATION = 2;
    private static final Integer DOCTORLOGIN = 3;

    private void startSwitch() {
        //检查服务器是否能够连接通，获取服务器时间
        ServerInfo serverInfo = (ServerInfo) SharedPreferenceUtil.getInstance().getObject(ServerInfo.class);

        if (serverInfo == null) {
            //跳转到IP输入的页面
            Intent intent = new Intent();
            intent.setClass(this, ServerLoginActivity.class);
            startActivityForResult(intent, SERVERLOGIN);
            return;
        }
        //判断设备类型
         boolean  isOutputDevice = DeviceTypeUtil.checkScreenIsTV(this);
      //  boolean isOutputDevice = true;

        //判断设备类型
        if (isOutputDevice) {
            //如果是输出设备不需要登录
            SharedPreferenceUtil.getInstance().putDeviceType(DeviceTypeEnum.OUTPUT.getValue());
        } else {
            //如果是输出设备不需要登录
            SharedPreferenceUtil.getInstance().putDeviceType(DeviceTypeEnum.DOCTOR_WORKSTATION.getValue());
            User user = (User) SharedPreferenceUtil.getInstance().getObject(User.class);
            if (user == null) {
                //跳转到登录页面进行登录
                Intent intent = new Intent();
                intent.setClass(this, DoctorLoginActivity.class);
                startActivityForResult(intent, DOCTORLOGIN);
                return;
            }
        }

        //获取当前的工作台
        Workstation workstation = (Workstation) SharedPreferenceUtil.getInstance().getObject(Workstation.class);
        if (workstation == null) {
            Intent intent = new Intent();
            intent.setClass(this, PickWorkstationActivity.class);
            startActivityForResult(intent, PICKETWORKSTATION);
            return;
        }
        //获取服务器时间，检查服务器是否能够连接通，如果连接不通，给出错误提示， 不断尝试重新连接
        switchToWorkstation(workstation.getPrtyCode(), workstation.getWstyCode());
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case 1:
                startSwitch();
                break;
            case 2:
                startSwitch();
                break;
            case 3:
                startSwitch();
                break;
            default:
        }
    }

    /**
     * @param prtyCode        流程编码
     * @param workstationType "工作台类型";
     */
    private void switchToWorkstation(String prtyCode, String workstationType) {
        Intent intent = new Intent();
        if (WorkStationTypeEnum.QUHAOJI.getValue().equals(workstationType)) {
            //取号
            intent.setClass(this, GetNumberPaperActivity.class);

        } else if (WorkStationTypeEnum.GONGZUOTAI.getValue().equals(workstationType)) {
            //工作台

            if (ProcdureEnum.YUJIAN.getValue().equals(prtyCode)) {
                //跳转到预检台
                intent.setClass(this, PretestDeskActivity.class);
            } else if (ProcdureEnum.DENGJI.getValue().equals(prtyCode)) {
                intent.setClass(this, RegisterDeskActivity.class);
            } else if (ProcdureEnum.SHOUFEI.getValue().equals(prtyCode)) {
                intent.setClass(this, ChargeDeskActivity.class);
            } else if (ProcdureEnum.JIEZHONG.getValue().equals(prtyCode)) {
                if(getIntent()!=null && getIntent().hasExtra("iceBoxNm")){
                    intent.putExtras(Objects.requireNonNull(getIntent().getExtras()));
                }
                intent.setClass(this, InoculateWorkstationActivity.class);
            } else if (ProcdureEnum.TIJIAN.getValue().equals(prtyCode)) {
                intent.setClass(this, PhysicalExamDeskActivity.class);
            } else {
                //不支持的工作台类型
                return;
            }
        } else if (WorkStationTypeEnum.DAPING.getValue().equals(workstationType)) {
            //大屏
            if (ProcdureEnum.LIUGUAN.getValue().equals(prtyCode)) {
                intent.setClass(this, StayObserveWaitScreenActivity.class);
            } else {
                intent.setClass(this, BigWaitScreenActivity.class);
            }
        } else if (WorkStationTypeEnum.XIAOPING.getValue().equals(workstationType)) {
            //小屏幕
            intent.setClass(this, LittleWaitScreenActivity.class);
        } else if (WorkStationTypeEnum.ZONGHEPING.getValue().equals(workstationType)) {
           //综合大屏
            intent.setClass(this, BigWaitScreenActivity.class);
        } else if (WorkStationTypeEnum.LIUGUANJI.getValue().equals(workstationType)) {
            //留观机
            intent.setClass(this, StayObserveWaitScreenActivity.class);
        } else if (WorkStationTypeEnum.YINXIANG.getValue().equals(workstationType)) {

        } else {
            //不支持的设备类型， 提示业务异常
            return;
        }

        startActivity(intent);
        Intent netIntent = new Intent(SplashActivity.this, NetService.class);
        stopService(netIntent);
        startService(netIntent);
        finish();
    }
}
