package com.cybermax.digitaloutpatient.activity.screen;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;

import com.cybermax.digitaloutpatient.R;
import com.cybermax.digitaloutpatient.adapter.InoculateWaitRvAdapter;
import com.cybermax.digitaloutpatient.bean.Ticket;
import com.cybermax.digitaloutpatient.bean.share.Workstation;
import com.cybermax.digitaloutpatient.constract.BigWaitScreenContract;
import com.cybermax.digitaloutpatient.databinding.ActivityInoculateWaitBinding;
import com.cybermax.digitaloutpatient.presenter.BigWaitScreenPresenter;
import com.cybermax.digitaloutpatient.tool.SharedPreferenceUtil;
import com.lib.tool.TimeInstance;

import java.util.ArrayList;
import java.util.List;

/**
 * 接种等待屏
 */
public class BigWaitScreenActivity extends BaseScreenActivity implements BigWaitScreenContract.View {
    ActivityInoculateWaitBinding binding;
    private BigWaitScreenPresenter presenter;
    private String TAG = "BigWaitScreenActivity";
    private Workstation workstation;
    private InoculateWaitRvAdapter mAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_inoculate_wait);
        workstation = (Workstation) SharedPreferenceUtil.getInstance().getObject(Workstation.class);
        presenter = new BigWaitScreenPresenter(this, this);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        binding.rv.setLayoutManager(layoutManager);
        /*binding.tv1.setText(workstation.getWostShowName());*/
        binding.tv2.setText(workstation.getWostShowName());
        binding.tv3.setText(new TimeInstance().getFormatTimeStr());
        registLisener(workstation);
        super.gestureCallback(binding.rv);
        mAdapter = new InoculateWaitRvAdapter(this);
        binding.rv.setAdapter(mAdapter);

      //  new InoculateTipDialog(this).show();
    }

    @Override
    public void setTime() {
        binding.tv3.setText(new TimeInstance().getFormatTimeStr());
    }

    //singleTop启动模式下，再次打开此activity时，直接执行onNewIntent
    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        refresh(workstation);
    }

    @Override
    public void refresh(Workstation workstation) {
        presenter.requestData(workstation);
    }

    @Override
    protected void onStart() {
        super.onStart();
        refresh(workstation);
    }

    @Override
    public void reloadMainView(List<Ticket> ticketList) {
        //浅拷贝
        List<Ticket>  copyList = new ArrayList<>(ticketList);
        if(copyList.size()<6){
            int blankcount = 6-copyList.size();
            for(int i=0;i<=blankcount;i++){
                Ticket blank = new Ticket();
                blank.setIsBlank(1);
                copyList.add(blank);
            }
        }
        mAdapter.setNewData(copyList);
    }

    @Override
    public void reloadPassedView(String str) {
        binding.tv10.setText(str);
    }

    @Override
    public void reloadWaitingView(String str) {
        binding.tv8.setText(str);
    }

    @Override
    public void reloadNoteMessage(List<String> stringList) {

    }

    @Override
    protected void onTimmer() {
      //  refresh(workstation);
    }
}
