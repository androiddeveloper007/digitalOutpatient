package com.cybermax.digitaloutpatient.fragment;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cybermax.digitaloutpatient.R;
import com.cybermax.digitaloutpatient.activity.PickWorkstationActivity;
import com.cybermax.digitaloutpatient.activity.SplashActivity;
import com.cybermax.digitaloutpatient.bean.share.User;
import com.cybermax.digitaloutpatient.bean.share.Workstation;
import com.cybermax.digitaloutpatient.constant.Constant;
import com.cybermax.digitaloutpatient.databinding.FragmentInoculateSet1Binding;
import com.cybermax.digitaloutpatient.tool.SharedPreferenceUtil;
import com.cybermax.digitaloutpatient.tool.SwitchFragmentModel;
import com.lib.dialog.AffirmDialog;

import java.util.Objects;

/**
 * 设置fragment
 */
public class SetFragment extends Fragment implements View.OnClickListener {
    FragmentInoculateSet1Binding binding;
    private SharedPreferenceUtil sp;
    private SwitchFragmentModel switchFragmentModel;
    private Fragment baseFragment;
    private SoundSetFragment soundSetFragment;
    private ScreenSetFragment screenSetFragment;
    private WorkstationSetFragment workstationSetFragment;
    private SystemParamSetFragment systemParamSetFragment;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_inoculate_set1, container, false);
        View view = binding.getRoot();
        initViews();
        return view;
    }

    private void initViews() {
        binding.logout.setOnClickListener(this);
        binding.soundSet.setOnClickListener(this);
        binding.screenShowSet.setOnClickListener(this);
        binding.workstationSet.setOnClickListener(this);
        binding.systemParamSet.setOnClickListener(this);
        switchFragmentModel = new SwitchFragmentModel(getActivity());
        setSoundLayoutSelected();
    }

    @Override
    public void onClick(View view) {
        if (sp == null) sp = SharedPreferenceUtil.getInstance();
        switch (view.getId()) {
            case R.id.soundSet:
                setSoundLayoutSelected();
                break;
            case R.id.screenShowSet:
                if(screenSetFragment==null)
                    screenSetFragment = new ScreenSetFragment();
                baseFragment = screenSetFragment;
                switchFragmentModel.add(baseFragment, R.id.setFragmentLayout);
                setLayoutSelectStatus(1);
                break;
            case R.id.workstationSet:
                if(workstationSetFragment==null)
                    workstationSetFragment = new WorkstationSetFragment();
                baseFragment = workstationSetFragment;
                switchFragmentModel.add(baseFragment, R.id.setFragmentLayout);
                setLayoutSelectStatus(2);
                break;
            case R.id.systemParamSet:
                if(systemParamSetFragment==null)
                    systemParamSetFragment = new SystemParamSetFragment();
                baseFragment = systemParamSetFragment;
                switchFragmentModel.add(baseFragment, R.id.setFragmentLayout);
                setLayoutSelectStatus(3);
                break;
            case R.id.logout:
                AffirmDialog dialog = new AffirmDialog(getContext());
                dialog.setmContentStr("确认退出登录？");
                dialog.setAffirmDialogListenner(new AffirmDialog.AffirmDialogListenner() {
                    @Override
                    public void onCancle() {

                    }

                    @Override
                    public void onOK() {
                        SharedPreferenceUtil.getInstance().removeObject(User.class);
                        SharedPreferenceUtil.getInstance().removeObject(Workstation.class);
                        startActivity(new Intent(getActivity(), SplashActivity.class));
                        Objects.requireNonNull(getActivity()).finish();
                    }
                });
                dialog.show();
                break;
        }
    }

    private void setSoundLayoutSelected() {
        setLayoutSelectStatus(0);
        if(soundSetFragment==null)
            soundSetFragment = new SoundSetFragment();
        baseFragment = soundSetFragment;
        switchFragmentModel.add(baseFragment, R.id.setFragmentLayout);
    }

    private void setLayoutSelectStatus(int i){
        binding.soundSet.setSelected(i==0?true:false);
        binding.screenShowSet.setSelected(i==1?true:false);
        binding.workstationSet.setSelected(i==2?true:false);
        binding.systemParamSet.setSelected(i==3?true:false);
        String str = "";
        switch (i){
            case 0:str = "语音设置";break;
            case 1:str = "屏幕显示设置";break;
            case 2:str = "工作台设置";break;
            case 3:str = "系统参数";break;
        }
        binding.fgTitle.setText(str);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        sp = null;
    }
}