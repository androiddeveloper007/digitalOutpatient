package com.cybermax.digitaloutpatient.adapter;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.cybermax.digitaloutpatient.R;
import com.cybermax.digitaloutpatient.bean.Ticket;
import com.cybermax.digitaloutpatient.enums.TicketActionStatusEnum;
import com.lib.util.EmptyUtils;
import com.lib.views.recyclerview.BaseQuickAdapter;
import com.lib.views.recyclerview.BaseViewHolder;

public class InoculateWaitRvAdapter extends BaseQuickAdapter<Ticket> {

    public InoculateWaitRvAdapter(Context cxt) {
        super(cxt);
    }

    @Override
    protected int setItemLayout() {
        return R.layout.rv_item_inoculate_wait;
    }

    @Override
    protected void convert(BaseViewHolder helper, Ticket item, int position) {
        ViewGroup.LayoutParams layoutParams = helper.convertView.getLayoutParams();
        layoutParams.height = getHeight() / 6;
        helper.convertView.setLayoutParams(layoutParams);

        TextView rowTitle0 = helper.convertView.findViewById(R.id.tv4);
        TextView rowTitle1 = helper.convertView.findViewById(R.id.tv5);
        TextView rowTitle2 = helper.convertView.findViewById(R.id.tv6);
        rowTitle0.setText(item.getTicketDisplayNo());
        rowTitle1.setText(item.getChilName());
        if (TicketActionStatusEnum.PROCESSING.getValue() == (item.getTiacStatus())) {
            String wostShowName = item.getWostShowName();
            if (EmptyUtils.isNotEmpty(wostShowName)) {
                rowTitle2.setText(wostShowName);
            }
            //弹出叫号弹框，默认10秒自动关闭
//            new InoculateTipDialog(mContext.get()).show();
        } else {
            if (item.getIsBlank() == null || (item.getIsBlank() != null && 1 != item.getIsBlank())) {
                rowTitle2.setText(TicketActionStatusEnum.getDesc(item.getTiacStatus()));
            }
        }
        helper.convertView.findViewById(R.id.line).
                setVisibility(position == getData().size() - 1 ? View.GONE : View.VISIBLE);
        helper.convertView.setBackgroundColor(
                Color.parseColor(position % 2 == 0 ? "#2b6de7" : "#5488eb"));
        if (TicketActionStatusEnum.PROCESSING.getValue() == item.getTiacStatus()) {
            setTextViewStyle(rowTitle0, 24, true);
            setTextViewStyle(rowTitle1, 24, true);
            setTextViewStyle(rowTitle2, 24, true);
        } else {
            setTextViewStyle(rowTitle0, 22, false);
            setTextViewStyle(rowTitle1, 22, false);
            setTextViewStyle(rowTitle2, 22, false);
        }
    }

    private void setTextViewStyle(TextView tv, int textSize, boolean bold) {
        tv.setTextColor(Color.parseColor(bold ? "#fa7c24" : "#ffffff"));
        tv.setTextSize(TypedValue.COMPLEX_UNIT_SP, textSize);
        tv.setTypeface(Typeface.defaultFromStyle(bold ? Typeface.BOLD : Typeface.NORMAL));//加粗
    }
}