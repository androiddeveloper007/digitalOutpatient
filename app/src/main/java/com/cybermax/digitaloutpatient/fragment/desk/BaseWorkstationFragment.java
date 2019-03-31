package com.cybermax.digitaloutpatient.fragment.desk;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;

import com.cybermax.digitaloutpatient.R;
import com.cybermax.digitaloutpatient.activity.BarcodeScanActivity;
import com.cybermax.digitaloutpatient.activity.SearchUserActivity;
import com.cybermax.digitaloutpatient.adapter.DrawerRecyclerViewAdapter;
import com.cybermax.digitaloutpatient.bean.CallNumber;
import com.cybermax.digitaloutpatient.bean.QueueInfo;
import com.cybermax.digitaloutpatient.bean.Ticket;
import com.cybermax.digitaloutpatient.bean.share.User;
import com.cybermax.digitaloutpatient.bean.share.Workstation;
import com.cybermax.digitaloutpatient.constant.Constant;
import com.cybermax.digitaloutpatient.constract.BaseWorkstationContract;
import com.cybermax.digitaloutpatient.databinding.FragmentDeskMainBinding;
import com.cybermax.digitaloutpatient.dialog.popupwindow.SearchResultPopWindow;
import com.cybermax.digitaloutpatient.enums.ProcdureEnum;
import com.cybermax.digitaloutpatient.enums.TicketActionStatusEnum;
import com.cybermax.digitaloutpatient.enums.WorkStationTypeEnum;
import com.cybermax.digitaloutpatient.presenter.TicketPresenter;
import com.cybermax.digitaloutpatient.tool.SharedPreferenceUtil;
import com.flyco.tablayout.listener.OnTabSelectListener;
import com.lib.tool.ClickFilter;
import com.lib.tool.RxToast;

import org.apache.commons.lang3.StringUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 接种台主界面
 */
public abstract class BaseWorkstationFragment extends Fragment implements BaseWorkstationContract.View, View.OnClickListener {

    protected Workstation workstation;
    protected User user;
    protected TicketPresenter presenter;
    protected CallNumber currentCallNumber;
    protected int ticketActionStatus = TicketActionStatusEnum.WAITING.getValue();
    protected int drawerTabIndex;
    protected ActionBarDrawerToggle mDrawerToggle;
    private FragmentDeskMainBinding binding;
    private SearchResultPopWindow popup;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_desk_main, container, false);
        workstation = (Workstation) SharedPreferenceUtil.getInstance().getObject(Workstation.class);
        user = (User) SharedPreferenceUtil.getInstance().getObject(User.class);
        presenter = new TicketPresenter(getActivity(), this, workstation, user);
        View view = binding.getRoot();
        //    x.view().inject(view);
        // 注入view
        initViews();
        initDrawerData();
        initMainView(binding, container, inflater);
        return view;
    }


    protected abstract void initMainView(FragmentDeskMainBinding binding, ViewGroup container, LayoutInflater inflater);


    /**
     * 初始化中间哪一块的内容，体检台，收费台逻辑不相同
     */

    public void displayCallingView(CallNumber bean) {
        currentCallNumber = bean;
        binding.callNumText.setText(bean.getTicketDisplayNo());
        binding.childNameText.setText(bean.getChilName());
        binding.sexText.setText(bean.getChilSex());
        binding.ivSex.setImageResource(R.drawable.svg_male);
        if (bean.getChilSex() == null)
            binding.ivSex.setImageResource(R.drawable.svg_male);
        else
            binding.ivSex.setImageResource(TextUtils.equals("男", bean.getChilSex()) ? R.drawable.svg_male : R.drawable.svg_female);
        binding.birthText.setText(bean.getChilBirthday());
        binding.fatherNameText.setText(bean.getChilFather());
        binding.matherNameText.setText(bean.getChilMother());
        binding.chilNoText.setText(bean.getChilNo());
        binding.chilCardNoText.setText(bean.getChilCardNo());
    }

    /**
     * 初始化页面
     */
    private void initViews() {
        binding.complete.setOnClickListener(this);
        binding.callBtn.setOnClickListener(this);
        binding.passBtn.setOnClickListener(this);
        binding.pauseBtn.setOnClickListener(this);
        binding.callAgainBtn.setOnClickListener(this);
        binding.backBtn.setOnClickListener(this);
        binding.autoCallTitle.setOnClickListener(this);
        binding.scanLayout.setOnClickListener(this);
        binding.switchAutoCall.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                SharedPreferenceUtil.getInstance().putBoolean(SharedPreferenceUtil.AUTO_CALL_BOOL, b);
            }
        });
        mDrawerToggle = new ActionBarDrawerToggle(getActivity(), binding.drawerLayout, R.string.drawer_open, R.string.drawer_close) {
            @Override
            public void onDrawerOpened(View drawerView) {
                ticketActionStatus = TicketActionStatusEnum.WAITING.getValue();
                presenter.reloadTicketQueue(ticketActionStatus);//0,等待中1,过号，2完成
            }
        };
        binding.drawerLayout.setDrawerListener(mDrawerToggle);
        binding.switchAutoCall.setChecked(SharedPreferenceUtil.getInstance().getBoolean(SharedPreferenceUtil.AUTO_CALL_BOOL, false));

        initWorkstationInfo();
        initDrawerData();
