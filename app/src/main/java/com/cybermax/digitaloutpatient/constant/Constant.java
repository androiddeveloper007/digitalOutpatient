package com.cybermax.digitaloutpatient.constant;

/**
 * 01  取号  1  02    1
 * 02  预检  0  03    1
 * 03  登记  0  04    1
 * 04  收费  0  05    1
 * 05  接种  0  06    1
 * 06  留观  0      1
 * 07  体检  0      1
 * 08  耳鼻喉
 */
public class Constant {

    public final static String GetNumber = "01";
    public final static String Pretest = "02";
    public final static String Register = "03";
    public final static String Charge = "04";
    public final static String Inoculate = "05";
    public final static String StayObserve = "06";
    public final static String PhysicalExam = "07";
    public final static String Face = "08";


    //设备类型  01取号机 02工作台 03大屏 04小屏 05综合大屏 06留观机 07音响设备
    public final static String WstyCode_GetNumber = "01";
    public final static String WstyCode_Desk = "02";
    public final static String WstyCode_BigScreen = "03";
    public final static String WstyCode_LittleScreen = "04";
    public final static String WstyCode_UnitScreen = "05";
    public final static String WstyCode_StayObserveMachine = "06";
    public final static String WstyCode_SoundMachine = "07";

    public final static String WOST_ID = "wostId";
    public final static String PRTY_CODE = "prtyCode";
    public final static String WSTY_CODE = "wstyCode";
    public final static String WSTY_SHOW_NAME = "wostShowName";

    public final static String[] TITLES = {"候诊个案", "过号个案", "完成个案"};

}
