package com.cybermax.digitaloutpatient.fragment.desk;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.cybermax.digitaloutpatient.R;
import com.cybermax.digitaloutpatient.adapter.RecyclerViewAdapter;
import com.cybermax.digitaloutpatient.bean.CallNumber;
import com.cybermax.digitaloutpatient.bean.Inoculation;
import com.cybermax.digitaloutpatient.databinding.ContantChargeLayoutBinding;
import com.cybermax.digitaloutpatient.databinding.FragmentDeskMainBinding;
import com.cybermax.digitaloutpatient.enums.TicketActionResultEnum;
import com.lib.dialog.AffirmDialog;
import com.lib.tool.RxToast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 收费台首页
 */
public class ChargeDeskFragment extends BaseWorkstationFragment implements  View.OnClickListener {
    private ContantChargeLayoutBinding chargeLayoutBinding;
    private RecyclerView rv;
    private RecyclerViewAdapter mAdapter;
    private View view;
    private TextView totalPrice;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    @Override
    protected void initMainView(FragmentDeskMainBinding binding, ViewGroup container, LayoutInflater inflater) {
        chargeLayoutBinding = DataBindingUtil.inflate(inflater, R.layout.contant_charge_layout, container, false);
        view = inflater.inflate(R.layout.contant_charge_layout,binding.rv);
        rv = view.findViewById(R.id.rv_charge);
        totalPrice = view.findViewById(R.id.totalPrice);
        initRecyclerView();
    }

    @Override
    protected void afterCallingViewDisplay(CallNumber callNumber) {
        if (callNumber.getInoculations() != null && callNumber.getInoculations().size() > 0) {
            for(Inoculation i:callNumber.getInoculations()) {
                i.setInstInocState(null);
            }
            mAdapter.setNewData(callNumber.getInoculations());
            totalPrice.setText(callNumber.getTotalPrice() + "元");
        }else{
            mAdapter.setNewData(new ArrayList<>());
        }
    }

    @Override
    protected void beforeCallingViewDisplay(CallNumber callNumber) {

    }

    /**
     * 呼叫完成按钮被点击
     */
    @Override
    protected void onBtnComplete(){
        Map<String,Object> operator = new HashMap<>();
        operator.put("tiacId",currentCallNumber.getTiacId());
        operator.put("wostId",workstation.getId());
        operator.put("tiacDoctor", user.getUserTrueName());
        operator.put("tiacResult", TicketActionResultEnum.DEFAULT.getValue());
        presenter.complete(operator);
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
        totalPrice.setText(0.00 + "元");
    }

    private void initRecyclerView() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        mAdapter = new RecyclerViewAdapter(getActivity());
        rv.setAdapter(mAdapter);
        rv.setLayoutManager(layoutManager);
    }

}