//        gestureCallback();
        if(StringUtils.equals(workstation.getPrtyCode(),ProcdureEnum.JIEZHONG.getValue())){
            binding.editSearch.setVisibility(View.INVISIBLE);
            binding.scanLayout.setVisibility(View.INVISIBLE);
        }
        binding.editSearch.setOnClickListener(this);
        presenter.getProcessingTicket(workstation.getId());
    }


    /////////////////////////右侧滑块 start////////////////////////
    private void initDrawerData() {
        binding.tl2.setTabData(Constant.TITLES);
        binding.tl2.setOnTabSelectListener(new OnTabSelectListener() {
            @Override
            public void onTabSelect(int position) {
                switchVisible(position);
                drawerTabIndex = position;
            }

            @Override
            public void onTabReselect(int position) {

            }
        });
    }


    private float startX, endX;

    @SuppressLint("ClickableViewAccessibility")
    public void gestureCallback(View v) {
        v.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        startX = event.getX();
                        break;
                    case MotionEvent.ACTION_UP:
                        endX = event.getX();
                        if (startX - endX > 200) {
                            if (!binding.drawerLayout.isDrawerOpen(binding.llDrawerRight)) {
                                binding.drawerLayout.openDrawer(binding.llDrawerRight);
                            }
                        }
                        break;
                }
                return false;
            }
        });
    }


    private void switchVisible(int position) {
        binding.drawerRvWait.setVisibility(position == 0 ? View.VISIBLE : View.GONE);
        binding.drawerRvPassed.setVisibility(position == 1 ? View.VISIBLE : View.GONE);
        binding.drawerRvFinished.setVisibility(position == 2 ? View.VISIBLE : View.GONE);
        switch (position) {
            case 0:
                ticketActionStatus = TicketActionStatusEnum.WAITING.getValue();
                binding.validTermTitleDrawer.setText("提示");
                binding.handleTitle.setText("操作");
                binding.handleTitle.setVisibility(View.VISIBLE);
                break;
            case 1:
                ticketActionStatus = TicketActionStatusEnum.PASSED.getValue();
                binding.validTermTitleDrawer.setText("过号时间");
                binding.handleTitle.setText("操作");
                binding.handleTitle.setVisibility(View.VISIBLE);
                break;
            case 2:
                ticketActionStatus = TicketActionStatusEnum.COMPLETED.getValue();
                binding.validTermTitleDrawer.setText("完成时间");
                binding.handleTitle.setText("");
                binding.handleTitle.setVisibility(View.GONE);
                break;
        }
        presenter.reloadTicketQueue(ticketActionStatus);//0,等待中1,过号，2完成
    }

    /////////////////////////右侧滑块 end////////////////////////

    @Override
    public void initTicketQueue(List<Ticket> tickets, int status) {
        RecyclerView recyclerView = null;
        try {
            switch (status) {
                case 0:
                    recyclerView = binding.drawerRvWait;
                    break;
                case 2:
                    recyclerView = binding.drawerRvPassed;
                    break;
                case 3:
                    recyclerView = binding.drawerRvFinished;
                    break;
            }
            LinearLayoutManager layoutManagerWait = new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false);
            recyclerView.setLayoutManager(layoutManagerWait);
            DrawerRecyclerViewAdapter mAdapterWait = new DrawerRecyclerViewAdapter(getActivity(), status);
            recyclerView.setAdapter(mAdapterWait);
            if (tickets != null && tickets.size() > 0) {
                mAdapterWait.setNewData(tickets);
            } else {
                View emptyView = getLayoutInflater().inflate(R.layout.rv_empty_view,
                        getActivity().findViewById(android.R.id.content), false);
                mAdapterWait.setEmptyView(emptyView);
            }
            mAdapterWait.setOnCallNumBtnListener(new DrawerRecyclerViewAdapter.onCallNumBtnListener() {
                @Override
                public void callNumBtn(int position) {
                    presenter.callNumber(workstation.getId(), ((Ticket) mAdapterWait.getData().get(position)).getId());
                    binding.drawerLayout.closeDrawers();
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * 显示工作台信息
     */
    private void initWorkstationInfo() {
        binding.vaccinateState.setText(workstation.getWostShowName());
        binding.doctorName.setText(user.getUserTrueName());
    }


    /**
     * 显示正在呼叫的儿童的信息
     *
     * @param callNumber
     */
    @Override
    public void showCallingTicket(CallNumber callNumber) {
        if (null != callNumber && callNumber.isRecover()) {
            RxToast.warning("请先处理未完成的儿童");
        }
        //展示左侧儿童数据之前，用于对数据进行处理
        beforeCallingViewDisplay(callNumber);
        //展示左侧儿童数据
        displayCallingView(callNumber);
        //展示左侧数据完成之后，主要用于展示中间数据
        afterCallingViewDisplay(callNumber);
    }

    protected abstract void afterCallingViewDisplay(CallNumber callNumber);

    protected abstract void beforeCallingViewDisplay(CallNumber callNumber);

    /**
     * 显示等待，完成，过号人数
     *
     * @param queueInfo
     */
    @Override
    public void showQueueCount(QueueInfo queueInfo) {
        binding.waitPeopleCount.setText("等待：" + (queueInfo != null ? queueInfo.getWaitingCount() : 0));
        binding.passPeopleCount.setText("过号：" + (queueInfo != null ? queueInfo.getPassCount() : 0));
        binding.finishPeopleCount.setText("完成：" + (queueInfo != null ? queueInfo.getCompletedCount() : 0));
    }

    /**
     * 获取正在自动呼叫开关
     *
     * @return
     */
    @Override
    public boolean getSwitchCallChecked() {
        return binding.switchAutoCall.isChecked();
    }

    /**
     * 清理儿童信息
     */
    @Override
    public void clearCallingTicket() {
        // 清理左侧
        binding.callNumText.setText("未呼叫");
        binding.sexText.setText("");
        binding.birthText.setText("");
        binding.childNameText.setText("");
        binding.fatherNameText.setText("");
        binding.chilNoText.setText("");
        binding.chilCardNoText.setText("");
        binding.matherNameText.setText("");
        currentCallNumber = null;
        afterClearCallingTicket();
    }

    protected void afterClearCallingTicket() {

    }

    ;

    /**
     * @param tickets
     */
    @Override
    public void showQueues(List<Ticket> tickets) {

    }

    /**
     * 呼叫完成按钮被点击
     */
    protected void onBtnCompleteClick() {
        if (null == currentCallNumber) {
            RxToast.error("请先呼叫儿童");
            return;
        }
        if(ClickFilter.isMultiClick()) return;
        onBtnComplete();
    }

    protected void onBtnComplete() {
    }

    /**
     * 呼叫按钮被点击
     */
    protected void onBtnCallClick() {
        if(ClickFilter.isMultiClick()) return;
        presenter.callNumber(workstation.getId(), null);
    }


    /**
     * 呼叫按钮被点击
     */
    protected void onBtnPassClick() {
        if (null == currentCallNumber) {
            RxToast.error("请先呼叫儿童");
            return;
        }
        if(ClickFilter.isMultiClick()) return;
        onBtnPass();
    }

    protected void onBtnPass() {
    }


    /**
     * 重复呼叫按钮被点击
     */
    protected void onBtnCallAgainClick() {
        if (null == currentCallNumber) {
            RxToast.error("请先呼叫儿童");
            return;
        }
        if(ClickFilter.isMultiClick()) return;
        onBtnCallAgain();
    }

    protected void onBtnCallAgain() {
    }


    /**
     * 重复呼叫按钮被点击
     */
    protected void onBtnBackClick() {
        if(ClickFilter.isMultiClick()) return;

    }

    /**
     * 扫一扫按钮被点击
     */
    protected void onScanLayoutClick() {
        if (null == currentCallNumber) {
            RxToast.error("请先呼叫儿童");
            return;
        }
        Intent intent = new Intent(getActivity(), BarcodeScanActivity.class);
        intent.putExtra("ticketId",currentCallNumber.getId());
        startActivityForResult(intent,1000);
    }

    /**
     * 搜索区域被点击
     */
    protected void onSearchLayoutClick() {
        if (null == currentCallNumber) {
            RxToast.error("请先呼叫儿童");
            return;
        }
        Intent intent = new Intent(getActivity(), SearchUserActivity.class);
        intent.putExtra("ticketId",currentCallNumber.getId());
        startActivityForResult(intent,1000);
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.complete:
                onBtnCompleteClick();
                break;
            case R.id.callBtn:  //呼叫按钮
                onBtnCallClick();
                break;
            case R.id.passBtn: //过号
                onBtnPassClick();
                break;
            case R.id.callAgainBtn: //重新呼叫
                onBtnCallAgainClick();
                break;
            case R.id.backBtn:
                onBtnBackClick();
                break;
            case R.id.autoCallTitle:
                binding.switchAutoCall.setChecked(!binding.switchAutoCall.isChecked());
                break;
            case R.id.scanLayout:
                onScanLayoutClick();
                break;
            case R.id.editSearch:
                onSearchLayoutClick();
                break;
        }
    }


    public Map<String, Object> generateOperator() {
        Map<String, Object> operator = new HashMap<>();
        operator.put("wostId", workstation.getId());
        operator.put("tiacDoctor", user.getUserTrueName());
        if (null != currentCallNumber.getTiacId())
            operator.put("tiacId", currentCallNumber.getTiacId());
        return operator;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (!hidden) {
            binding.etScan.requestFocus();
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(resultCode == 1000) {
            CallNumber callNumber = (CallNumber) data.getSerializableExtra("callNumber");
            if(null == callNumber)
                return;
            this.currentCallNumber = callNumber;
            beforeCallingViewDisplay(callNumber);
            //展示左侧儿童数据
            displayCallingView(callNumber);
            //展示左侧数据完成之后，主要用于展示中间数据
            afterCallingViewDisplay(callNumber);
        }
    }

}