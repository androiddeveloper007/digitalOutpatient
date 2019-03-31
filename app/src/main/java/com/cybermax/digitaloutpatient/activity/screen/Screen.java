package com.cybermax.digitaloutpatient.activity.screen;

import com.cybermax.digitaloutpatient.bean.share.Workstation;
import com.cybermax.digitaloutpatient.netty.MessageDTO;

public interface Screen {
    void  refresh(Workstation workstation);
    void  registLisener(Workstation workstation);
    void  onServerMessage(Workstation workstation ,MessageDTO messageDTO);
    void  switchScreen();
}
