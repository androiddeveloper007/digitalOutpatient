package com.cybermax.digitaloutpatient.fragment.desk;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.bainuosoft.aidlInterface.IVimsAidlInterface;
import com.cybermax.digitaloutpatient.R;
import com.cybermax.digitaloutpatient.adapter.RecyclerViewAdapter;
import com.cybermax.digitaloutpatient.bean.CallNumber;
import com.cybermax.digitaloutpatient.bean.FridgeInfo;
import com.cybermax.digitaloutpatient.bean.Inoculation;
import com.cybermax.digitaloutpatient.constract.BaseWorkstationContract;
import com.cybermax.digitaloutpatient.constract.InoculateDeskContract;
import com.cybermax.digitaloutpatient.databinding.ContantInoculateLayoutBinding;
import com.cybermax.digitaloutpatient.databinding.FragmentDeskMainBinding;
import com.cybermax.digitaloutpatient.dialog.ChooseBodyPartDialog;
import com.cybermax.digitaloutpatient.dialog.TakeOutVaccineDialog;
import com.cybermax.digitaloutpatient.enums.FridgeTypeEnum;
import com.cybermax.digitaloutpatient.enums.TicketActionResultEnum;
import com.cybermax.digitaloutpatient.presenter.InoculateDeskPresenter;
import com.lib.dialog.AffirmDialog;
import com.lib.tool.RxToast;

import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 接种台主界面
 */
public class InoculateDeskMainFragment extends BaseWorkstationFragment implements InoculateDeskContract.View,BaseWorkstationContract.View, View.OnClickListener {
    ContantInoculateLayoutBinding inoculateLayoutBinding;
    private IVimsAidlInterface vi;
    private InoculateDeskPresenter mPresenter;
    private RecyclerViewAdapter mAdapter;
    RecyclerView rv;
    private FridgeInfo fridgeInfo;
    public  int fridgeType =FridgeTypeEnum.NONE.getValue();
    private final int SCAN_CHILD_BAR_CODE = 1;
    private final int INTERVAL = 500; //输入时间间隔为300毫秒
    private Handler mHandler;


    private List<Inoculation> globalInoculationList;//全局疫苗列表

//    public InoculateDeskMainFragment(FridgeAdapter FridgeAdapter) {
//        super();
//    }


    @Override
    protected void initMainView(FragmentDeskMainBinding binding, ViewGroup container, LayoutInflater inflater) {
        mPresenter = new InoculateDeskPresenter(getActivity(), this,workstation, user);
        View v = inflater.inflate(R.layout.contant_inoculate_layout,binding.rv);
//        inoculateLayoutBinding = DataBindingUtil.inflate(inflater, R.layout.contant_inoculate_layout, container, false);
        rv = v.findViewById(R.id.rv_inoculate);
        initRecyclerView();
        gestureCallback(rv);
        presenter.getProcessingTicket(workstation.getId());
        //是否为冰箱类型
        Bundle bundle =this.getArguments();
        if(bundle!=null&&StringUtils.isNotEmpty(bundle.getString("iceBoxNm"))) {
            // 初始化海尔服务
            fridgeType=FridgeTypeEnum.HAIER.getValue();
            initHaierService();
        }

        binding.etScan.setVisibility(View.VISIBLE);
//        binding.etScan.setText("0034262879");
        binding.etScan.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
//                RxToast.error(b ? "focus on" : "focus off");
                Log.e("sdffj", b ? "focus on" : "focus off");
            }
        });
        mHandler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                if (msg.what == SCAN_CHILD_BAR_CODE) {
                    //判断长度是条形码字符长度时，调用接口，判断条码是否和当前用户的条码编号相同。若是直接调用打开冰箱抽屉接口
                    String val = binding.etScan.getText().toString().trim();
                    binding.etScan.setText("");
                    if(val.length() <= 0)
                        return;
                    if (currentCallNumber == null) {
                        RxToast.error("请先呼叫儿童！");
                        return;
                    }
                    if(StringUtils.isBlank(currentCallNumber.getChilCardNo()) ||
                            !currentCallNumber.getChilCardNo().equals(val)) {
                        RxToast.error("免疫卡号不匹配！");
                        return;
                    }
                    //前段校验当前儿童免疫卡号和扫码所得卡号相等
                    // 打开未完成的疫苗
                    openFridgeDoor(null);
                }
            }
        };

        binding.etScan.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                if (mHandler.hasMessages(SCAN_CHILD_BAR_CODE)) {
                    mHandler.removeMessages(SCAN_CHILD_BAR_CODE);
                }
                mHandler.sendEmptyMessageDelayed(SCAN_CHILD_BAR_CODE, INTERVAL);
            }
        });
