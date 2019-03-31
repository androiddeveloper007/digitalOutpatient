package com.cybermax.digitaloutpatient.constract;

import com.cybermax.digitaloutpatient.bean.StayObserveScreenBean;
import com.cybermax.digitaloutpatient.bean.Ticket;

import org.json.JSONObject;

import java.util.List;

/**
 * 留观屏constract
 */

public interface StayObserveScreenContract {

    interface View {
        /**
         * 刷新主页面
         */
        void reloadMainView(List<StayObserveScreenBean> observeScreenBeans);

        /**
         * 刷新温馨提示
         */
        void reloadNoteMessage(List<String> stringList);
    }
}
