package com.cybermax.digitaloutpatient.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.cybermax.digitaloutpatient.R;
import com.cybermax.digitaloutpatient.bean.StayObserveScreenBean;
import com.lib.views.recyclerview.BaseQuickAdapter;
import com.lib.views.recyclerview.BaseViewHolder;

import org.apache.commons.lang3.time.DateFormatUtils;

public class StayObserveRvAdapter extends BaseQuickAdapter<StayObserveScreenBean> {

    public StayObserveRvAdapter(Context cxt) {
        super(cxt);
    }

    @Override
    protected int setItemLayout() {
        return R.layout.rv_item_stay_observe;
    }

    @Override
    protected void convert(BaseViewHolder helper, StayObserveScreenBean item, int position) {
        ViewGroup.LayoutParams layoutParams = helper.convertView.getLayoutParams();
        layoutParams.height = getHeight() / 6;
        helper.convertView.setLayoutParams(layoutParams);
        TextView rowTitle0 = helper.convertView.findViewById(R.id.rowTitle0);
        TextView rowTitle1 = helper.convertView.findViewById(R.id.rowTitle1);
        TextView rowTitle2 = helper.convertView.findViewById(R.id.rowTitle2);
        TextView rowTitle3 = helper.convertView.findViewById(R.id.rowTitle3);

        helper.convertView.findViewById(R.id.row1line).
                setVisibility(position == getData().size() - 1 ? View.GONE : View.VISIBLE);
        helper.convertView.setBackgroundColor(
                Color.parseColor(position % 2 == 0 ? "#2b6de7" : "#5488eb"));
        setTextValid(rowTitle0, item.getTicketNo());
        setTextValid(rowTitle1, item.getChilName());
        if(item.getEndTime()!=null)
            rowTitle2.setText(DateFormatUtils.format(item.getEndTime(),DateFormatUtils.ISO_TIME_NO_T_FORMAT.getPattern()));
        if(item.getRestTime()!=null){
            if(0 == item.getRestTime()) {
                rowTitle3.setText("留观结束");
                rowTitle3.setTextColor(Color.parseColor("#e74c3c"));
            } else {
                rowTitle3.setText("剩余" + item.getRestTime() + "分");
                rowTitle3.setTextColor(Color.parseColor("#ffffff"));
            }
        }
    }
}