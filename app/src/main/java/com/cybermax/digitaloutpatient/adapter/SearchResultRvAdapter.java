package com.cybermax.digitaloutpatient.adapter;

import android.content.Context;
import android.view.ViewGroup;
import android.widget.TextView;

import com.cybermax.digitaloutpatient.R;
import com.cybermax.digitaloutpatient.bean.Child;
import com.cybermax.digitaloutpatient.bean.SearchResult;
import com.lib.views.recyclerview.BaseQuickAdapter;
import com.lib.views.recyclerview.BaseViewHolder;

public class SearchResultRvAdapter extends BaseQuickAdapter<Child> {

    public SearchResultRvAdapter(Context cxt) {
        super(cxt);
    }

    @Override
    protected int setItemLayout() {
        return R.layout.rv_item_search_result;
    }

    @Override
    protected void convert(final BaseViewHolder helper, Child item, int position) {
        ViewGroup.LayoutParams layoutParams = helper.convertView.getLayoutParams();
        layoutParams.height = getHeight() / 4;
        helper.convertView.setLayoutParams(layoutParams);
        TextView childNameText = helper.convertView.findViewById(R.id.childNameText);
        TextView chilBirthdayText = helper.convertView.findViewById(R.id.chilBirthdayText);
        TextView chilYearText = helper.convertView.findViewById(R.id.chilYearText);
        TextView chilCardNoText = helper.convertView.findViewById(R.id.chilCardNoText);
        TextView chilMotherText = helper.convertView.findViewById(R.id.chilMotherText);
        TextView chilFatherText = helper.convertView.findViewById(R.id.chilFatherText);
        TextView chilAddressText = helper.convertView.findViewById(R.id.chilAddressText);
        childNameText.setText(item.getChilName());
        chilBirthdayText.setText(item.getChilBirthday());
        chilYearText.setText(item.getChilYear() + "周岁");
        chilCardNoText.setText(item.getChilCardNo());
        chilMotherText.setText(item.getChilMother());
        chilFatherText.setText(item.getChilFather());
        chilAddressText.setText(item.getChilAddress());
    }

}