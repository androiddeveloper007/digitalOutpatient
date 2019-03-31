package com.cybermax.digitaloutpatient.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.cybermax.digitaloutpatient.R;
import com.cybermax.digitaloutpatient.bean.share.Workstation;
import com.lib.views.recyclerview.BaseQuickAdapter;
import com.lib.views.recyclerview.BaseViewHolder;

public class SwitchScreenDialogRvAdapter extends BaseQuickAdapter<Workstation> {

    public SwitchScreenDialogRvAdapter(Context cxt) {
        super(cxt);
    }

    @Override
    protected int setItemLayout() {
        return R.layout.rv_item_choose_screen_mode;
    }

    @Override
    protected void convert(BaseViewHolder helper, Workstation item, int position) {
        ViewGroup.LayoutParams layoutParams = helper.convertView.getLayoutParams();
        layoutParams.width = getWidth() / 6;
        layoutParams.height = getWidth() / 6;
        TextView chooseScreenModeTitle = helper.convertView.findViewById(R.id.chooseScreenModeTitle);
        ImageView rvChooseScreenIcon = helper.convertView.findViewById(R.id.rvChooseScreenIcon);
        Glide.with(helper.convertView.getContext()).
                load(TextUtils.equals("设置", item.getWostShowName()) ? R.drawable.svg_set : R.drawable.svg_screen).
                into(rvChooseScreenIcon);
        String str = "";
        if (item.getWostShowName() != null)
            str += item.getWostShowName();
       /* if (item.getWostName() != null)
            str += "-" + item.getWostName();*/
        chooseScreenModeTitle.setText(str);
    }
}