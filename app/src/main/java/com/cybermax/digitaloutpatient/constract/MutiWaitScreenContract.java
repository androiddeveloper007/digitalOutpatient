package com.cybermax.digitaloutpatient.constract;

import com.cybermax.digitaloutpatient.bean.Ticket;

import java.util.List;

public interface MutiWaitScreenContract {

    interface View {
        /**
         * 加载主区域
         * @param tickets
         */
        void reloadMainView(List<Ticket> tickets);
        /**
         * 加载等待区域
         */
        void reloadLitteSubView(String waitDisplayNo);
        /**
         * 加载等待区域
         */
        void reloadLittePassView(String passedDisplayNo);

        /**
         * 温馨提示
         * @param noticeMessages
         */
        void reloadNote(List<String> noticeMessages);
    }
}
