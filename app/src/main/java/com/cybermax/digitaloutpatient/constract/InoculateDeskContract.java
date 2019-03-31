package com.cybermax.digitaloutpatient.constract;

import com.cybermax.digitaloutpatient.bean.CallNumber;
import com.cybermax.digitaloutpatient.bean.Inoculation;
import com.cybermax.digitaloutpatient.bean.QueueInfo;
import com.cybermax.digitaloutpatient.bean.Ticket;
import com.cybermax.digitaloutpatient.bean.VacccrkBarcode;

import java.util.List;


public interface InoculateDeskContract {

    interface View extends BaseWorkstationContract.View{

        /**
         * 确认接种时进行刷新
         */
        void refreshInoculations(Inoculation inoculation,boolean startNext);
    }

    interface DialogView {
        void validateCode(VacccrkBarcode vacccrkBarcode);
        void verifyFailed(String msg);
    }
}
