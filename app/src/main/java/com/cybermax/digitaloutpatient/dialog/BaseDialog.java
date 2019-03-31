package com.cybermax.digitaloutpatient.dialog;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;

import com.cybermax.digitaloutpatient.R;

public class BaseDialog extends Dialog {
	
	@SuppressLint("InlinedApi")
	public BaseDialog(Context context) {
		super(context);
		getContext().setTheme(R.style.CustomDialogStyle);//Theme_Holo_InputMethod
	}

}