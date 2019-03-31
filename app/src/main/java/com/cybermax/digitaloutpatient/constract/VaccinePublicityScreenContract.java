package com.cybermax.digitaloutpatient.constract;

import com.cybermax.digitaloutpatient.bean.Ticket;

import java.util.List;

public interface VaccinePublicityScreenContract {

    interface View {
        /**
         * 刷新主页面
         * @param ticketList
         */
        void reloadMainView(List<Ticket> ticketList);

    }
}
