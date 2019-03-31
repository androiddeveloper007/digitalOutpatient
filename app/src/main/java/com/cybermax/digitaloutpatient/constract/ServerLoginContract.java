package com.cybermax.digitaloutpatient.constract;

import org.json.JSONObject;


public interface ServerLoginContract {

    interface View {
        void  makeShortToast(String text);

        void  onLoginSuccess();

        void onCheckUpdateSuccess(Object object);

        void onCheckUpdateFail(String str);

    }
}
