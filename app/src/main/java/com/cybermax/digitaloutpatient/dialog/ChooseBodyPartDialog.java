package com.cybermax.digitaloutpatient.dialog;

import android.content.Context;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;

import com.cybermax.digitaloutpatient.R;
import com.cybermax.digitaloutpatient.bean.BatchInfo;
import com.cybermax.digitaloutpatient.bean.Inoculation;
import com.cybermax.digitaloutpatient.constract.BaseWorkstationContract;
import com.cybermax.digitaloutpatient.enums.InoculatePlaceEnum;
import com.lib.tool.RxToast;
import com.lib.tool.ScreenSizeUtil;
import com.lib.util.DateUtil;
import com.lib.util.EmptyUtils;

import org.apache.commons.lang3.time.DateFormatUtils;

/**
 * 选择接种部位并完成接种弹框
 */
public class ChooseBodyPartDialog extends BaseDialog implements View.OnClickListener {

    private BaseWorkstationContract.OnClickListener onCommitListener;
    private Context mContext;
    private Inoculation inoculationBean;
    private CheckBox checkboxOther;
    private CheckBox checkboxMouth;
    private CheckBox checkboxLeftArm;
    private CheckBox checkboxRightArm;
    private CheckBox checkboxLeftHips;
    private CheckBox checkboxRightHips;
    private TextView vaccineCode;
    private TextView vaccineTermText;
    private TextView productCompany;
    private TextView vaccineTitle;

