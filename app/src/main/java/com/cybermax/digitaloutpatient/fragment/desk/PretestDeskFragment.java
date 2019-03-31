package com.cybermax.digitaloutpatient.fragment.desk;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;

import com.cybermax.digitaloutpatient.R;
import com.cybermax.digitaloutpatient.adapter.PretestMainRvAdapter;
import com.cybermax.digitaloutpatient.bean.CallNumber;
import com.cybermax.digitaloutpatient.bean.Child;
import com.cybermax.digitaloutpatient.bean.InoculationExam;
import com.cybermax.digitaloutpatient.bean.Question;
import com.cybermax.digitaloutpatient.constract.PretestDeskContract;
import com.cybermax.digitaloutpatient.databinding.FragmentDeskMainBinding;
import com.cybermax.digitaloutpatient.enums.ProcdureEnum;
import com.cybermax.digitaloutpatient.enums.TicketActionResultEnum;
import com.cybermax.digitaloutpatient.presenter.PretestDeskPresenter;
import com.lib.tool.RxToast;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 预检台
 */
public class PretestDeskFragment extends BaseWorkstationFragment implements PretestDeskContract.View, View.OnClickListener {
    private  PretestMainRvAdapter mAdapter;
    private  RecyclerView recyclerView;
    private  List<CheckBox> checkBoxList=new ArrayList<>();
    private  List<Question>  questions;
    private  CheckBox   checkboxEnablePhysicalExam;
    private  CheckBox   checkboxEnableInoculate;
    private  CheckBox   checkboxUnable;
    private PretestDeskPresenter pretestDeskPresenter;
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return super.onCreateView(inflater, container, savedInstanceState);
    }


    @Override
    protected void initMainView(FragmentDeskMainBinding binding, ViewGroup container, LayoutInflater inflater) {
        View  view =  inflater.inflate(R.layout.contant_pretest_layout,binding.rv);
        pretestDeskPresenter = new PretestDeskPresenter(getActivity(),this, workstation, user);
        mAdapter= new PretestMainRvAdapter(getActivity()) {
            @Override
            public void onYesOrNoClcked(CheckBox checkBox) {
                if(currentCallNumber == null) {
                    RxToast.error("请先呼叫儿童");
                    checkBox.setChecked(false);
                    return;
                }
                for (CheckBox c : checkBoxList) {
                    c.setChecked(false);
                }
                for(Question question:questions){
                    if(null ==question.getInexResult()){
                        continue;
                    } else if(question.getInexResult() == 1){
                        checkboxUnable.setChecked(true);
                        return;
                    }
                }
                checkboxEnableInoculate.setChecked(true);
            }
        };
        recyclerView = view.findViewById(R.id.rvPretest);
        LinearLayoutManager layoutManagerWait = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManagerWait);
        recyclerView.setAdapter(mAdapter);

//        mAdapter.setNewData(questions);

        checkboxEnablePhysicalExam= view.findViewById(R.id.checkboxEnablePhysicalExam);
        checkBoxList.add(checkboxEnablePhysicalExam);

        checkboxEnableInoculate=view.findViewById(R.id.checkboxEnableInoculate);
        checkBoxList.add(checkboxEnableInoculate);

        checkboxUnable=view.findViewById(R.id.checkboxUnable);
        checkBoxList.add(checkboxUnable);

        for(CheckBox checkBox:checkBoxList){
            checkBox.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(currentCallNumber == null) {
                        RxToast.error("请先呼叫儿童");
                    }
                    for(CheckBox c : checkBoxList) {
                        c.setChecked(false);
                    }
                    checkBox.setChecked(true);
                }
            });
        }
        pretestDeskPresenter.listQuestions(0);
        presenter.getProcessingTicket(workstation.getId());
    }


    @Override
    protected void afterCallingViewDisplay(CallNumber callNumber) {
    }

    @Override
    protected void beforeCallingViewDisplay(CallNumber callNumber) {



    }

    @Override
    public void showQuestions(List<Question> questions) {
        if(null == this.questions || this.questions.size() == 0)
            this.questions = questions;
        mAdapter.setNewData(questions);
    }


    @Override
    public void clearCallingTicket() {
        super.clearCallingTicket();
        if(null != questions && questions.size() > 0) {
            for(Question question : questions) {
                question.setInexResult(null);
            }
        }
        for(CheckBox checkBox:checkBoxList) {
            checkBox.setChecked(false);
        }
        mAdapter.setNewData(questions);
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
            RxToast.error("未选择预检结果");
            return;
        }
        //获取adapter上所有的数据状态
        if(null == questions || questions.size() == 0) {
            RxToast.error("测试题不能为空");
            return;
        }
        List<InoculationExam> exams = new ArrayList<>();
        for(Question question : questions) {
            if(null != question.getInexResult()) {
                InoculationExam inoculationExam = new InoculationExam();
                inoculationExam.setChilNo(currentCallNumber.getChilNo());
                inoculationExam.setInexResult(question.getInexResult());
                inoculationExam.setQueId(question.getQueId());
                inoculationExam.setTicketId(currentCallNumber.getId());
                exams.add(inoculationExam);
            }
        }
        Map<String,Object> operator = new HashMap<>();
        operator.put("wostId",workstation.getId());
        operator.put("tiacDoctor", user.getUserTrueName());
        operator.put("tiacId", currentCallNumber.getTiacId());
        operator.put("exams", exams);
        if(selctedCheckBox.getId() == R.id.checkboxEnableInoculate) {
            operator.put("tiacResult", TicketActionResultEnum.INOC_ENABLED.getValue());
        } else if(selctedCheckBox.getId() == R.id.checkboxEnablePhysicalExam) {
            operator.put("tiacResult", TicketActionResultEnum.EXAM_ENABLED.getValue());
        }else if(selctedCheckBox.getId() == R.id.checkboxUnable) {
            operator.put("tiacResult", TicketActionResultEnum.INOC_DISABLED.getValue());
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

}