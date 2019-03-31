package com.cybermax.digitaloutpatient.fragment;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cybermax.digitaloutpatient.R;
import com.cybermax.digitaloutpatient.databinding.FragmentScreenShowSetBinding;
import com.lib.tool.DeviceTypeUtil;
import com.lib.views.bordereffect.BorderView;

public class ScreenSetFragment extends Fragment implements View.OnClickListener {
    FragmentScreenShowSetBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_screen_show_set, container, false);
        View view = binding.getRoot();
        initViews();
        return view;
    }

    private void initViews() {
        if(DeviceTypeUtil.checkScreenIsTV(getActivity())){
            BorderView border = new BorderView(getActivity());
            border.attachTo(binding.rootLayout);
        }
        binding.pretestScreenSet.setOnClickListener(this);
        binding.registerScreenSet.setOnClickListener(this);
        binding.inoculateScreenSet.setOnClickListener(this);
        binding.physicalExamScreenSet.setOnClickListener(this);
        binding.ENTScreenSet.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.pretestScreenSet:
                binding.switchPretestScreenSet.setChecked(!binding.switchPretestScreenSet.isChecked());
                break;
            case R.id.registerScreenSet:
                binding.switchRegisterScreenSet.setChecked(!binding.switchRegisterScreenSet.isChecked());
                break;
            case R.id.inoculateScreenSet:
                binding.switchInoculateScreenSet.setChecked(!binding.switchInoculateScreenSet.isChecked());
                break;
            case R.id.physicalExamScreenSet:
                binding.switchPhysicalExamScreenSet.setChecked(!binding.switchPhysicalExamScreenSet.isChecked());
                break;
            case R.id.ENTScreenSet:
                binding.switchENTScreenSet.setChecked(!binding.switchENTScreenSet.isChecked());
                break;
        }
    }
}