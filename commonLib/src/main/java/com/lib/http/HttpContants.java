package com.lib.http;


import com.lib.LibApplication;

/**
 * PHP请求常量
 */
public class HttpContants {

    /**
     * 服务器地址
     */
//    public static String REQUEST_URL = "";
    public static String REQUEST_URL = "http://192.168.199.110:8080/";

    public static final  String  SERVER_LOGIN ="/sysConfig/checkPassword";

    public static final String numberGeneratingMachineData = "numberGeneratingMachine/data";//取号机页面渲染数据接口
    public static final String numberGeneratingMachineSetting = "numberGeneratingMachine/setting";//取号机设置接口
    public static final String numberGenerating = "numberGenerating";//取号接口
    public static final String reservationUpload = "reservation/upload";//预约信息上传接口
    public static final String reservationUpload1 = "reservation/upload";//获取等待人数接口
    public static final String ticketActionProcedureList = "ticketAction/procedureList";//获取预检等待、过号、完成接口
    public static final String ticketActionProcedureList1 = "ticketAction/procedureList";//叫号接口
    public static final String ticketActionPassNumber = "ticketAction/passNumber";//过号接口
    public static final String childBind = "child/bind";//儿童绑定接口
    public static final String ticketActionNext = "ticketAction/next";//预检完成接口
    public static final String ticketActionProcedureList2 = "ticketAction/procedureList";//获取登记等待、过号、完成接口
    public static final String ticketActionCallNumber = "ticketAction/callNumber";//叫号接口

    public static final String inoculationUpload = "inoculation/upload";//接种记录上传接口
    public static final String inoculationUpload1 = "inoculation/upload";//登记完成接口
    public static final String inoculationUpload2 = "inoculation/upload";//获取收费等待、过号、完成接口
    public static final String inoculationUpload12 = "inoculation/upload";//确认收费接口








    //请求状态
    public static final String HTTP_OK = "1000";//成功
    public static final String HTTP_FAIL = "9999";//失败
    public static final String HTTP_LOGIN_OVERDUE = "9911";//登录过期
    public static final String HTTP_LOGIN_INVALID = "9912";//登录失效
}
