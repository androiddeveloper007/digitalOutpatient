package com.lib.dialog;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.lib.util.DialogManagerUtil;

public class BaseDialog extends Dialog implements View.OnClickListener{

    public Context mContext;

    public BaseDialogListener mListener;


    public interface BaseDialogListener {
        void onDataCallBack(Object... obj);
    }


    public void setBaseDialogListener(BaseDialogListener listener){
        mListener = listener;
    }

    public BaseDialog(Context context) {
        super(context);
        this.mContext = context;
    }

    public BaseDialog(Context context, int themeResId) {
        super(context, themeResId);
        this.mContext = context;
    }

    public BaseDialog(Context context, boolean cancelable, OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
        this.mContext = context;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onClick(View v) {

    }

    @Override
    public void show() {
        super.show();
        DialogManagerUtil.getInstance().add(this);
    }

    @Override
    public void dismiss() {
        if (isShowing()) {
            DialogManagerUtil.getInstance().remove(this);
        }
        super.dismiss();
    }
}
