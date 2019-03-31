package com.cybermax.digitaloutpatient.constract;

import com.cybermax.digitaloutpatient.bean.Ticket;

import org.json.JSONObject;

import java.util.List;

public interface BigWaitScreenContract {

    interface View {
        /**
         * 刷新主页面
         * @param ticketList
         */
        void reloadMainView(List<Ticket> ticketList);

        /**
         * 刷新总过号列表
         * @param str
         */
        void reloadPassedView(String str);

        /**
         * 刷新总等待列表
         * @param str
         */
        void reloadWaitingView(String str);
        /**
         * 刷新温馨提示
         */
        void reloadNoteMessage(List<String> stringList);
    }
}
