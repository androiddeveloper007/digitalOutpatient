package com.cybermax.digitaloutpatient.adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.cybermax.digitaloutpatient.R;
import com.cybermax.digitaloutpatient.bean.Inoculation;
import com.cybermax.digitaloutpatient.constract.BaseWorkstationContract;
import com.cybermax.digitaloutpatient.dialog.ChooseBodyPartDialog;
import com.cybermax.digitaloutpatient.dialog.TakeOutVaccineDialog;
import com.cybermax.digitaloutpatient.presenter.TicketPresenter;
import com.lib.dialog.AffirmDialog;
import com.lib.util.EmptyUtils;
import com.lib.views.recyclerview.BaseQuickAdapter;
import com.lib.views.recyclerview.BaseViewHolder;

public class RecyclerViewAdapter extends BaseQuickAdapter<Inoculation> {
    private BaseWorkstationContract.OnClickListener mBatchClickListener;
    private BaseWorkstationContract.OnClickListener cancelClickListener;
    private BaseWorkstationContract.OnClickListener inoculationClickListener;


    public RecyclerViewAdapter(Context cxt) {
        super(cxt);
    }

    @Override
    protected int setItemLayout() {
        return R.layout.rv_item_activity_main;
    }

    @Override
    protected void convert(final BaseViewHolder helper, Inoculation item, int position) {
        ViewGroup.LayoutParams layoutParams = helper.convertView.getLayoutParams();
        layoutParams.height = getHeight() / 3;
        helper.convertView.setLayoutParams(layoutParams);

        //疫苗ID
        TextView vaccineTitle = helper.convertView.findViewById(R.id.vaccineTitle);
        setTextValid(vaccineTitle, item.getInstProdCode());

        //批号
        TextView batchNumberText = helper.convertView.findViewById(R.id.batchNumberText);
        setTextValid(batchNumberText, item.getInstBatchNo());

        //有效期
        TextView vaccineTermText = helper.convertView.findViewById(R.id.vaccineTermText);
        if (EmptyUtils.isNotEmpty(item.getInstValidDate())) {
            vaccineTermText.setText(item.getInstValidDate());
        } else {
            vaccineTermText.setText("");
        }

        //价格
        TextView vaccinePriceText = helper.convertView.findViewById(R.id.vaccinePriceText);
        setTextValid(vaccinePriceText, item.getInstPrice());

        //是否收费免费的图标
        ImageView iv = helper.convertView.findViewById(R.id.chargeTypeIv);

        switch (item.getInstFree()) {
            case "1":
                Glide.with(iv.getContext()).load(R.drawable.notfree).into(iv);
                break;
            case "0":
                Glide.with(iv.getContext()).load(R.drawable.free).into(iv);
                break;
            case "2":
                Glide.with(iv.getContext()).load(R.drawable.notcharge).into(iv);
                break;
        }

        //接种部位
        TextView vaccinePartText = helper.convertView.findViewById(R.id.vaccinePartText);
        setTextValid(vaccinePartText, item.getInstInocPosition());

        //接种途径
        TextView vaccineWayText = helper.convertView.findViewById(R.id.vaccineWayText);
        setTextValid(vaccineWayText, item.getInstJztjFormat());

        //弹出选择批次和接种部位的弹框
        helper.convertView.findViewById(R.id.confirmBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                inoculationClickListener.onClick(item);
//                ChooseBodyPartDialog dialog = new ChooseBodyPartDialog(helper.convertView.getContext(), item);
//                dialog.show();
//                dialog.setOnCommitListener(new ChooseBodyPartDialog.onCommitListener() {
//                    @Override
//                    public void onCommit(Inoculation checkedBean) {
//                        if (mBatchClickListener != null) {
//                            mBatchClickListener.onClick(checkedBean);
//                            dialog.dismiss();
//                        }
//                        dialog.dismiss();
//                    }
//                });
            }
        });

        helper.convertView.findViewById(R.id.cancelBtn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AffirmDialog dialog = new AffirmDialog(mContext.get());
                dialog.setAffirmDialogListenner(new AffirmDialog.AffirmDialogListenner() {
                    @Override
                    public void onCancle() {

                    }

                    @Override
                    public void onOK() {
                        cancelClickListener.onClick(item);
                    }
                });
                dialog.show();
            }
        });

        //已接种疫苗，隐藏接种按钮，显示已接种标识图标
        if (item.getInstInocState() != null) {
            helper.convertView.findViewById(R.id.confirmBtn).setVisibility(item.getInstInocState() == 1 ? View.VISIBLE : View.GONE);
            helper.convertView.findViewById(R.id.cancelBtn).setVisibility(item.getInstInocState() == 1 ? View.GONE : View.VISIBLE);
            TextView inoculateStateIcon = helper.convertView.findViewById(R.id.inoculateStateIcon);
            helper.convertView.findViewById(R.id.inoculateStateIcon).setVisibility(item.getInstInocState() == 1 ? View.GONE : View.VISIBLE);
            switch (item.getInstInocState()) {
                case 2:
                    inoculateStateIcon.setText("已接种");
                    break;
                case 3:
                    inoculateStateIcon.setText("异常");
                    break;
                case 4:
                    inoculateStateIcon.setText("延后");
                    break;
            }
        }
    }

    public void setOnBatchClickListener(BaseWorkstationContract.OnClickListener listener) {
        this.mBatchClickListener = listener;
    }

    public void setOnCancelClickListener(BaseWorkstationContract.OnClickListener listener) {
        this.cancelClickListener = listener;
    }

    public void setOnInoculationClickListener(BaseWorkstationContract.OnClickListener listener) {
        this.inoculationClickListener = listener;
    }

}