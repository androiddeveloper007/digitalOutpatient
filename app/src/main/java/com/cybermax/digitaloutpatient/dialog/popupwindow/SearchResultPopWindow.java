package com.cybermax.digitaloutpatient.dialog.popupwindow;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnKeyListener;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.PopupWindow;

import com.cybermax.digitaloutpatient.R;
import com.cybermax.digitaloutpatient.adapter.DrawerRecyclerViewAdapter;
import com.cybermax.digitaloutpatient.adapter.SearchResultRvAdapter;
import com.cybermax.digitaloutpatient.bean.Child;
import com.cybermax.digitaloutpatient.bean.Inoculation;
import com.cybermax.digitaloutpatient.bean.SearchResult;
import com.lib.tool.RxToast;
import com.lib.tool.ScreenSizeUtil;
import com.lib.views.BasePopWindow;
import com.lib.views.recyclerview.BaseQuickAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * Describe:搜索结果
 */
public class SearchResultPopWindow extends BasePopWindow {

    private final List<Child> mList;
    private SearchResultRvAdapter mAdapter;
    private keyboardNeedCloseListener mKeyboardNeedCloseListener;

    public SearchResultPopWindow(Context context, List<Child> list) {
        super(context);
        mList = list;
        if (mList.size() == 0) {
            Child bean = new Child();
            mList.add(bean);
            mList.add(bean);
            mList.add(bean);
            mList.add(bean);
            mList.add(bean);
            mList.add(bean);
            mList.add(bean);
        }
        mAdapter.setNewData(mList);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mView = mInflater.inflate(R.layout.popup_search_result, null);
        mPopWindow = new PopupWindow(mView, ScreenSizeUtil.getScreenWidth() / 3 * 2, ScreenSizeUtil.getScreenHeight());//
        mPopWindow.setOutsideTouchable(true);
        mPopWindow.setFocusable(false);//true

        mPopWindow.setOnDismissListener(new PopupWindow.OnDismissListener() {
            @Override
            public void onDismiss() {
                if(ScreenSizeUtil.isKeyboardOpen()){
                    if(mKeyboardNeedCloseListener!=null){
                        mKeyboardNeedCloseListener.keyboardNeedClose();
                    }
                }
            }
        });
//        mView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                mPopWindow.dismiss();
//            }
//        });
        RecyclerView rv = mView.findViewById(R.id.rv);
        LinearLayoutManager layoutManagerWait = new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false);
        rv.setLayoutManager(layoutManagerWait);
        mAdapter = new SearchResultRvAdapter(mContext);
        rv.setAdapter(mAdapter);
        mPopWindow.setInputMethodMode(PopupWindow.INPUT_METHOD_NEEDED);
//        mPopWindow.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);

        rv.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if(ScreenSizeUtil.isKeyboardOpen()){
                    if(mKeyboardNeedCloseListener!=null){
                        mKeyboardNeedCloseListener.keyboardNeedClose();
                    }
                }
                return false;
            }
        });
        mAdapter.setOnRecyclerViewItemClickListener(new BaseQuickAdapter.OnRecyclerViewItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                RxToast.normal(""+position);
            }
        });
    }


    public void show(View v) {
        if (mPopWindow != null && !mPopWindow.isShowing()) {
//            showAsDropDown(mPopWindow, v, 0, 0);
            mPopWindow.showAsDropDown(v, mContext.getResources().getDisplayMetrics().widthPixels / 6,
                    0, Gravity.CENTER_HORIZONTAL);//
        }
    }

    public void showAsDropDown(final View anchor, final int xoff, final int yoff, int g) {
        if (mPopWindow != null && !mPopWindow.isShowing()) {
            mPopWindow.showAsDropDown(anchor, 0, 0, g);
        }
    }

    public boolean isShowing() {
        if (mPopWindow != null) {
            return mPopWindow.isShowing();
        }
        return false;
    }

    public interface keyboardNeedCloseListener{
        void keyboardNeedClose();
    }

    public void setOnKeyboardNeedCloseListener(keyboardNeedCloseListener listener){
        this.mKeyboardNeedCloseListener = listener;
    }
}