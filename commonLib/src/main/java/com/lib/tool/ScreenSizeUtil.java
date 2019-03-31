package com.lib.tool;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.Rect;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;

import com.lib.LibApplication;

import java.lang.reflect.Field;

public class ScreenSizeUtil {

    private static DisplayMetrics displayMetrics = getDisplayMetrics();

    public static int dp2px(int dp) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, displayMetrics);
    }

    public static int dp2px(float dp) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, displayMetrics);
    }

    public static int sp2px(float sp) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, sp, displayMetrics);
    }

    public static int px2dp(int px) {
        float scale = displayMetrics.density;
        return (int) (px / scale + 0.5f);
    }

    public static int px2dp(float px) {
        float scale = displayMetrics.density;
        return (int) (px / scale + 0.5f);
    }

    public static int getScreenWidth() {
        return displayMetrics.widthPixels;
    }

    public static int getScreenHeight() {
        return displayMetrics.heightPixels;
    }

    public static int[] getScreenDispaly() {
        int[] temp = new int[2];
        temp[0] = displayMetrics.widthPixels;
        temp[1] = displayMetrics.heightPixels;
        return temp;
    }

    public static DisplayMetrics getDisplayMetrics() {
        WindowManager wm = (WindowManager) LibApplication.getCommonLibContext().getSystemService(Context.WINDOW_SERVICE);
        final DisplayMetrics displayMetrics = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(displayMetrics);
        return displayMetrics;
    }

    public static int getStatusBarHeight(Activity activity) {
        Rect frame = new Rect();
        activity.getWindow().getDecorView().getWindowVisibleDisplayFrame(frame);
        return frame.top;
    }

    public static int getStatusBarHeight(Context context) {
        try {
            Class<?> c = Class.forName("com.android.internal.R$dimen");
            Object object = c.newInstance();
            Field field = c.getField("status_bar_height");
            int x = (Integer) field.get(object);
            return context.getResources().getDimensionPixelSize(x);
        } catch (Exception e) {
            return 0;
        }
    }

    public static int getWidgetWidth(View view) {
        int w = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
        int h = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
        view.measure(w, h);
        return view.getMeasuredWidth();
    }

    public static int getWidgetHeight(View view) {
        int w = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
        int h = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
        view.measure(w, h);
        return view.getMeasuredHeight();
    }

    /**
     * 置灰控件
     * @param imageview
     */
    public static void setImageViewGray(ImageView imageview) {
        ColorMatrix matrix = new ColorMatrix();
        matrix.setSaturation(0);
        ColorMatrixColorFilter filter = new ColorMatrixColorFilter(matrix);
        imageview.setColorFilter(filter);
    }

    public static int getHeightDp(Context context){
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics dm = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(dm);
        int height= dm.heightPixels; // 屏幕高度（像素）
        float density = dm.density;//屏幕密度（0.75 / 1.0 / 1.5）
        int screenHeight = (int)(height/density);//屏幕高度(dp)
        return screenHeight;
    }

    public static int getWidthDp(Context context){
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics dm = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(dm);
        float density = dm.density;//屏幕密度（0.75 / 1.0 / 1.5）
        int width = dm.widthPixels;
        int screenWidth = (int) (width/density);//屏幕宽度(dp)
        return screenWidth;
    }

    public static int getNavigationBarHeight(Context context) {
        Resources resources = context.getResources();
        int resourceId = resources.getIdentifier("navigation_bar_height","dimen", "android");
        int height = resources.getDimensionPixelSize(resourceId);
        return height;
    }
    /**
     * 强制收起软键盘
     *
     * @param view
     */
    public static void forceHideSoftInput(View view) {
        InputMethodManager imm = (InputMethodManager) LibApplication.getCommonLibContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0); //强制隐藏键盘
    }
    /***
     * 判断软键盘是否打开
     * @return
     */
    public static boolean isKeyboardOpen() {
        InputMethodManager imm = (InputMethodManager)LibApplication.getCommonLibApplication().getSystemService(Context.INPUT_METHOD_SERVICE);
        return imm.isActive();//若返回true，则表示输入法打开
    }
}