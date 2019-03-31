package com.cybermax.digitaloutpatient.activity.screen;

import android.annotation.SuppressLint;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;

import com.cybermax.digitaloutpatient.MyApplication;
import com.cybermax.digitaloutpatient.activity.SplashActivity;
import com.cybermax.digitaloutpatient.bean.share.Workstation;
import com.cybermax.digitaloutpatient.dialog.DialogDismissCallBack;
import com.cybermax.digitaloutpatient.dialog.ScreenSetDialog;
import com.cybermax.digitaloutpatient.dialog.SwitchScreenDialog;
import com.cybermax.digitaloutpatient.enums.MessageTypeEnum;
import com.cybermax.digitaloutpatient.enums.WorkStationTypeEnum;
import com.cybermax.digitaloutpatient.netty.MessageDTO;
import com.cybermax.digitaloutpatient.netty.MessageLisener;
import com.cybermax.digitaloutpatient.netty.NettyClientHandler;
import com.cybermax.digitaloutpatient.tool.SharedPreferenceUtil;

public abstract class BaseScreenActivity extends AppCompatActivity implements Screen  {
    private    MessageLisener   messageLisener;

    private Handler  handler=new Handler();
    private   long  delay = 30*1000;


    Runnable runnable=new Runnable() {
        @Override
        public void run() {
            // TODO Auto-generated method stub
            //要做的事情
            onTimmer();
            handler.postDelayed(this, delay);
        }
    };

    protected  void onTimmer(){
        Log.e(BaseScreenActivity.class.getName(),"定时器触发！！！！");
    };

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        registerReceiver(mTimeRefreshReceiver, new IntentFilter(Intent.ACTION_TIME_TICK));
        handler.postDelayed(runnable, delay);
    }

    /*注册监听器*/
    @Override
    public void registLisener( final  Workstation workstation) {
         messageLisener = new MessageLisener() {
            @Override
            public void onMessage(MessageDTO message) {
                onServerMessage(workstation,message);
            }
        };
        NettyClientHandler.registLisener(messageLisener);
    }

    private BroadcastReceiver mTimeRefreshReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            if (Intent.ACTION_TIME_TICK.equals(intent.getAction())) {
                setTime();
            }
        }
    };

    public abstract void setTime();

    @Override
    protected void onStart() {
        super.onStart();
    }

    /**
     * 切换屏幕
     */
    @Override
    public void switchScreen() {
        SwitchScreenDialog dialog = new SwitchScreenDialog(this, new DialogDismissCallBack() {
            @Override
            public void onDialogDismis(Object obj) {
                //判断是否是设置项如果是设置项 make toast
                Workstation  workstation = (Workstation) obj;
                //如果不是就切换屏幕
                onScreemChoose(workstation);
            }
        });
        dialog.show();
    }

    @Override
    public void onServerMessage( Workstation workstation ,MessageDTO messageDTO) {
        String prtyCode = workstation.getPrtyCode();
        if(MessageTypeEnum.MEDIA.getValue()==messageDTO.getMsgtype()){
            playVoice(messageDTO);
        }
        if(prtyCode.equals(messageDTO.getPrtyCode())||
                WorkStationTypeEnum.ZONGHEPING.getValue().equals(workstation.getWstyCode())){
            //刷新屏幕
            refresh(workstation);
        }
    }

    protected  void playVoice(MessageDTO messageDTO){
        if(MessageTypeEnum.MEDIA.getValue()==messageDTO.getMsgtype()){
            String msg= messageDTO.getMediavoice();
            //输出语音
            MyApplication.getOffline().stop();
            MyApplication.getOffline().play(msg);
        }
    }

    private  void onScreemChoose( Workstation  workstation ){
        if(!TextUtils.equals("设置",workstation.getWostShowName())){
            SharedPreferenceUtil.getInstance().putObject(workstation);
            startActivity(new Intent(this, SplashActivity.class));
            finish();
        }else {
//            Toast.makeText(this, "设置暂时不支持", Toast.LENGTH_SHORT).show();
//            startActivity(new Intent(this, ScreenSetActivityDialog.class));
            ScreenSetDialog d = new ScreenSetDialog(this);
            d.show();
        }
    }

    private float startX, endX;
    @SuppressLint("ClickableViewAccessibility")
    protected void gestureCallback(View view) {
       view.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        startX = event.getX();
                        break;
                    case MotionEvent.ACTION_UP:
                        endX = event.getX();
                        if (startX - endX > 200) {
                            switchScreen();
                        }
                        break;
                }
                return false;
            }
        });
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_MENU && event.getAction() == KeyEvent.ACTION_DOWN) {// 按下遥控器menu键
            switchScreen();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(mTimeRefreshReceiver);
    }
}
