package com.lib.views.recyclerview;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.LinearSmoothScroller;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.util.Log;

/**
 * Created by lip on 2017/2/24.
 * 控制recycler滚动速度manager
 */

public class RvAutoScrollLinearLayoutManager extends LinearLayoutManager {

    /**
     * 滚动完成回调
     */
    private StopScrollCallBack outStopScrollCallBack;

    /**
     * 滚动速度
     */
    private float speed;

    /**
     *
     * @param context 上下文
     * @param stopScrollCallBack 滚动完成回调
     * @param speed 滚动速度
     */
    public RvAutoScrollLinearLayoutManager(Context context, StopScrollCallBack stopScrollCallBack, float speed) {
        super(context);
        this.outStopScrollCallBack = stopScrollCallBack;
        this.speed = speed;
    }

    @Override
    public void smoothScrollToPosition(RecyclerView recyclerView, RecyclerView.State state, final int position) {

        LinearSmoothScroller linearSmoothScroller = new LinearSmoothScroller(recyclerView.getContext()) {

            @Override
            protected float calculateSpeedPerPixel(DisplayMetrics displayMetrics) {
                return speed;//返回滚过1px需要多少ms
            }

            @Override
            protected void onStop() {
                super.onStop();
                Log.e("ZZP", "onStop"+position);
                outStopScrollCallBack.scrollStop(position);
            }

        };
        linearSmoothScroller.setTargetPosition(position);
        startSmoothScroll(linearSmoothScroller);
    }

    public interface StopScrollCallBack {
        void scrollStop(int position);
    }

}
