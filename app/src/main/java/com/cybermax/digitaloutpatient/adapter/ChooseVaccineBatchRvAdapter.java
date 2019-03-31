package com.cybermax.digitaloutpatient.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.ViewGroup;
import android.widget.TextView;

import com.cybermax.digitaloutpatient.R;
import com.cybermax.digitaloutpatient.bean.BatchInfo;
import com.lib.util.DateUtil;
import com.lib.util.EmptyUtils;
import com.lib.views.recyclerview.BaseQuickAdapter;
import com.lib.views.recyclerview.BaseViewHolder;

import org.apache.commons.lang3.time.DateFormatUtils;

public class ChooseVaccineBatchRvAdapter extends BaseQuickAdapter<BatchInfo> {

    public int checkedPosition=-1;

    public ChooseVaccineBatchRvAdapter(Context cxt) {
        super(cxt);
    }

    @Override
    protected int setItemLayout() {
        return R.layout.rv_item_dialog_choose_vaccine_batch;
    }

    @Override
    protected void convert(BaseViewHolder helper, BatchInfo item, int position) {
        ViewGroup.LayoutParams layoutParams = helper.convertView.getLayoutParams();
        layoutParams.height = (getHeight() / 6);
        helper.convertView.setLayoutParams(layoutParams);
        TextView rowTitle0 = helper.convertView.findViewById(R.id.vaccineTitle);
        TextView rowTitle1 = helper.convertView.findViewById(R.id.factoryTitle);
        TextView rowTitle2 = helper.convertView.findViewById(R.id.batchTitle);
        TextView rowTitle3 = helper.convertView.findViewById(R.id.validTermTitle);
        TextView rowTitle4 = helper.convertView.findViewById(R.id.stockTitle);
        TextView rowTitle5 = helper.convertView.findViewById(R.id.priceTitle);
        if (EmptyUtils.isNotEmpty(item.getBactName()))
            rowTitle0.setText(item.getBactName());
        if (EmptyUtils.isNotEmpty(item.getCorporation()))
            rowTitle1.setText(item.getCorporation());
        if (EmptyUtils.isNotEmpty(item.getBatchNo()))
            rowTitle2.setText(item.getBatchNo());
        if (EmptyUtils.isNotEmpty(item.getValidDate()))
            rowTitle3.setText(DateFormatUtils.format(item.getValidDate(), DateUtil.YYYY_MM_DD));
        if (EmptyUtils.isNotEmpty(item.getStock()))
            rowTitle4.setText(item.getStock().toString());
        if (EmptyUtils.isNotEmpty(item.getPrice()))
            rowTitle5.setText(item.getPrice().toString());
        helper.convertView.findViewById(R.id.root).setBackgroundColor(Color.parseColor(checkedPosition == position ? "#6ecfd8" : "#ffffff"));
    }

    public void setOnCheckChanged(int position) {
        checkedPosition = position;
        notifyDataSetChanged();
    }
}