    public ChooseBodyPartDialog(Context context, Inoculation bean) {
        super(context);
        mContext = context;
        inoculationBean = bean;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_choose_body_part);
        getWindow().getAttributes().width = ScreenSizeUtil.getScreenWidth() / 5 * 4;
        getWindow().getAttributes().height = ScreenSizeUtil.getScreenHeight() / 6 * 5;
        getWindow().getAttributes().gravity = Gravity.CENTER;
        setCanceledOnTouchOutside(false);
        initView();
    }

    private void initView() {
        findViewById(R.id.ivClose).setOnClickListener(this);
        findViewById(R.id.commit).setOnClickListener(this);
        checkboxOther = findViewById(R.id.checkboxOther);
        checkboxMouth = findViewById(R.id.checkboxMouth);
        checkboxLeftArm = findViewById(R.id.checkboxLeftArm);
        checkboxRightArm = findViewById(R.id.checkboxRightArm);
        checkboxLeftHips = findViewById(R.id.checkboxLeftHips);
        checkboxRightHips = findViewById(R.id.checkboxRightHips);
        checkboxOther.setOnClickListener(this);
        checkboxMouth.setOnClickListener(this);
        checkboxLeftArm.setOnClickListener(this);
        checkboxRightArm.setOnClickListener(this);
        checkboxLeftHips.setOnClickListener(this);
        checkboxRightHips.setOnClickListener(this);
        findViewById(R.id.chooseBatch).setOnClickListener(this);
        if (dataError()) return;
        //字段中默认接种部位，设置到控件中，如果没有值，则默认选左臂
        selectInoculatePlace();
        //bean中的数据填入view
        vaccineCode = findViewById(R.id.vaccineCode);
        setTextValid(vaccineCode, inoculationBean.getInstBatchNo());
        //疫苗名
        vaccineTitle = findViewById(R.id.tv4);
        setTextValid(vaccineTitle, inoculationBean.getInstProdCode());
        //有效期
        vaccineTermText = findViewById(R.id.tv5);
        if (EmptyUtils.isNotEmpty(inoculationBean.getInstValidDate())) {
            vaccineTermText.setText(inoculationBean.getInstValidDate());
        } else {
            vaccineTermText.setText("");
        }
        //生产企业
        productCompany = findViewById(R.id.tv6);
        setTextValid(productCompany, inoculationBean.getCorporationName());
    }

    private void selectInoculatePlace() {
        InoculatePlaceEnum e = InoculatePlaceEnum.getByValue(inoculationBean.getInstInocPosition());
        switch (e) {
            case QITA:
                checkboxOther.setChecked(true);
                break;
            case KOUFU:
                checkboxMouth.setChecked(true);
                break;
            case ZUOSHANGBI:
                checkboxLeftArm.setChecked(true);
                break;
            case YOUSHANGBI:
                checkboxRightArm.setChecked(true);
                break;
            case ZUODATUI:
                checkboxLeftHips.setChecked(true);
                break;
            case YOUDATUI:
                checkboxRightHips.setChecked(true);
                break;
            default:
                break;
        }
    }

    private boolean dataError() {
        if (inoculationBean == null) {
            RxToast.normal("数据异常");
            return true;
        }
        return false;
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.ivClose) {
            dismiss();
        } else if (v.getId() == R.id.commit) {
            commitInoculate();
        } else if (v.getId() == R.id.checkboxOther) {
            switchChecked(checkboxOther);
        } else if (v.getId() == R.id.checkboxMouth) {
            switchChecked(checkboxMouth);
        } else if (v.getId() == R.id.checkboxLeftArm) {
            switchChecked(checkboxLeftArm);
        } else if (v.getId() == R.id.checkboxRightArm) {
            switchChecked(checkboxRightArm);
        } else if (v.getId() == R.id.checkboxLeftHips) {
            switchChecked(checkboxLeftHips);
        } else if (v.getId() == R.id.checkboxRightHips) {
            switchChecked(checkboxRightHips);
        } else if (v.getId() == R.id.chooseBatch) {
            showChooseVaccineBatchDia();
        }
    }

    //确认接种按钮
    private void commitInoculate() {
        if (onCommitListener != null && inoculationBean != null) {
            onCommitListener.onClick(inoculationBean);
        }
    }

    private void showChooseVaccineBatchDia() {
        if (dataError()) return;
        ChooseVaccineBatchDialog dialog = new ChooseVaccineBatchDialog(mContext, inoculationBean.getInstId());
        dialog.show();
        dialog.setOnCommitListener(new ChooseVaccineBatchDialog.onCommitListener() {
            @Override
            public void onCommit(BatchInfo bean) {
                //设置批次号
                setTextValid(vaccineCode, bean.getBatchNo());
                inoculationBean.setInstBatchNo(bean.getBatchNo());
                //疫苗名称
                setTextValid(vaccineTitle, bean.getBactName());
                inoculationBean.setInstProdCode(bean.getBactName());
                //设置生产企业名
                setTextValid(productCompany, bean.getCorporation());
                inoculationBean.setCorporationName(bean.getCorporation());
                //设置有效期
                String str = DateFormatUtils.format(bean.getValidDate(), DateUtil.YYYY_MM_DD);
                setTextValid(vaccineTermText, str);
                inoculationBean.setInstValidDate(str);
                //设置价格，判断价格是否相同，否则toast提示
                if (!inoculationBean.getInstPrice().equals(bean.getPrice())) {
                    RxToast.error("您选择的疫苗批次价格与登记价格不一致");
                }
            }
        });
    }

    private void switchChecked(CheckBox checkBox) {
        int id = checkBox.getId();
        switch (id) {
            case R.id.checkboxOther:
                inoculationBean.setInstInocPosition(InoculatePlaceEnum.QITA.getValue());
                break;
            case R.id.checkboxMouth:
                inoculationBean.setInstInocPosition(InoculatePlaceEnum.KOUFU.getValue());
                break;
            case R.id.checkboxLeftArm:
                inoculationBean.setInstInocPosition(InoculatePlaceEnum.ZUOSHANGBI.getValue());
                break;
            case R.id.checkboxRightArm:
                inoculationBean.setInstInocPosition(InoculatePlaceEnum.YOUSHANGBI.getValue());
                break;
            case R.id.checkboxLeftHips:
                inoculationBean.setInstInocPosition(InoculatePlaceEnum.ZUODATUI.getValue());
                break;
            case R.id.checkboxRightHips:
                inoculationBean.setInstInocPosition(InoculatePlaceEnum.YOUDATUI.getValue());
                break;
        }
        checkboxOther.setChecked(id == R.id.checkboxOther);
        checkboxMouth.setChecked(id == R.id.checkboxMouth);
        checkboxLeftArm.setChecked(id == R.id.checkboxLeftArm);
        checkboxRightArm.setChecked(id == R.id.checkboxRightArm);
        checkboxLeftHips.setChecked(id == R.id.checkboxLeftHips);
        checkboxRightHips.setChecked(id == R.id.checkboxRightHips);
    }

    public void setOnCommitListener(BaseWorkstationContract.OnClickListener onCommitListener) {
        this.onCommitListener = onCommitListener;
    }

    private void setTextValid(TextView tv, Object obj) {
        if (obj != null) {
            if (obj instanceof String)
                tv.setText((String) obj);
            else
                tv.setText(obj.toString());
        } else {
            tv.setText("");
        }
    }
}
