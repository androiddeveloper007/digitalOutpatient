package com.cybermax.digitaloutpatient.activity.screen;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;

import com.cybermax.digitaloutpatient.R;
import com.cybermax.digitaloutpatient.adapter.VaccinePublicityRvAdapter;
import com.cybermax.digitaloutpatient.bean.Ticket;
import com.cybermax.digitaloutpatient.bean.share.Workstation;
import com.cybermax.digitaloutpatient.constract.VaccinePublicityScreenContract;
import com.cybermax.digitaloutpatient.databinding.ActivityInoculateWaitBinding;
import com.cybermax.digitaloutpatient.presenter.BigWaitScreenPresenter;
import com.cybermax.digitaloutpatient.tool.SharedPreferenceUtil;
import com.lib.tool.TimeInstance;

import java.util.List;

/**
 * 疫苗公示屏
 */
public class VaccinePublicityScreenActivity extends BaseScreenActivity implements VaccinePublicityScreenContract.View {
    ActivityInoculateWaitBinding binding;
    private BigWaitScreenPresenter presenter;
    private String TAG = "VaccinePublicityScreenActivity";
    private Workstation workstation;
    private VaccinePublicityRvAdapter mAdapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_vaccine_publicity);
        workstation = (Workstation) SharedPreferenceUtil.getInstance().getObject(Workstation.class);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        binding.rv.setLayoutManager(layoutManager);
        /*binding.tv1.setText(workstation.getWostShowName());*/
        binding.tv2.setText(workstation.getWostShowName());
        binding.tv3.setText(new TimeInstance().getFormatTimeStr());
        registLisener(workstation);
        super.gestureCallback(binding.rv);
        mAdapter = new VaccinePublicityRvAdapter(this);
        binding.rv.setAdapter(mAdapter);
    }

    @Override
    public void setTime() {
        binding.tv3.setText(new TimeInstance().getFormatTimeStr());
    }

    //singleTop启动模式下，再次打开此activity时，直接执行onNewIntent
    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
    }


    @Override
    protected void onStart() {
        super.onStart();
        refresh(workstation);
    }

    @Override
    public void refresh(Workstation workstation) {

    }

    @Override
    public void reloadMainView(List<Ticket> ticketList) {

    }
}
