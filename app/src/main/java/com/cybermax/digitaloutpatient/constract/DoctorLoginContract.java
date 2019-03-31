package com.cybermax.digitaloutpatient.constract;

public interface DoctorLoginContract {

    interface View extends CommonContract.BaseView {
        void onLoginSuccess();
    }
}