//        new TakeOutVaccineDialog(getActivity(),mPresenter,null).show();
    }


    private void initHaierService() {
        //绑定海尔app aidl服务
        try {
            Intent bindIntent = new Intent();
            bindIntent.setComponent(new ComponentName("com.bainuosoft.vims.activitys","com.bainuosoft.vims.service.MyService"));
            getActivity().bindService(bindIntent, new ServiceConnection() {
                public void onServiceConnected(ComponentName className, IBinder service) {
                    Toast.makeText(getActivity(), "onServiceConnected", Toast.LENGTH_LONG).show();
                    vi = com.bainuosoft.aidlInterface.IVimsAidlInterface.Stub.asInterface(service);
                    fridgeType = FridgeTypeEnum.HAIER.getValue();
                }

                public void onServiceDisconnected(ComponentName className) {
                    Toast.makeText(getActivity(), "Service has unexpectedly disconnected", Toast.LENGTH_LONG).show();
                    vi = null;
                }
            }, getActivity().BIND_AUTO_CREATE);
        } catch (Exception ex) {
            Toast.makeText(getActivity(), "ex=" + ex.getMessage(), Toast.LENGTH_LONG).show();
        }
        Bundle bundle =this.getArguments();//得到从Activity传来的数据
        if(bundle!=null){
            fridgeInfo = new FridgeInfo();
            fridgeInfo.setDeviceId(bundle.getString("deviceId"));
            fridgeInfo.setDeviceType(bundle.getString("deviceType"));
            fridgeInfo.setEmpId(bundle.getString("empId"));
            fridgeInfo.setCorpId(bundle.getString("corpId"));
            fridgeInfo.setIceBoxNm(bundle.getString("iceBoxNm"));
            fridgeInfo.setUserName(bundle.getString("userName"));
            fridgeInfo.setKey(bundle.getString("key"));
        }
    }


    private void openFridgeDoor(Inoculation inoculation) {
        try {
            if(null == inoculation) {
                for(Inoculation i : globalInoculationList) {
                    if(i.getInstInocState() == 1) {
                        inoculation = i;
                        break;
                    }
                }
            }
            if(null == inoculation) {
                return;
            }
            String res = vi.openDoorByVaccinerInfo(inoculation.getBactCode(), inoculation.getInstCorporation(), inoculation.getInstBatchNo());
            org.json.JSONObject jsonObject = new org.json.JSONObject(res);
            if(!jsonObject.getBoolean("IsSuccess")) {
                RxToast.error(jsonObject.getString("OutMessage"));
                return;
            }
            //打开取疫苗的提示框
            inoculation.setFridgeInfo(fridgeInfo);
            TakeOutVaccineDialog dialog = new TakeOutVaccineDialog(getActivity(), mPresenter, inoculation);
            dialog.show();
            dialog.setInoculationClickListener(new BaseWorkstationContract.OnClickListener() {
                @Override
                public void onClick(Inoculation selectedBean) {
                    mPresenter.confirmSingleInoc(selectedBean);
                }
            });
        }catch (Exception e) {
            RxToast.error("开启失败:"+e.getMessage());
        }
    }


    private void initRecyclerView() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        mAdapter = new RecyclerViewAdapter(getActivity());
        rv.setAdapter(mAdapter);
        rv.setLayoutManager(layoutManager);

        //取消接种
        mAdapter.setOnCancelClickListener(new BaseWorkstationContract.OnClickListener() {
            @Override
            public void onClick(Inoculation selectedBean) {
                mPresenter.cancel(selectedBean);
            }
        });

        // 接种
        mAdapter.setOnInoculationClickListener(new BaseWorkstationContract.OnClickListener() {
            @Override
            public void onClick(Inoculation selectedBean) {
                if(fridgeType == FridgeTypeEnum.NONE.getValue()) {
                    ChooseBodyPartDialog dialog = new ChooseBodyPartDialog(getContext(), selectedBean);
                    dialog.show();
                    dialog.setOnCommitListener(new BaseWorkstationContract.OnClickListener() {
                        @Override
                        public void onClick(Inoculation checkedBean) {
                            mPresenter.confirmSingleInoc(selectedBean);
                            dialog.dismiss();
                        }
                    });
                } else {
                    openFridgeDoor(selectedBean);
                }
            }
        });
    }




    /**
     * 呼叫完成按钮被点击
     */
    @Override
    protected void onBtnComplete(){
        List<Inoculation> list = currentCallNumber.getInoculations();
        Map<String,Object> operator = new HashMap<>();
        operator.put("tiacId",currentCallNumber.getTiacId());
        operator.put("wostId",workstation.getId());
        operator.put("tiacDoctor", user.getUserTrueName());
        operator.put("tiacResult", TicketActionResultEnum.DEFAULT.getValue());

        // 判断是否存在未处理的疫苗
        if(null == list || list.size() == 0) {
            mPresenter.complete(operator);
            return;
        }
        int count = 0;
        for (Inoculation i : list) {
            if(i.getInstInocState().equals(1)) {
                count++;
            }
        }
        if(count > 0) {
            AffirmDialog dialog = new AffirmDialog(getContext(),String.format("你还有%s剂疫苗未接种，确认完成接种？", count));
            dialog.setAffirmDialogListenner(new AffirmDialog.AffirmDialogListenner() {
                @Override
                public void onCancle() {}
                @Override
                public void onOK() {
                    mPresenter.complete(operator);
                }
            });
            dialog.show();
        }else {
            mPresenter.complete(operator);
        }
    }

    /**
     * 呼叫按钮被点击
     */
    @Override
    protected  void onBtnCallClick(){
        presenter.callNumber(workstation.getId(), null);
    }


    /**
     * 呼叫按钮被点击
     */
    @Override
    protected  void onBtnPass(){
        presenter.passNumber(currentCallNumber.getTiacId(), workstation.getId());
    }


    /**
     * 重复呼叫按钮被点击
     */
    @Override
    protected  void onBtnCallAgain(){
        presenter.reCallNumber(workstation.getId(), currentCallNumber.getTiacId());
    }

    @Override
    public void clearCallingTicket() {
        super.clearCallingTicket();
        mAdapter.setNewData(new ArrayList<>());
    }

    @Override
    protected void afterCallingViewDisplay(CallNumber callNumber) {
        if (callNumber.getInoculations() != null && callNumber.getInoculations().size() > 0) {
            globalInoculationList = callNumber.getInoculations();
            mAdapter.setNewData(callNumber.getInoculations());
        }else {
            mAdapter.setNewData(new ArrayList<>());
        }
    }

    @Override
    protected void beforeCallingViewDisplay(CallNumber callNumber) {

    }

    @Override
    public void refreshInoculations(Inoculation inoculation,boolean startNext) {
        for(int i = 0;i<globalInoculationList.size();i++){
            if(globalInoculationList.get(i).getInstId().equals(inoculation.getInstId()))
                globalInoculationList.set(i, inoculation);
        }
        mAdapter.setNewData(globalInoculationList);
        if(startNext && fridgeType != 0)
            openFridgeDoor(null);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

}