package com.cybermax.digitaloutpatient.fragment;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cybermax.digitaloutpatient.R;
import com.cybermax.digitaloutpatient.activity.SplashActivity;
import com.cybermax.digitaloutpatient.adapter.ChooseScreenModeRvAdapter;
import com.cybermax.digitaloutpatient.bean.share.User;
import com.cybermax.digitaloutpatient.bean.share.Workstation;
import com.cybermax.digitaloutpatient.databinding.FragmentWorkstationSetChooseBinding;
import com.cybermax.digitaloutpatient.enums.DeviceTypeEnum;
import com.cybermax.digitaloutpatient.model.PickWorkstationModel;
import com.cybermax.digitaloutpatient.tool.SharedPreferenceUtil;
import com.lib.http.HttpTaskCallBack;
import com.lib.tool.RxToast;
import com.lib.views.recyclerview.BaseQuickAdapter;

import java.util.List;

public class WorkstationSetFragment extends Fragment {
    FragmentWorkstationSetChooseBinding binding;
    private ChooseScreenModeRvAdapter mAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_workstation_set_choose, container, false);
        View view = binding.getRoot();
        initViews();
        return view;
    }

    private void initViews() {
        String deviceType = SharedPreferenceUtil.getInstance().getDeviceType();
        String userGuid = null;
        if (DeviceTypeEnum.DOCTOR_WORKSTATION.getValue().equals(deviceType)) {
            User user = (User) SharedPreferenceUtil.getInstance().getObject(User.class);
            if (user != null) {
                userGuid = user.getUserGuid();
            }
        }

        GridLayoutManager layoutManager = new GridLayoutManager(getContext(), 4, GridLayoutManager.VERTICAL, false);
        binding.rvChooseScreenMode.setLayoutManager(layoutManager);
        mAdapter = new ChooseScreenModeRvAdapter(getActivity());
        binding.rvChooseScreenMode.setAdapter(mAdapter);

        PickWorkstationModel mModel = new PickWorkstationModel(getActivity());
        mModel.getWorkstation(userGuid, deviceType, new HttpTaskCallBack<List<Workstation>>() {
            @Override
            public void onSuccess(List<Workstation> beanList) {
                if(beanList!=null && beanList.size()>0){
                    mAdapter.setNewData(beanList);
                    mAdapter.setOnRecyclerViewItemClickListener(new BaseQuickAdapter.OnRecyclerViewItemClickListener() {
                        @Override
                        public void onItemClick(View view, int position) {
                            Workstation  workstation = beanList.get(position);
                            SharedPreferenceUtil.getInstance().putObject(workstation);
                            getActivity().startActivity(new Intent(getActivity(),SplashActivity.class));
                            getActivity().finish();
                        }
                    });
                }else{
                    View emptyView = getLayoutInflater().inflate(R.layout.rv_empty_view,
                            getActivity().findViewById(android.R.id.content), false);
                    mAdapter.setEmptyView(true, emptyView);
                }
            }

            @Override
            public void onFail(String str) {
                RxToast.success("str");
            }

        });
    }
}