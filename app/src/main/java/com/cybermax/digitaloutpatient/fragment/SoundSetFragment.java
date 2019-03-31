package com.cybermax.digitaloutpatient.fragment;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cybermax.digitaloutpatient.R;
import com.cybermax.digitaloutpatient.databinding.FragmentSoundSetBinding;
import com.lib.tool.DeviceTypeUtil;
import com.lib.views.bordereffect.BorderView;

public class SoundSetFragment extends Fragment implements View.OnClickListener {
    FragmentSoundSetBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_sound_set, container, false);
        View view;
        view = binding.getRoot();
        initViews();
        return view;
    }

    private void initViews() {
        if(DeviceTypeUtil.checkScreenIsTV(getActivity())){
            BorderView border = new BorderView(getActivity());
            border.setBackgroundResource(R.drawable.border_highlight);
            border.attachTo(binding.rootLayout);
        }
        binding.pretestCallNum.setOnClickListener(this);
        binding.registerCallNum.setOnClickListener(this);
        binding.inoculateCallNum.setOnClickListener(this);
        binding.physicalExamCallNum.setOnClickListener(this);
        binding.ENTCallNum.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.pretestCallNum:
                binding.switchPretestCallNum.setChecked(!binding.switchPretestCallNum.isChecked());
                break;
            case R.id.registerCallNum:
                binding.switchRegisterCallNum.setChecked(!binding.switchRegisterCallNum.isChecked());
                break;
            case R.id.inoculateCallNum:
                binding.switchInoculateCallNum.setChecked(!binding.switchInoculateCallNum.isChecked());
                break;
            case R.id.physicalExamCallNum:
                binding.switchPhysicalExamCallNum.setChecked(!binding.switchPhysicalExamCallNum.isChecked());
                break;
            case R.id.ENTCallNum:
                binding.switchENTCallNum.setChecked(!binding.switchENTCallNum.isChecked());
                break;
        }
    }
}