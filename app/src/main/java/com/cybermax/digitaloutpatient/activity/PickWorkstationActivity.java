package com.cybermax.digitaloutpatient.activity;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.cybermax.digitaloutpatient.R;
import com.cybermax.digitaloutpatient.adapter.ChooseScreenModeRvAdapter;
import com.cybermax.digitaloutpatient.bean.share.User;
import com.cybermax.digitaloutpatient.bean.share.Workstation;
import com.cybermax.digitaloutpatient.constract.PicketWorkstationContract;
import com.cybermax.digitaloutpatient.databinding.ActivityPickWorkstationBinding;
import com.cybermax.digitaloutpatient.enums.DeviceTypeEnum;
import com.cybermax.digitaloutpatient.presenter.PickWorkstationPresenter;
import com.cybermax.digitaloutpatient.tool.SharedPreferenceUtil;
import com.lib.dialog.LoadingDialog;
import com.lib.tool.DeviceTypeUtil;
import com.lib.views.bordereffect.BorderView;
import com.lib.views.recyclerview.BaseQuickAdapter;

import java.util.List;

/**
 * 选择工作台,
 */
public class PickWorkstationActivity extends AppCompatActivity implements PicketWorkstationContract.View {
    ActivityPickWorkstationBinding binding;
    private   ChooseScreenModeRvAdapter mAdapter;
    private   LoadingDialog dialog;
    private   PickWorkstationPresenter mPresenter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        dialog = new LoadingDialog(this);
        dialog.show();
        binding = DataBindingUtil.setContentView(this, R.layout.activity_pick_workstation);
        String deviceType  =  SharedPreferenceUtil.getInstance().getDeviceType();
        String  userGuid = null;
        if(DeviceTypeEnum.DOCTOR_WORKSTATION.getValue().equals(deviceType)){
            User user = (User) SharedPreferenceUtil.getInstance().getObject(User.class);
            if(user!=null){
                userGuid =user.getUserGuid();
            }
        }
        mPresenter = new PickWorkstationPresenter(this, this);
        mPresenter.getWorkstation(userGuid,deviceType);

        mAdapter = new ChooseScreenModeRvAdapter(this);
        binding.rvChooseScreenMode.setAdapter(mAdapter);
        View emptyView = getLayoutInflater().inflate(R.layout.rv_empty_view, (ViewGroup) findViewById(android.R.id.content), false);
        mAdapter.setEmptyView(true, emptyView);
        if (DeviceTypeUtil.checkScreenIsTV(this)) {
            BorderView border = new BorderView(this);
            border.setBackgroundResource(R.drawable.login_focuse_border);
            border.attachTo(binding.rvChooseScreenMode);
        }
    }

    @Override
    public void initWorkstaionChooseView(List<Workstation> beanList) {
        int spanCount;
        if (beanList != null && beanList.size() > 0) {
            spanCount = beanList.size() > 4 ? 5 : beanList.size();
        } else {
            return;
        }
        GridLayoutManager layoutManager = new GridLayoutManager(this, spanCount, GridLayoutManager.VERTICAL, false);
        binding.rvChooseScreenMode.setLayoutManager(layoutManager);
        mAdapter.setNewData(beanList);
        mAdapter.setOnRecyclerViewItemClickListener(new BaseQuickAdapter.OnRecyclerViewItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Workstation  workstation = beanList.get(position);
                SharedPreferenceUtil.getInstance().putObject(workstation);
                Intent intent = new Intent();
                // 获取用户计算后的结果+
                setResult(1, intent);
                finish();
            }
        });
        dialog.dismiss();
    }

    @Override
    public void makeShortToast(String text) {
        Toast.makeText(this, text, Toast.LENGTH_SHORT).show();
    }
    @Override
    public void  finishActiviyAsFailed(){
        dialog.dismiss();
        Intent intent = new Intent();
        // 获取用户计算后的结果+
        setResult(0, intent);
        finish();
    }
}
