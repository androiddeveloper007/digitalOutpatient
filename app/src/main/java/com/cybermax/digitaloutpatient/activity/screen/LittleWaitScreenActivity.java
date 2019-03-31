package com.cybermax.digitaloutpatient.activity.screen;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.TypedValue;

import com.cybermax.digitaloutpatient.R;
import com.cybermax.digitaloutpatient.bean.Ticket;
import com.cybermax.digitaloutpatient.bean.share.Workstation;
import com.cybermax.digitaloutpatient.constract.CommonContract;
import com.cybermax.digitaloutpatient.databinding.ActivityInoculateWaitLittleBinding;
import com.cybermax.digitaloutpatient.netty.MessageDTO;
import com.cybermax.digitaloutpatient.presenter.LittleWaitScreenPresenter;
import com.cybermax.digitaloutpatient.tool.SharedPreferenceUtil;
import com.lib.tool.TimeInstance;

/**
 * 接种等待小屏
 */
public class LittleWaitScreenActivity extends BaseScreenActivity implements CommonContract.View<Ticket>{
    private ActivityInoculateWaitLittleBinding binding;
    private LittleWaitScreenPresenter littleWaitScreenPresenter;
    private String TAG = "LittleWaitScreenActivity";
    private  Workstation workstation;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_inoculate_wait_little);
        workstation = (Workstation) SharedPreferenceUtil.getInstance().getObject(Workstation.class);
        littleWaitScreenPresenter = new LittleWaitScreenPresenter(this,this);
        binding.wostShowName.setText(workstation.getWostShowName());
        binding.timeTv.setText(new TimeInstance().getFormatTimeStr());
        super.gestureCallback(binding.rootLayout);
        registLisener(workstation);
    }

    @Override
    public void setTime() {
        binding.timeTv.setText(new TimeInstance().getFormatTimeStr());
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        refresh(workstation);
    }

    @Override
    public void onServerMessage(Workstation workstation, MessageDTO messageDTO) {
       //获取当天票号的ID
        playVoice(messageDTO);
        Integer wskId =  messageDTO.getWskid();
        if(wskId!=null&&wskId.equals(workstation.getWostRelationId())){
            littleWaitScreenPresenter.getCallingTicket(workstation.getWostRelationId());
        }
        // 获取工作台呼叫的儿童
    }

    private void reloadData(String chilName,String disPlayNo){
        if(TextUtils.isEmpty(chilName))
            binding.childNameText.setText("");
        else{
            if(chilName.length()>12){
                binding.childNameText.setTextSize(TypedValue.COMPLEX_UNIT_SP, 60);
            }else if(chilName.length()>8){
                binding.childNameText.setTextSize(TypedValue.COMPLEX_UNIT_SP, 70);
            }else if(chilName.length()>3){
                binding.childNameText.setTextSize(TypedValue.COMPLEX_UNIT_SP, 90);
            }
            binding.childNameText.setText(chilName);
        }

        if(TextUtils.isEmpty(disPlayNo))
            binding.ticketDisplayNoText.setText("");
        else
            binding.ticketDisplayNoText.setText(disPlayNo);
    }


    @Override
    public void onLoadSuccess(Ticket object) {
        if(object!=null){
            reloadData(object.getChilName(),object.getTicketDisplayNo());
        }else {
            reloadData("","");
        }
    }

    @Override
    public void onLoadFail(String str) {

    }

    @Override
    protected void onStart() {
        super.onStart();
        littleWaitScreenPresenter.getCallingTicket( workstation.getWostRelationId() );
    }

    @Override
    public void  refresh(Workstation workstation) {
        littleWaitScreenPresenter.getCallingTicket( workstation.getWostRelationId() );
    }

    @Override
    protected void onTimmer() {
      //  refresh(workstation);
    }
}
