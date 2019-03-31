package com.cybermax.digitaloutpatient.adapter;

import android.content.Context;
import android.view.ViewGroup;
import android.widget.TextView;

import com.cybermax.digitaloutpatient.R;
import com.cybermax.digitaloutpatient.bean.Ticket;
import com.lib.views.recyclerview.BaseQuickAdapter;
import com.lib.views.recyclerview.BaseViewHolder;

/**
 * 体检历史adapter
 */
public class PhysicalExamHistoryRvAdapter extends BaseQuickAdapter<Ticket> {

    public PhysicalExamHistoryRvAdapter(Context cxt) {
        super(cxt);
    }

    @Override
    protected int setItemLayout() {
        return R.layout.rv_item_physical_exam_history;
    }

    @Override
    protected void convert(BaseViewHolder helper, Ticket item, int position) {
        ViewGroup.LayoutParams layoutParams = helper.convertView.getLayoutParams();
        layoutParams.height = getHeight() / 7;
        helper.convertView.setLayoutParams(layoutParams);
        TextView rowTitle0 = helper.convertView.findViewById(R.id.tv4);
        TextView rowTitle1 = helper.convertView.findViewById(R.id.tv5);
        TextView rowTitle2 = helper.convertView.findViewById(R.id.tv6);
    }
}