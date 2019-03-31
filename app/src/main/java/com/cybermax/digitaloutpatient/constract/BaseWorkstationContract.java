package com.cybermax.digitaloutpatient.constract;

import com.cybermax.digitaloutpatient.bean.CallNumber;
import com.cybermax.digitaloutpatient.bean.Child;
import com.cybermax.digitaloutpatient.bean.Inoculation;
import com.cybermax.digitaloutpatient.bean.QueueInfo;
import com.cybermax.digitaloutpatient.bean.Ticket;

import java.util.List;

public interface BaseWorkstationContract {
    interface View {

        /**
         * 显示叫号信息
         */
        void showCallingTicket(CallNumber callNumber);

        /**
         * 清理叫号信息
         */
        void clearCallingTicket();

        /**
         * 显示等待过号完成人数
         */
        void showQueueCount(QueueInfo queueInfo);

        /**
         * 自动顺呼开关
         * @return
         */
        boolean getSwitchCallChecked();

        /**
         * 显示右侧叫号，过号，完成列表数据
         * @param tickets
         */
        void showQueues(List<Ticket> tickets);

        /**
         *
         * @param tickets
         * @param status
         * 重新载入右侧的滑动框里的数据
         */
        void initTicketQueue(List<Ticket> tickets, int status);

    }

    interface ChildView {
        /**
         * 显示儿童列表数据
         * @param children
         */
        void showChildren(List<Child> children);

        /**
         * 显示绑定儿童的票
         * @param callNumber
         */
        void showBindTicket(CallNumber callNumber);

        /**
         * @param data
         */
        void stopScan(CallNumber data);
    }




    interface OnClickListener {
        void onClick(Inoculation inoculation);
    }
}
