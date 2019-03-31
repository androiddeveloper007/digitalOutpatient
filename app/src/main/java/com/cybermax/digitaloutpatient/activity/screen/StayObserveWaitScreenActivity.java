package com.cybermax.digitaloutpatient.activity.screen;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.view.MotionEvent;
import android.view.View;

import com.cybermax.digitaloutpatient.R;
import com.cybermax.digitaloutpatient.adapter.StayObserveRvAdapter;
import com.cybermax.digitaloutpatient.bean.StayObserveScreenBean;
import com.cybermax.digitaloutpatient.bean.share.Workstation;
import com.cybermax.digitaloutpatient.constract.StayObserveScreenContract;
import com.cybermax.digitaloutpatient.databinding.ActivityStayObserveWaitBinding;
import com.cybermax.digitaloutpatient.presenter.StayObservePresenter;
import com.cybermax.digitaloutpatient.tool.SharedPreferenceUtil;
import com.lib.tool.TimeInstance;
import com.lib.tool.UIHandler;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * 留观等待屏
 */
public class StayObserveWaitScreenActivity extends BaseScreenActivity implements StayObserveScreenContract.View {
    private ActivityStayObserveWaitBinding binding;
    private StayObserveRvAdapter mAdapter;
    private StayObservePresenter mPresenter;
    private TimerHandler mHandler = new TimerHandler(this);
    private static Workstation workstation;
    private int cycleIndex = 0;//循环切换list的下标
    private List<List<StayObserveScreenBean>> listList;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_stay_observe_wait);
        binding.tv3.setText(new TimeInstance().getFormatTimeStr());
        mPresenter = new StayObservePresenter(this, this);
        workstation = (Workstation) SharedPreferenceUtil.getInstance().getObject(Workstation.class);
        registLisener(workstation);
        initRv();
        gestureCallback(binding.rvStayObserve);
        refresh(null);
    }

    private float startX, endX;
    @SuppressLint("ClickableViewAccessibility")
    protected void gestureCallback(View view) {
        view.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        startX = event.getX();
                        break;
                    case MotionEvent.ACTION_UP:
                        endX = event.getX();
                        if (startX - endX > 200) {
                            switchScreen();
                        }
                        break;
                }
                return false;
            }
        });
    }

    //singleTop启动模式下，再次打开此activity时，直接执行onNewIntent
    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
//        refresh(null);
    }

    @Override
    public void setTime() {
        binding.tv3.setText(new TimeInstance().getFormatTimeStr());
    }

    private void initRv() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        binding.rvStayObserve.setLayoutManager(layoutManager);
        mAdapter = new StayObserveRvAdapter(this);
        binding.rvStayObserve.setAdapter(mAdapter);
    }

    @Override
    public void refresh(Workstation workstation) {
        if(mPresenter!=null)
        this.mPresenter.listObservers();
    }

    @Override
    public void reloadMainView(List<StayObserveScreenBean> beanList) {
        List<StayObserveScreenBean> copyList;
        if (beanList != null) {
            copyList = new ArrayList<>(beanList);
        }else {
            copyList=new ArrayList<>();
        }
        if (copyList.size() < 6) {
            int blankcount = 6 - copyList.size();
            for (int i = 0; i < blankcount; i++) {
                StayObserveScreenBean blank = new StayObserveScreenBean();
                copyList.add(blank);
            }
        }
        //滚动方式
//            SimpleAdapter mAdapter = new SimpleAdapter(beanList,this);
//            binding.rvStayObserve.setAdapter(mAdapter);
        //循环翻页
        if (copyList.size() > 6) {
            listList = new ArrayList<>();
            int dataSize = copyList.size();
            for (int j = 0; j < dataSize / 6 + 1; j++) {
                List<StayObserveScreenBean> list = new ArrayList<>();
                int k = j * 6 + 6;
                for (int i = j * 6; i < k; i++) {
                    if(i>copyList.size()-1){
                        list.add(new StayObserveScreenBean());
                    }else{
                        list.add(copyList.get(i));
                    }
                }
                listList.add(j, list);
            }
            sendHandlerMessage(false);
        } else {
            mAdapter.setNewData(copyList);
        }
    }

    private void sendHandlerMessage(boolean delayed) {
        Message msg = new Message();
        msg.what = 1;
        Bundle bundle = new Bundle();
        bundle.putSerializable("listList", (Serializable) listList);
        msg.setData(bundle);
        if(delayed)
            mHandler.sendMessageDelayed(msg,5000);
        else
            mHandler.sendMessage(msg);
    }

    @Override
    public void reloadNoteMessage(List<String> stringList) {

    }

    //定时执行请求
    private static class TimerHandler extends UIHandler<StayObserveWaitScreenActivity> {
        TimerHandler(StayObserveWaitScreenActivity cls) {
            super(cls);
        }

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            final StayObserveWaitScreenActivity activity = ref.get();
            if (activity != null) {
                if (activity.isFinishing()) return;
                switch (msg.what) {
                    case 0:
                        postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                activity.refresh(workstation);
                            }
                        }, 30 * 1000);
                        break;
                    case 1:
                        List<List<StayObserveScreenBean>> listList =
                                (List<List<StayObserveScreenBean>>) msg.getData().getSerializable("listList");
                        activity.mAdapter.setNewData(listList.get(activity.cycleIndex++ % listList.size()));
                        activity.sendHandlerMessage(true);
                        break;
                }
            }
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mHandler.removeCallbacksAndMessages(null);
        mHandler = null;
        binding = null;
        mAdapter = null;
        mPresenter = null;
    }

    @Override
    protected void onPause() {
        super.onPause();
        if(mHandler!=null && mHandler.hasMessages(1)){
            mHandler.removeMessages(1);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(mHandler!=null && listList!=null && listList.size()>0){
            sendHandlerMessage(false);
        }
    }

    @Override
    protected void onTimmer() {
        refresh(workstation);
    }

    @Override
    protected void onStart() {
        super.onStart();
        refresh(workstation);
    }
}
