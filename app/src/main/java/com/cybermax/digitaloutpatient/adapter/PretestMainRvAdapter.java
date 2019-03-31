package com.cybermax.digitaloutpatient.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.cybermax.digitaloutpatient.R;
import com.cybermax.digitaloutpatient.bean.CallNumber;
import com.cybermax.digitaloutpatient.bean.Question;
import com.lib.tool.RxToast;
import com.lib.views.recyclerview.BaseQuickAdapter;
import com.lib.views.recyclerview.BaseViewHolder;

import java.util.ArrayList;
import java.util.List;

/**
 * 预检台rv的adapter
 */
public abstract class PretestMainRvAdapter extends BaseQuickAdapter<Question> {


    public PretestMainRvAdapter(Context cxt) {
        super(cxt);
    }

    public abstract  void onYesOrNoClcked(CheckBox checkBox);

    private CheckBox checkboxYes;

    private CheckBox checkboxNo;

    private CallNumber currentCallNumber;


    @Override
    protected int setItemLayout() {
        return R.layout.rv_item_activity_pretest;
    }

    @Override
    protected void convert(final BaseViewHolder helper, Question item, int position) {
        ViewGroup.LayoutParams layoutParams = helper.convertView.getLayoutParams();
        layoutParams.height = getHeight() / 10;
        helper.convertView.setLayoutParams(layoutParams);
        TextView textView = helper.convertView.findViewById(R.id.tv1);
        textView.setText(item.getQueContent());
        final List<CheckBox> checkBoxes = new ArrayList<>();
        checkboxYes = helper.convertView.findViewById(R.id.checkboxYes);
        checkBoxes.add(checkboxYes);
        checkboxNo = helper.convertView.findViewById(R.id.checkboxNo);
        checkBoxes.add(checkboxNo);

        for(CheckBox checkBox :checkBoxes){
            checkBox.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    // 先全部取消选中
                    for(CheckBox c:checkBoxes) {
                        c.setChecked(false);
                    }
                    checkBox.setChecked(true);
                    if(checkBox.getId() == R.id.checkboxYes) {
                        item.setInexResult(1);
                    } else if(checkBox.getId() == R.id.checkboxNo) {
                        item.setInexResult(0);
                    } else
                        item.setInexResult(null);
                    onYesOrNoClcked(checkBox);
                }
            });
        }
    }


}