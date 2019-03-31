package com.cybermax.digitaloutpatient.presenter;

import android.content.Context;
import android.widget.Toast;

import com.cybermax.digitaloutpatient.bean.Ticket;
import com.cybermax.digitaloutpatient.bean.share.Workstation;
import com.cybermax.digitaloutpatient.constract.BigWaitScreenContract;
import com.cybermax.digitaloutpatient.enums.ProcdureEnum;
import com.cybermax.digitaloutpatient.enums.TicketActionResultEnum;
import com.cybermax.digitaloutpatient.enums.TicketActionStatusEnum;
import com.cybermax.digitaloutpatient.enums.WorkStationTypeEnum;
import com.cybermax.digitaloutpatient.model.ScreemModel;
import com.cybermax.digitaloutpatient.model.TicketModel;
import com.lib.http.HttpTaskCallBack;

import java.util.ArrayList;
import java.util.List;


public class BigWaitScreenPresenter extends BasePresenter {
    private  Context context;
    private ScreemModel mModel;
    private TicketModel ticketModel;
    private BigWaitScreenContract.View mView;

    public BigWaitScreenPresenter(Context context, BigWaitScreenContract.View view) {
        super(context);
        this.context = context;
        mModel = new ScreemModel(context);
        ticketModel = new TicketModel(context);
        mContext = context;
        mView = view;
    }

    public void requestData(Workstation workstation) {
        try {
            List<String>   queueList = new ArrayList<>();
            //如果是综合大屏， 显示的数据应该由设置项控制
            if(WorkStationTypeEnum.ZONGHEPING.getValue().equals(workstation.getWstyCode())){
                queueList.add(ProcdureEnum.YUJIAN.getValue());
                queueList.add(ProcdureEnum.DENGJI.getValue());
                queueList.add(ProcdureEnum.JIEZHONG.getValue());
                mModel.listSynQueues(queueList, new HttpTaskCallBack<List<Ticket>>() {
                    @Override
                    public void onSuccess(List<Ticket> tickets) {
                        //等待list集合
                        showScreen(tickets);
                    }

                    @Override
                    public void onFail(String str) {

                    }
                });
            }else {
                listTicketScreen(workstation);
            }

        } catch (Exception e) {
            e.printStackTrace();
            Toast.makeText(context, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    private void showScreen(List<Ticket> tickets) {
        //等待list集合
        List<Ticket>  wait = new ArrayList<>();
        List<Ticket>  passed= new ArrayList<>();

        for(int i=0;i<tickets.size();i++){
            Ticket ticket = tickets.get(i);
            if(TicketActionStatusEnum.PROCESSING.getValue()==ticket.getTiacStatus()
                    ||TicketActionStatusEnum.WAITING.getValue()==ticket.getTiacStatus()){
                wait.add(ticket);
            }else  if(TicketActionStatusEnum.PASSED.getValue()==ticket.getTiacStatus()){
                passed.add(ticket);
            }
        }
        mView.reloadMainView(wait);
        //其他
        StringBuffer waitLitteStringBuffer = new StringBuffer();
        for(int i=0;i<wait.size();i++){
            if(i<6){
                continue;
            }
            Ticket t = wait.get(i);
            if (waitLitteStringBuffer.length() > 0)
                waitLitteStringBuffer.append(", ").append(t.getTicketDisplayNo());
            else
                waitLitteStringBuffer.append(t.getTicketDisplayNo());
        }
        mView.reloadWaitingView(waitLitteStringBuffer.toString());

        //其他
        StringBuffer passedStringBuffer = new StringBuffer();
        for(int i=0;i<passed.size();i++){
            Ticket ticket = passed.get(i);
            if (passedStringBuffer.length() > 0)
                passedStringBuffer.append(", ").append(ticket.getTicketDisplayNo());
            else
                passedStringBuffer.append(ticket.getTicketDisplayNo());
        }
        mView.reloadPassedView(passedStringBuffer.toString());
    }


    public void listTicketScreen(Workstation workstation) {
        ticketModel.listTicketQueue(workstation.getPrtyCode(), TicketActionStatusEnum.WAITING.getValue(),new HttpTaskCallBack<List<Ticket>>() {
            @Override
            public void onSuccess(List<Ticket> data) {
                showScreen(data);
            }
        });
    }

}
