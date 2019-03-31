package com.cybermax.digitaloutpatient.httpapi;

import com.cybermax.digitaloutpatient.bean.share.ServerInfo;
import com.cybermax.digitaloutpatient.tool.SharedPreferenceUtil;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.lib.util.DateUtil;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitFactory {
    public static HttpInterface getRetrofit() {
        ServerInfo  serverInfo = (ServerInfo) SharedPreferenceUtil.getInstance().getObject(ServerInfo.class);
        Gson gson = new GsonBuilder().setDateFormat(DateUtil.YYYY_MM_DD_HH_MM_SS).create();
        Retrofit mRetrofit = new Retrofit.Builder().baseUrl(serverInfo.getServerUrl()).addConverterFactory
                (GsonConverterFactory.create(gson)).build();
        HttpInterface mService = mRetrofit.create(HttpInterface.class);
        return mService;
    }

    public static HttpInterface getRetrofit(String url) {
        Gson gson = new GsonBuilder().setDateFormat(DateUtil.YYYY_MM_DD_HH_MM_SS).create();
        Retrofit mRetrofit = new Retrofit.Builder().baseUrl(url).addConverterFactory
                                    (GsonConverterFactory.create(gson)).build();
        HttpInterface mService = mRetrofit.create(HttpInterface.class);
        return mService;
    }
}
