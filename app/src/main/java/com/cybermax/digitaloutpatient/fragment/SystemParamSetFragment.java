package com.cybermax.digitaloutpatient.fragment;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cybermax.digitaloutpatient.R;
import com.cybermax.digitaloutpatient.activity.SplashActivity;
import com.cybermax.digitaloutpatient.bean.share.ServerInfo;
import com.cybermax.digitaloutpatient.databinding.FragmentSysParamSetBinding;
import com.cybermax.digitaloutpatient.tool.SharedPreferenceUtil;
import com.lib.dialog.AffirmDialog;
import com.lib.tool.DeviceTypeUtil;
import com.lib.views.bordereffect.BorderView;

import java.util.Objects;

public class SystemParamSetFragment extends Fragment {
    FragmentSysParamSetBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_sys_param_set, container, false);
        View view = binding.getRoot();
        initViews();
        return view;
    }

    private void initViews() {
        if(DeviceTypeUtil.checkScreenIsTV(getActivity())){
            BorderView border = new BorderView(getActivity());
            border.attachTo(binding.rootLayout);
        }
        ServerInfo info = (ServerInfo) SharedPreferenceUtil.getInstance().getObject(ServerInfo.class);
        binding.serverIpText.setText(info.getIp());
        binding.portText.setText(info.getPort());
        binding.connectStateText.setText("连接正常");
        binding.confirmClean.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AffirmDialog dialog = new AffirmDialog(getContext());
                dialog.setmContentStr("确认清除数据？");
                dialog.setAffirmDialogListenner(new AffirmDialog.AffirmDialogListenner() {
                    @Override
                    public void onCancle() {

                    }

                    @Override
                    public void onOK() {
                        SharedPreferenceUtil.getInstance().clearAll(getActivity());
                        startActivity(new Intent(getActivity(), SplashActivity.class));
                        Objects.requireNonNull(getActivity()).finish();
                    }
                });
                dialog.show();
            }
        });
    }
}