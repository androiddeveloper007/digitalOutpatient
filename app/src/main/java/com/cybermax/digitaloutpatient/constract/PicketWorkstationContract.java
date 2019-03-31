package com.cybermax.digitaloutpatient.constract;

import com.cybermax.digitaloutpatient.bean.share.Workstation;

import java.util.List;

public interface PicketWorkstationContract {

    interface View {
        void initWorkstaionChooseView(List<Workstation> object);
        void finishActiviyAsFailed();
        void makeShortToast(String str);
    }
}
