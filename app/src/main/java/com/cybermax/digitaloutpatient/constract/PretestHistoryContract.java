package com.cybermax.digitaloutpatient.constract;

import com.cybermax.digitaloutpatient.bean.PretestHistory;

public interface PretestHistoryContract {

    interface View extends CommonContract.BaseView{
         void  onLoadSuccess(PretestHistory bean);
    }
}
