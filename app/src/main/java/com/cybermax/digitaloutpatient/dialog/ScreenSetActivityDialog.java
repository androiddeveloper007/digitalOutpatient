package com.cybermax.digitaloutpatient.dialog;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.cybermax.digitaloutpatient.R;
import com.cybermax.digitaloutpatient.activity.SplashActivity;
import com.cybermax.digitaloutpatient.bean.share.User;
import com.cybermax.digitaloutpatient.databinding.FragmentInoculateSet2Binding;
import com.cybermax.digitaloutpatient.fragment.ScreenSetFragment;
import com.cybermax.digitaloutpatient.fragment.SoundSetFragment;
import com.cybermax.digitaloutpatient.fragment.SystemParamSetFragment;
import com.cybermax.digitaloutpatient.fragment.WorkstationSetFragment;
import com.cybermax.digitaloutpatient.tool.SharedPreferenceUtil;
import com.cybermax.digitaloutpatient.tool.SwitchFragmentModel;
import com.lib.views.bordereffect.BorderView;

import java.util.Objects;

public class ScreenSetActivityDialog extends AppCompatActivity implements View.OnClickListener {
    FragmentInoculateSet2Binding binding;
    private SwitchFragmentModel switchFragmentModel;
    private SharedPreferenceUtil sp;
    private Fragment baseFragment;
    private SoundSetFragment soundSetFragment;
    private ScreenSetFragment screenSetFragment;
    private WorkstationSetFragment workstationSetFragment;
    private SystemParamSetFragment systemParamSetFragment;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.fragment_inoculate_set2);

        BorderView border = new BorderView(this);
        border.setBackgroundResource(R.drawable.border_highlight);
        border.attachTo(binding.rootLayout);

        binding.logout.setOnClickListener(this);
        binding.soundSet.setOnClickListener(this);
        binding.screenShowSet.setOnClickListener(this);
        binding.workstationSet.setOnClickListener(this);
        binding.systemParamSet.setOnClickListener(this);
        switchFragmentModel = new SwitchFragmentModel(this);
    }

    @Override
    public void onClick(View view) {
        if (sp == null) sp = SharedPreferenceUtil.getInstance();
        switch (view.getId()) {
            case R.id.logout:
                SharedPreferenceUtil.getInstance().removeObject(User.class);
                startActivity(new Intent(this, SplashActivity.class));
                Objects.requireNonNull(this).finish();
                break;
            case R.id.soundSet:
                if (soundSetFragment == null)
                    soundSetFragment = new SoundSetFragment();
                baseFragment = soundSetFragment;
                switchFragmentModel.add(baseFragment, R.id.setFragmentLayout);
                break;
            case R.id.screenShowSet:
                if (screenSetFragment == null)
                    screenSetFragment = new ScreenSetFragment();
                baseFragment = screenSetFragment;
                switchFragmentModel.add(baseFragment, R.id.setFragmentLayout);
                break;
            case R.id.workstationSet:
                if (workstationSetFragment == null)
                    workstationSetFragment = new WorkstationSetFragment();
                baseFragment = workstationSetFragment;
                switchFragmentModel.add(baseFragment, R.id.setFragmentLayout);
                break;
            case R.id.systemParamSet:
                if (systemParamSetFragment == null)
                    systemParamSetFragment = new SystemParamSetFragment();
                baseFragment = systemParamSetFragment;
                switchFragmentModel.add(baseFragment, R.id.setFragmentLayout);
                break;
        }
    }
}
