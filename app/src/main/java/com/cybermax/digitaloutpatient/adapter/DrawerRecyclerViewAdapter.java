package com.cybermax.digitaloutpatient.adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.constraint.ConstraintLayout;
import android.view.View;
import android.widget.TextView;

import com.cybermax.digitaloutpatient.R;
import com.cybermax.digitaloutpatient.bean.Ticket;
import com.cybermax.digitaloutpatient.enums.TicketActionStatusEnum;
import com.lib.util.DateUtil;
import com.lib.views.recyclerview.BaseQuickAdapter;
import com.lib.views.recyclerview.BaseViewHolder;

import org.apache.commons.lang3.time.DateFormatUtils;

public class DrawerRecyclerViewAdapter extends BaseQuickAdapter<Ticket> {
private int status;
private onCallNumBtnListener mOnCallNumListener;
    public DrawerRecyclerViewAdapter(Context cxt) {
        super(cxt);
    }

    public DrawerRecyclerViewAdapter(Context cxt,int status) {
        super(cxt);
        this.status =status;
    }

    @Override
    protected int setItemLayout() {
        return R.layout.rv_item_drawer;
    }

    @Override
    protected void convert(BaseViewHolder helper, Ticket item, int position) {
        TextView vaccineTitle = helper.convertView.findViewById(R.id.vaccineTitle);
        TextView agentTimeTitle = helper.convertView.findViewById(R.id.agentTimeTitle);
        TextView validTermTitle = helper.convertView.findViewById(R.id.validTermTitle);
        ConstraintLayout rvRoot = helper.convertView.findViewById(R.id.rv_root);
        vaccineTitle.setText(item.getTicketDisplayNo());
        agentTimeTitle.setText(item.getChilName());
        helper.convertView.setBackgroundColor(
                Color.parseColor(position % 2 == 0 ? "#ecf0f6" : "#ffffff"));
        if (item.getTiacStatus() == TicketActionStatusEnum.PROCESSING.getValue()) {
            rvRoot.setSelected(true);
            validTermTitle.setText(item.getWostShowName());
        }else if(item.getTiacStatus() == TicketActionStatusEnum.WAITING.getValue()){
            validTermTitle.setText(String.format("已等待%s分钟",item.getWaitTime()));
        }else if(item.getTiacStatus() == TicketActionStatusEnum.PASSED.getValue()) {
            validTermTitle.setText(DateFormatUtils.format(item.getTiacOverpassTime(),DateUtil.HH_MM_SS));
        } else if(item.getTiacStatus() == TicketActionStatusEnum.COMPLETED.getValue()) {
            validTermTitle.setText(DateFormatUtils.format(item.getTiacCompleteTime(),DateUtil.HH_MM_SS));
        }
//        if(status==0){
//            helper.convertView.findViewById(R.id.callNumBtn).setVisibility(View.VISIBLE);
//        }else if(status==2){
//            helper.convertView.findViewById(R.id.callNumBtn).setVisibility(View.VISIBLE);
//        }else if(status==3){
//            helper.convertView.findViewById(R.id.callNumLayout).setVisibility(View.GONE);
//        }
        if(status==3){
            helper.convertView.findViewById(R.id.callNumLayout).setVisibility(View.GONE);
        }else{
            helper.convertView.findViewById(R.id.callNumLayout).setVisibility(View.VISIBLE);
            helper.convertView.findViewById(R.id.callNumBtn).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    if(mOnCallNumListener!=null){
                        mOnCallNumListener.callNumBtn(position);
                    }
                }
            });
        }
    }

    public void setOnCallNumBtnListener(onCallNumBtnListener listener){
        mOnCallNumListener = listener;
    }

    public interface onCallNumBtnListener{
        void callNumBtn(int position);
    }
}