package com.cybermax.digitaloutpatient;

import android.app.Application;
import android.content.Context;
import android.os.Environment;
import android.support.multidex.MultiDexApplication;

import com.cybermax.digitaloutpatient.httpapi.SpeechUtilOffline;
import com.lib.LibApplication;
import com.lib.tool.CrashLogHandlerUtils;
import com.lib.tool.IMMLeaks;
import com.tencent.bugly.crashreport.CrashReport;

public class MyApplication extends MultiDexApplication {
    private static Application application;
    private static SpeechUtilOffline offline;
    public static final Thread.UncaughtExceptionHandler sUncaughtExceptionHandler = Thread
            .getDefaultUncaughtExceptionHandler();

    @Override
    public void onCreate() {
        super.onCreate();
        application = this;
        init(this);
        LibApplication.init(this,true);
        CrashReport.initCrashReport(getApplicationContext(), "4cfb0059d0", true);
        offline = new SpeechUtilOffline(this);
        //修复输入法回收导致的leak
        IMMLeaks.fixFocusedViewLeak(this);
        //恢复异常捕获的功能，第三方的sdk可能截断捕获功能导致crash等报错日志不能打印 by zzp
        Thread.setDefaultUncaughtExceptionHandler(sUncaughtExceptionHandler);
		CrashLogHandlerUtils.getInstance(this).setSavePath(Environment.getExternalStorageDirectory()).start();
    }

    public static void init(Application app){
        application = app;
//        if (LeakCanary.isInAnalyzerProcess(app)) {
//            return;
//        }
//        LeakCanary.install(application);
    }

    public static Context getCommonLibContext(){
        return application.getApplicationContext();
    }

    public static Application getCommonLibApplication(){
        return application;
    }

    public static SpeechUtilOffline getOffline() {
        return offline;
    }
}
