package com.cybermax.digitaloutpatient.bean.share;

import com.lib.util.EmptyUtils;

public class ServerInfo {
    private  String  ip;
    private  String  port;
    private  String  nettyPort;

    public String getIp() {
        return ip;
    }
    public void setIp(String ip) {
        this.ip = ip;
    }
    public String getPort() {
        return port;
    }

    public void setPort(String port) {
        this.port = port;
    }

    public String getNettyPort() {
        return nettyPort;
    }

    public void setNettyPort(String nettyPort) {
        this.nettyPort = nettyPort;
    }
    public String getServerUrl() {
        if (EmptyUtils.isNotEmpty(ip) && EmptyUtils.isNotEmpty(port)) {
            return "http://" + ip + ":" + port;
        }
        return null;
    }
}


