package com.cybermax.digitaloutpatient.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.cybermax.digitaloutpatient.R;
import com.cybermax.digitaloutpatient.bean.Ticket;
import com.cybermax.digitaloutpatient.enums.TicketActionStatusEnum;
import com.lib.util.EmptyUtils;
import com.lib.views.recyclerview.BaseQuickAdapter;
import com.lib.views.recyclerview.BaseViewHolder;

/**
 * 疫苗公示adapter
 */
public class VaccinePublicityRvAdapter extends BaseQuickAdapter<Ticket> {

    public VaccinePublicityRvAdapter(Context cxt) {
        super(cxt);
    }

    @Override
    protected int setItemLayout() {
        return R.layout.rv_item_vaccine_publicity;
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