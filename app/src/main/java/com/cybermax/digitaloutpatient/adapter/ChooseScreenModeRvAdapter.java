package com.cybermax.digitaloutpatient.adapter;

import android.content.Context;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.cybermax.digitaloutpatient.R;
import com.cybermax.digitaloutpatient.bean.share.Workstation;
import com.cybermax.digitaloutpatient.enums.DeviceTypeEnum;
import com.cybermax.digitaloutpatient.enums.ProductEnum;
import com.cybermax.digitaloutpatient.tool.SharedPreferenceUtil;
import com.lib.tool.ScreenSizeUtil;
import com.lib.views.recyclerview.BaseQuickAdapter;
import com.lib.views.recyclerview.BaseViewHolder;

public class ChooseScreenModeRvAdapter extends BaseQuickAdapter<Workstation> {

    public ChooseScreenModeRvAdapter(Context cxt) {
        super(cxt);
    }

    @Override
    protected int setItemLayout() {
        return R.layout.rv_item_choose_screen_mode;
    }

    @Override
    protected void convert(BaseViewHolder helper, Workstation item, int position) {
        ViewGroup.LayoutParams layoutParams = helper.convertView.getLayoutParams();
        layoutParams.height = ScreenSizeUtil.getScreenWidth() / 7;
        layoutParams.width = ScreenSizeUtil.getScreenWidth() / 7;
        helper.convertView.setLayoutParams(layoutParams);
        TextView chooseScreenModeTitle = helper.convertView.findViewById(R.id.chooseScreenModeTitle);
        ImageView rvChooseScreenIcon = helper.convertView.findViewById(R.id.rvChooseScreenIcon);
        int drawableId = R.drawable.svg_screen;
        String deviceType = SharedPreferenceUtil.getInstance().getDeviceType();
        if (DeviceTypeEnum.DOCTOR_WORKSTATION.getValue().equals(deviceType)) {
            ProductEnum en = ProductEnum.getByValue(item.getPrtyCode());
            switch (en) {
                case YUJIAN:
                    drawableId = R.drawable.svg_pretest;
                    break;
                case DENGJI:
                    drawableId = R.drawable.svg_register;
                    break;
                case SHOUFEI:
                    drawableId = R.drawable.svg_charge;
                    break;
                case JIEZHONG:
                    drawableId = R.drawable.svg_inoculate;
                    break;
                case LIUGUAN:
                    drawableId = R.drawable.svg_stayobserve;
                    break;
                case TIJIAN:
                    drawableId = R.drawable.svg_physical_exam;
                    break;
                case ERBIHOU:
                    drawableId = R.drawable.svg_five_sence;
                    break;
            }
        }
        Glide.with(helper.convertView.getContext()).
                load(drawableId).
                into(rvChooseScreenIcon);
        chooseScreenModeTitle.setText(item.getWostShowName());
    }
}