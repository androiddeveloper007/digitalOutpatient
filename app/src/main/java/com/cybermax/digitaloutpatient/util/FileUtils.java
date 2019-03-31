package com.cybermax.digitaloutpatient.util;

import android.util.Base64;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.UUID;

public class FileUtils {

    private static int DEFAULT_BUFFER_SIZE = 1024 * 200;

    public static int byteArrayToInt(byte[] b) {
        return b[3] & 0xFF |
                (b[2] & 0xFF) << 8 |
                (b[1] & 0xFF) << 16 |
                (b[0] & 0xFF) << 24;
    }

    public static byte[] intToByteArray(int a) {
        return new byte[]{
                (byte) ((a >> 24) & 0xFF),
                (byte) ((a >> 16) & 0xFF),
                (byte) ((a >> 8) & 0xFF),
                (byte) (a & 0xFF)
        };
    }

    /**
     * 文件转化成base64字符串
     *
     * @param file 文件的位置
     * @return 返回Base64编码过的字节数组字符串
     */
    public static String coverFileToString(File file) {// 将文件转化为字节数组字符串，并对其进行Base64编码处理
        InputStream in = null;
        byte[] data = null;
        // 读取文件字节数组
        try {
            in = new FileInputStream(file);
            data = new byte[in.available()];
            in.read(data);
            in.close();
            // 对字节数组Base64编码
            return Base64.encodeToString(data, Base64.DEFAULT);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }

    }

    public static final int UNICODE_LEN = 2;


    /**
     * int转换为小端byte[]（高位放在高地址中）
     * @param iValue
     * @return
     */
    public static byte[] int2Bytes_LE(int iValue){
        byte[] rst = new byte[4];
        // 先写int的最后一个字节
        rst[0] = (byte)(iValue & 0xFF);
        // int 倒数第二个字节
        rst[1] = (byte)((iValue & 0xFF00) >> 8 );
        // int 倒数第三个字节
        rst[2] = (byte)((iValue & 0xFF0000) >> 16 );
        // int 第一个字节
        rst[3] = (byte)((iValue & 0xFF000000) >> 24 );
        return rst;
    }

    /**
     * int转换为小端byte[]（高位放在高地址中）
     * @param iValue
     * @return
     */
    public static byte[] int2Bytes_BE(int iValue){
        byte[] rst = new byte[4];
        // 先写int的最后一个字节
        rst[3] = (byte)(iValue & 0xFF);
        // int 倒数第二个字节
        rst[2] = (byte)((iValue & 0xFF00) >> 8 );
        // int 倒数第三个字节
        rst[1] = (byte)((iValue & 0xFF0000) >> 16 );
        // int 第一个字节
        rst[0] = (byte)((iValue & 0xFF000000) >> 24 );
        return rst;
    }


    /**
     * 转换String为byte[]
     * @param str
     * @return
     */
    public static byte[] string2Bytes_LE(String str) {
        if(str == null){
            return null;
        }
        char[] chars = str.toCharArray();

        byte[] rst =chars2Bytes_LE(chars);

        return rst;
    }



    /**
     * 转换字符数组为定长byte[]
     * @param chars              字符数组
     * @return 若指定的定长不足返回null, 否则返回byte数组
     */
    public static byte[] chars2Bytes_LE(char[] chars){
        if(chars == null)
            return null;

        int iCharCount = chars.length;
        byte[] rst = new byte[iCharCount*UNICODE_LEN];
        int i = 0;
        for( i = 0; i < iCharCount; i++){
            rst[i*2] = (byte)(chars[i] & 0xFF);
            rst[i*2 + 1] = (byte)(( chars[i] & 0xFF00 ) >> 8);
        }

        return rst;
    }




    /**
     * 转换byte数组为int（小端）
     * @return
     * @note 数组长度至少为4，按小端方式转换,即传入的bytes是小端的，按这个规律组织成int
     */
    public static int bytes2Int_LE(byte[] bytes){
        if(bytes.length < 4)
            return -1;
        int iRst = (bytes[0] & 0xFF);
        iRst |= (bytes[1] & 0xFF) << 8;
        iRst |= (bytes[2] & 0xFF) << 16;
        iRst |= (bytes[3] & 0xFF)<< 24;

        return iRst;
    }



    /**
     * 转换byte数组为int（大端）
     * @return
     * @note 数组长度至少为4，按小端方式转换，即传入的bytes是大端的，按这个规律组织成int
     */
    public static int bytes2Int_BE(byte[] bytes){
        if(bytes.length < 4)
            return -1;
        int iRst = (bytes[0] << 24) & 0xFF;
        iRst |= (bytes[1] << 16) & 0xFF;
        iRst |= (bytes[2] << 8) & 0xFF;
        iRst |= bytes[3] & 0xFF;

        return iRst;
    }



    /**
     * 转换byte数组为Char（小端）
     * @return
     * @note 数组长度至少为2，按小端方式转换
     */
    public static char bytes2Char_LE(byte[] bytes){
        if(bytes.length < 2)
            return (char)-1;
        int iRst = (bytes[0] & 0xFF);
        iRst |= (bytes[1] & 0xFF) << 8;

        return (char)iRst;
    }




    /**
     * 转换byte数组为char（大端）
     * @return
     * @note 数组长度至少为2，按小端方式转换
     */
    public static char bytes2Char_BE(byte[] bytes){
        if(bytes.length < 2)
            return (char)-1;
        int iRst = (bytes[0] << 8) & 0xFF;
        iRst |= bytes[1] & 0xFF;

        return (char)iRst;
    }

    /**
     *  小端整型转大端整型
     */
//    public static int leToBe(int val) {
//        byte[] bytes = int2Bytes_LE(val);
//        return bytes2Int_BE(bytes);
//    }

    /**
     * 大端整型转小端整型
     */
    public static int beToLe(int val) {
        byte[] bytes = int2Bytes_BE(val);
        return bytes2Int_LE(bytes);
    }

    public static byte[] changePos(byte[] buff) {
        return new byte[] {buff[3],buff[2],buff[1],buff[0]};
    }

}
