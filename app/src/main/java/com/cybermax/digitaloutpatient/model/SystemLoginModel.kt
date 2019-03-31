package com.cybermax.digitaloutpatient.model

import android.content.Context

import com.cybermax.digitaloutpatient.bean.share.User
import com.cybermax.digitaloutpatient.httpapi.BaseCallBack
import com.cybermax.digitaloutpatient.httpapi.HttpResult
import com.cybermax.digitaloutpatient.httpapi.RetrofitFactory
import com.lib.dialog.LoadingDialog
import com.lib.http.HttpTaskCallBack

import org.json.JSONObject

import java.util.HashMap

import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

/**
 * 系统登录
 * 包括 1.系统用户登录   2.服务器用户连接登录
 */
class SystemLoginModel : BaseModel {

    lateinit var mContext: Context

    constructor(myContext: Context):super(myContext){
        mContext = myContext
    }


    /**
     * 系统用户登录
     * @param username
     * @param pwd
     * @param mListener
     */
    fun sysUserLogin(username: String, pwd: String, mListener: HttpTaskCallBack<User>) {
        val dialog = LoadingDialog(mContext)
        dialog.show()
        val hashMap = HashMap<String, Any>()
        hashMap["username"] = username
        hashMap["userPassword"] = pwd
        //无网络时提示
        showConnectError()
        val responseBodyCall = RetrofitFactory.getRetrofit().doctorLogin(hashMap)

        responseBodyCall.enqueue(object : BaseCallBack<HttpResult<User>>(false) {
            override fun onSuccess(httpResult: HttpResult<User>) {
                mListener.onSuccess(httpResult.data)
                dialog.dismiss()
            }

            override fun onFailure(call: Call<HttpResult<User>>, t: Throwable) {
                super.onFailure(call, t)
                dialog.dismiss()
            }
        })
    }


    /**
     * 服务器连接
     * @param url
     * @param pwd
     * @param mListener
     */
    fun serverLogin(url: String, pwd: String, mListener: HttpTaskCallBack<Any>?) {
        val dialog = LoadingDialog(mContext)
        dialog.show()
        //无网络时提示
        showConnectError()
        val hashMap = HashMap<String, Any>()
        hashMap["confValue"] = pwd
        val responseBodyCall = RetrofitFactory.getRetrofit(url).checkPassword(hashMap)
        responseBodyCall.enqueue(object : Callback<ResponseBody> {

            override fun onResponse(call: Call<ResponseBody>, response: Response<ResponseBody>) {
                try {
                    assert(response.body() != null)
                    val string = String(response.body()!!.bytes())
                    val jsonObject = JSONObject(string)
                    mListener?.onSuccess(jsonObject)
                } catch (e: Exception) {
                    e.printStackTrace()
                }
                dialog.dismiss()
            }

            override fun onFailure(call: Call<ResponseBody>, t: Throwable) {
                mListener?.onFail(t.message)
                dialog.dismiss()
            }
        })
    }
}
