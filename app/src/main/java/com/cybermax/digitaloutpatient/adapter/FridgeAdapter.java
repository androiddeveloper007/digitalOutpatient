package com.cybermax.digitaloutpatient.adapter;

import com.cybermax.digitaloutpatient.bean.FridgeInfo;
import com.cybermax.digitaloutpatient.constract.BaseWorkstationContract;
import com.cybermax.digitaloutpatient.constract.InoculateDeskContract;

/**
 *
 */
public abstract class FridgeAdapter {

    private InoculateDeskContract.View view;

    protected FridgeInfo fridgeInfo;

    public static int fridgeType;

    /**
     * 冰箱开门
     */
    public abstract void openDoor();

    /**
     * 扫描儿童编码,验证儿童身份
     */
    public abstract void scanChildBarCode();


    /**
     * 扫描电子监管码，验证电子监管码
     */
    public abstract void scanEMCode();


    public InoculateDeskContract.View getView() {
        return view;
    }

    public void setView(InoculateDeskContract.View view) {
        this.view = view;
    }


    public FridgeInfo getFridgeInfo() {
        return fridgeInfo;
    }

    public void setFridgeInfo(FridgeInfo fridgeInfo) {
        this.fridgeInfo = fridgeInfo;
    }
}
