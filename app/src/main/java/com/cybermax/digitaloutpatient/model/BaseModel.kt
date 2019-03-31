package com.cybermax.digitaloutpatient.model

import android.content.Context

import com.lib.tool.RxToast
import com.lib.util.NetworkUtil

open class BaseModel {

    lateinit var context: Context

    constructor(mContext:Context){
        context = mContext
    }

    fun showConnectError() {
        //无网络时提示
        if (!NetworkUtil.isNetworkConnected()) {
            RxToast.error("网络异常，请检查后重试")
            return
        }
    }
}
