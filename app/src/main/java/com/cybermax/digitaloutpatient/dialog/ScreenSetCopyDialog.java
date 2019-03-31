package com.cybermax.digitaloutpatient.dialog;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.Gravity;
import android.view.View;

import com.cybermax.digitaloutpatient.R;
import com.cybermax.digitaloutpatient.activity.PickWorkstationActivity;
import com.cybermax.digitaloutpatient.constant.Constant;
import com.cybermax.digitaloutpatient.fragment.ScreenSetFragment;
import com.cybermax.digitaloutpatient.fragment.SoundSetFragment;
import com.cybermax.digitaloutpatient.fragment.SystemParamSetFragment;
import com.cybermax.digitaloutpatient.fragment.WorkstationSetFragment;
import com.cybermax.digitaloutpatient.tool.SharedPreferenceUtil;
import com.cybermax.digitaloutpatient.tool.SwitchFragmentModel;
import com.lib.dialog.BaseDialog;
import com.lib.tool.ScreenSizeUtil;

/**
 * 盒子设置弹框
 */
public class ScreenSetCopyDialog extends BaseDialog implements View.OnClickListener{
    private SwitchFragmentModel switchFragmentModel;
    private SharedPreferenceUtil sp;
    private Fragment baseFragment;
    private SoundSetFragment soundSetFragment;
    private ScreenSetFragment screenSetFragment;
    private WorkstationSetFragment workstationSetFragment;
    private SystemParamSetFragment systemParamSetFragment;

    public ScreenSetCopyDialog(Context context) {
        super(context, R.style.CustomDialogStyle);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_inoculate_set1);
        getWindow().getAttributes().width = ScreenSizeUtil.getScreenWidth() / 5 * 4;
        getWindow().getAttributes().height = ScreenSizeUtil.getScreenHeight() / 5 * 4;
        getWindow().getAttributes().gravity = Gravity.CENTER;
        setCanceledOnTouchOutside(true);
        initView();
    }

    private void initView() {
        findViewById(R.id.logout).setOnClickListener(this);
        findViewById(R.id.soundSet).setOnClickListener(this);
        findViewById(R.id.screenShowSet).setOnClickListener(this);
        findViewById(R.id.workstationSet).setOnClickListener(this);
        findViewById(R.id.systemParamSet).setOnClickListener(this);
        switchFragmentModel = new SwitchFragmentModel(mContext);
    }

    @Override
    public void onClick(View view) {
        if (sp == null) sp = SharedPreferenceUtil.getInstance();
        switch (view.getId()) {
            case R.id.logout:
//                sp.putString(SharedPreferenceUtil.DOCTOR_LOGIN_ACCOUNT, "");
//                sp.putString(SharedPreferenceUtil.DOCTOR_LOGIN_PWD, "");
                sp.putString(Constant.PRTY_CODE, "");
                mContext.startActivity(new Intent(mContext, PickWorkstationActivity.class));
                dismiss();
                break;
//            case R.id.cleanCache:
//                sp.clearAll(getActivity());
//                startActivity(new Intent(getActivity(),
//                        ServerLoginActivity.class));
//                Objects.requireNonNull(getActivity()).finish();
//                break;
            case R.id.soundSet:
                if(soundSetFragment==null)
                    soundSetFragment = new SoundSetFragment();
                baseFragment = soundSetFragment;
                break;
            case R.id.screenShowSet:
                if(screenSetFragment==null)
                    screenSetFragment = new ScreenSetFragment();
                baseFragment = screenSetFragment;
                break;
            case R.id.workstationSet:
                if(workstationSetFragment==null)
                    workstationSetFragment = new WorkstationSetFragment();
                baseFragment = workstationSetFragment;
                break;
            case R.id.systemParamSet:
                if(systemParamSetFragment==null)
                    systemParamSetFragment = new SystemParamSetFragment();
                baseFragment = systemParamSetFragment;
                break;
        }
        switchFragmentModel.add(baseFragment, R.id.setFragmentLayout);
    }
}
