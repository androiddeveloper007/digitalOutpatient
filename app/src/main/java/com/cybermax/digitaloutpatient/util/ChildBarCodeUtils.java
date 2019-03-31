package com.cybermax.digitaloutpatient.util;

public class ChildBarCodeUtils {

    public static String decrypt(String str) {
        String result = str;
        int length = str.length();
        if (length == 10 || (length == 11 && str.startsWith("44"))) {
            return result;
        }
        if (length == 12 || (length == 15 && str.startsWith("321"))) {
            if (length == 15) {
                String code = dec(str.substring(3, length));
                if (code == null) {
                    return "";
                }
                result = "321" + code;
            } else {
                result = dec(str);
                if (result == null) {
                    return "";
                }
            }
        }
        return result;
    }

    private static String dec(String s) {
        String iCalcNum = "9157036248";
        String sTemp = "";
        int j = 0;
        int FKey = 0;
        try {
            if (s == null || "".equals(s) || s.length() != 12) {
                return null;
            } else {
                sTemp = s.substring(1, s.length());
                for (int i = 0; i < sTemp.length(); i++) {
                    j = j + Integer.parseInt(sTemp.charAt(i) + "");
                }
                if (Integer.parseInt(s.charAt(0) + "") != j % 10) {
                    return null;
                }
                FKey = iCalcNum.indexOf(s.charAt(8) + "") + 1 + 48;
                s = s.substring(1, 8) + s.substring(9, s.length());
                sTemp = "";
                for (int i = 0; i < s.length(); i++) {
                    j = Integer.parseInt((s.charAt(i) + "").getBytes()[0] + "") - 48;
                    if (j >= Integer.parseInt((iCalcNum.charAt(i) + ""))) {
                        j = j - Integer.parseInt((iCalcNum.charAt(i) + ""));
                    } else {
                        j = j - Integer.parseInt((iCalcNum.charAt(i) + "")) + 10;
                    }
                    j = j ^ FKey;
                    sTemp = sTemp + ((char) (j) + "");
                }
                for (int i = 0; i < 9; i++) {
                    j = Integer.parseInt((sTemp.charAt(i) + "")) - Integer.parseInt((sTemp.charAt(i + 1) + ""));
                    if (j < 0) {
                        j = j + 10;
                    }
                    sTemp = (i == 0 ? "" : sTemp.substring(0, i)) + String.valueOf(j) + sTemp.substring(i + 1, 10);
                }
                return sTemp;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static void main(String[] args) {
        System.out.println(decrypt("44021069098"));
    }
}