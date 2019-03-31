package com.lib;

import android.app.Application;
import android.content.Context;

import com.lib.tool.RxUtils;

/**
 * Created by zhuzhipeng on 2018/11/30.
 */

public class LibApplication {

    private static Application application;

    public static boolean DEBUG = true;

    public static void init(Application app, boolean debug) {
        application = app;
        DEBUG = debug;
        RxUtils.init(app);
    }

    public static Context getCommonLibContext() {
        return application.getApplicationContext();
    }

    public static Application getCommonLibApplication() {
        return application;
    }
//
//    public static void setHttpUrl(String httpUrl) {
//        HttpContants.REQUEST_URL = httpUrl;
//    }
}
