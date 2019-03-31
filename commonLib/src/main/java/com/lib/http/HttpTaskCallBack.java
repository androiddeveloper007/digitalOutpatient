package com.lib.http;


import com.lib.tool.RxToast;

import org.apache.commons.lang3.StringUtils;

/**
 * 调用接口的回调
 * @author junhez
 *
 */
public abstract class HttpTaskCallBack<T> {

	public abstract void onSuccess(T data);
	
	public void onFail(String str) {
		if(StringUtils.isNotEmpty(str))
			RxToast.error(str);
		else
			RxToast.error("业务异常");
	}

//	public void onFail(T data) {
//		RxToast.error("业务异常");
//	}

}
