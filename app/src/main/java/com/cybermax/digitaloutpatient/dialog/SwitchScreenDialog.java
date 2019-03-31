package com.cybermax.digitaloutpatient.dialog;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.View;

import com.cybermax.digitaloutpatient.R;
import com.cybermax.digitaloutpatient.adapter.SwitchScreenDialogRvAdapter;
import com.cybermax.digitaloutpatient.bean.share.User;
import com.cybermax.digitaloutpatient.bean.share.Workstation;
import com.cybermax.digitaloutpatient.enums.DeviceTypeEnum;
import com.cybermax.digitaloutpatient.model.PickWorkstationModel;
import com.cybermax.digitaloutpatient.tool.SharedPreferenceUtil;
import com.google.gson.reflect.TypeToken;
import com.lib.dialog.BaseDialog;
import com.lib.http.HttpTaskCallBack;
import com.lib.tool.DeviceTypeUtil;
import com.lib.tool.ScreenSizeUtil;
import com.lib.util.GsonUtil;
import com.lib.views.bordereffect.BorderView;
import com.lib.views.recyclerview.BaseQuickAdapter;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.List;

public class SwitchScreenDialog extends BaseDialog {
    private     Context mContext;
    private     SwitchScreenDialogRvAdapter mAdapter;
    private   DialogDismissCallBack  dialogDismissCallBack;
    public SwitchScreenDialog(Context context, DialogDismissCallBack  dialogDismissCallBack) {
        super(context, R.style.CustomDialogStyle);
        mContext = context;
        this.dialogDismissCallBack =dialogDismissCallBack;

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_switch_screen);
        getWindow().getAttributes().width = ScreenSizeUtil.getScreenWidth() * 9 / 10;
        getWindow().getAttributes().height = ScreenSizeUtil.getScreenHeight() / 2;
        getWindow().getAttributes().gravity = Gravity.CENTER;
        setCanceledOnTouchOutside(false);
        initView();
    }

    private void initView() {
        RecyclerView rvChooseScreenMode = findViewById(R.id.rvChooseScreenMode);
        String deviceType  =  SharedPreferenceUtil.getInstance().getDeviceType();
        String  userGuid = null;
        if(DeviceTypeEnum.DOCTOR_WORKSTATION.getValue().equals(deviceType)){
            User user = (User) SharedPreferenceUtil.getInstance().getObject(User.class);
            if(user!=null){
                userGuid =user.getUserGuid();
            }
        }

        if (DeviceTypeEnum.OUTPUT.getValue().equals(deviceType)) {
            BorderView border = new BorderView(mContext);
            border.attachTo(rvChooseScreenMode);
            rvChooseScreenMode.setFocusable(false);
            rvChooseScreenMode.scrollToPosition(0);
        }

        mAdapter = new SwitchScreenDialogRvAdapter(mContext);
        rvChooseScreenMode.setAdapter(mAdapter);
        View emptyView = getLayoutInflater().inflate(R.layout.rv_empty_view,
                findViewById(android.R.id.content), false);
        mAdapter.setEmptyView(true, emptyView);
        if (DeviceTypeUtil.checkScreenIsTV(mContext)) {
            BorderView border = new BorderView(mContext);
            border.attachTo(rvChooseScreenMode);
        }
        PickWorkstationModel mModel = new PickWorkstationModel(mContext);
        mModel.getWorkstation(userGuid,deviceType, new HttpTaskCallBack<List<Workstation>>() {
            @Override
            public void onSuccess(List<Workstation> beanList) {
                    beanList.add(new Workstation("设置"));
                    LinearLayoutManager layoutManager = new LinearLayoutManager(mContext);
                    layoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
                    rvChooseScreenMode.setLayoutManager(layoutManager);
                    mAdapter.setNewData(beanList);
                    mAdapter.setOnRecyclerViewItemClickListener(new BaseQuickAdapter.OnRecyclerViewItemClickListener() {
                        @Override
                        public void onItemClick(View view, int position) {
                            Workstation workstation = beanList.get(position);
                            dialogDismissCallBack.onDialogDismis(workstation);
                            dismiss();
                        }
                    });
            }

            @Override
            public void onFail(String result) {

            }
        });
    }
}
