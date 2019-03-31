package com.cybermax.digitaloutpatient.tool;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

import com.alibaba.fastjson.JSON;
import com.lib.LibApplication;
import com.lib.util.EmptyUtils;

public class SharedPreferenceUtil {
    private static SharedPreferenceUtil sharedPreferenceUtil;
    private static SharedPreferences sharedPreferences;

    public static final String FILENAME = LibApplication.getCommonLibContext().getPackageName();

    public static final String AUTO_CALL_BOOL = "autoCallBool"; //自动顺呼开关
    private static final String  KEY_DEVICE_TYPE="KEY_DEVICE_TYPE_0X0001";

    private SharedPreferenceUtil() {
        sharedPreferences = LibApplication.getCommonLibContext().getSharedPreferences(FILENAME, Context.MODE_PRIVATE);
    }

    public static SharedPreferenceUtil getInstance() {
        if (sharedPreferenceUtil == null) {
            synchronized (SharedPreferenceUtil.class) {
                if (sharedPreferenceUtil == null) {
                    sharedPreferenceUtil = new SharedPreferenceUtil();
                }
            }
        }
        return sharedPreferenceUtil;
    }

    public String getString(String key, String defValue) {
        return sharedPreferences.getString(key, defValue);
    }

    public void putString(String key, String value) {
        Editor editor = sharedPreferences.edit();
        if (value == null) {
            editor.putString(key, "");
        } else {
            editor.putString(key, value);
        }
        editor.apply();
    }

    public boolean getBoolean(String key, boolean value) {
        return sharedPreferences.getBoolean(key, value);
    }

    public void putBoolean(String key, boolean value) {
        Editor editor = sharedPreferences.edit();
        editor.putBoolean(key, value);
        editor.apply();
    }

    public void putInt(String key, int value) {
        Editor editor = sharedPreferences.edit();
        editor.putInt(key, value);
        editor.apply();
    }

    public void remove(String key) {
        Editor editor = sharedPreferences.edit();
        editor.remove(key);
        editor.apply();
    }

    public int getInt(String key) {
        return sharedPreferences.getInt(key, 0);
    }


    public void clearAll(Context c) {
        SharedPreferences shared = c.getSharedPreferences(FILENAME, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = shared.edit();
        editor.clear();
        editor.apply();
    }
    public boolean  removeObject(Class clazz){
        try {
            Editor editor = sharedPreferences.edit();
            String key = clazz.getName();
            editor.remove(key);
            editor.apply();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
    public boolean  putObject(Object  obj){
        try {
            String key = obj.getClass().getName();
            String jsonString = JSON.toJSONString(obj);
            this.putString(key, jsonString);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public Object  getObject(Class  clazz){
        try {
            String key = clazz.getName();
            String jsonString =this.getString(key, "");
            return JSON.parseObject(jsonString, clazz);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean  putDeviceType(String  type){
        try {
            this.putString(KEY_DEVICE_TYPE, type);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
    public String  getDeviceType(){
        return  this.getString(KEY_DEVICE_TYPE, "");
    }
}
