package com.cybermax.digitaloutpatient.fragment.desk;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;

import com.cybermax.digitaloutpatient.R;
import com.cybermax.digitaloutpatient.bean.CallNumber;
import com.cybermax.digitaloutpatient.bean.Question;
import com.cybermax.digitaloutpatient.databinding.ContantExamLayoutBinding;
import com.cybermax.digitaloutpatient.databinding.FragmentDeskMainBinding;
import com.cybermax.digitaloutpatient.enums.ProcdureEnum;
import com.cybermax.digitaloutpatient.enums.ProcdureEnum;
import com.cybermax.digitaloutpatient.enums.TicketActionResultEnum;
import com.lib.tool.RxToast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 体检台主界面
 */
public class PhysicalExamDeskFragment extends BaseWorkstationFragment{
    private   CheckBox   checkboxComplete;
    private   CheckBox   checkboxEnableInoculate;
    private   ContantExamLayoutBinding   examLayoutBinding;
    private List<CheckBox> checkBoxList=new ArrayList<>();
    private   View    view;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return super.onCreateView(inflater, container, savedInstanceState);
    }


    @Override
    protected void initMainView(FragmentDeskMainBinding binding, ViewGroup container, LayoutInflater inflater) {
        examLayoutBinding = DataBindingUtil.inflate(inflater, R.layout.contant_exam_layout, container, false);
        view =inflater.inflate(R.layout.contant_exam_layout,binding.rv);
        examLayoutBinding.checkboxEnableInoculate.setOnClickListener(this);
        checkboxComplete=view.findViewById(R.id.checkboxComplete);
        checkBoxList.add(checkboxComplete);
        checkboxEnableInoculate=view.findViewById(R.id.checkboxEnableInoculate);
        checkBoxList.add(checkboxEnableInoculate);

        for(CheckBox checkBox: checkBoxList){
            checkBox.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    setChecked((CheckBox) v);
                }
            });
        }
        view.setVisibility(View.INVISIBLE);
    }

    private  void setChecked(CheckBox  v){
        for(CheckBox checkBox:checkBoxList){
            if(!checkBox.equals(v)){
                checkBox.setChecked(false);
            }
        }
    }

    @Override
    protected void afterCallingViewDisplay(CallNumber callNumber) {
        view.setVisibility(View.VISIBLE);
    }

    @Override
    protected void beforeCallingViewDisplay(CallNumber callNumber) {

    }
    @Override
    protected void onBtnComplete() {
        //完成按钮
        CheckBox selctedCheckBox =null;
        for(CheckBox checkBox:checkBoxList){
            if(checkBox.isChecked()){
                selctedCheckBox=checkBox;
            }
        }
        if(selctedCheckBox==null){
            RxToast.error("未选择体检结果");
            return;
        }
        Map<String,Object> operator = generateOperator();
        if(selctedCheckBox.getId() == R.id.checkboxComplete) {
            operator.put("tiacResult", TicketActionResultEnum.EXAM_OVER.getValue());
        } else if(selctedCheckBox.getId() == R.id.checkboxEnableInoculate) {
            operator.put("tiacResult", TicketActionResultEnum.EXAM_TO_INOC.getValue());
        }
        presenter.complete(operator);
    }

    /**
     * 呼叫按钮被点击
     */
    @Override
    protected void onBtnPass() {
        presenter.passNumber(currentCallNumber.getTiacId(), workstation.getId());
    }

    /**
     * 重复呼叫按钮被点击
     */
    @Override
    protected void onBtnCallAgain() {
        presenter.reCallNumber(workstation.getId(), currentCallNumber.getTiacId());
    }

    @Override
    public void clearCallingTicket() {
        super.clearCallingTicket();
        checkboxComplete.setChecked(false);
        checkboxEnableInoculate.setChecked(false);
        view.setVisibility(View.INVISIBLE);
    }

}