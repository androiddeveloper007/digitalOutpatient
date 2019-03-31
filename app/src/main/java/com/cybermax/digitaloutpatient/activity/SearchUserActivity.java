package com.cybermax.digitaloutpatient.activity;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.TextView;

import com.cybermax.digitaloutpatient.R;
import com.cybermax.digitaloutpatient.adapter.SearchResultRvAdapter;
import com.cybermax.digitaloutpatient.bean.CallNumber;
import com.cybermax.digitaloutpatient.bean.Child;
import com.cybermax.digitaloutpatient.bean.SearchResult;
import com.cybermax.digitaloutpatient.bean.share.User;
import com.cybermax.digitaloutpatient.bean.share.Workstation;
import com.cybermax.digitaloutpatient.constract.BaseWorkstationContract;
import com.cybermax.digitaloutpatient.databinding.ActivitySearchUserBinding;
import com.cybermax.digitaloutpatient.fragment.desk.BaseWorkstationFragment;
import com.cybermax.digitaloutpatient.presenter.ChildPresenter;
import com.cybermax.digitaloutpatient.presenter.ServerLoginPresenter;
import com.cybermax.digitaloutpatient.tool.SharedPreferenceUtil;
import com.lib.dialog.AffirmDialog;
import com.lib.tool.RxToast;
import com.lib.tool.ScreenSizeUtil;
import com.lib.util.ApplicationUtils;
import com.lib.views.recyclerview.BaseQuickAdapter;

import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * 搜索用户界面
 */
public class SearchUserActivity extends AppCompatActivity implements BaseWorkstationContract.ChildView,View.OnClickListener{
    ActivitySearchUserBinding binding;
    private SearchResultRvAdapter mAdapter;
    private ChildPresenter childPresenter;
    private Workstation workstation;
    private User user;
    private List<Child> children;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_search_user);
        workstation = (Workstation) SharedPreferenceUtil.getInstance().getObject(Workstation.class);
        user = (User)SharedPreferenceUtil.getInstance().getObject(User.class);
        childPresenter = new ChildPresenter(this,this, workstation, user);
        initView();
    }

    private void initView() {
        createRecyclerView();
        binding.editSearch.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    String keywords = v.getText().toString();
                    if(StringUtils.isNotBlank(keywords)) {
                        childPresenter.listChildren(keywords);
                        ApplicationUtils.closeKeyboard(v);
                    } else {
                        RxToast.error("搜索内容不能为空");
                    }
                    return true;
                }
                return false;
            }
        });


        binding.cancelBtn.setOnClickListener(this);
        binding.backIcon.setOnClickListener(this);
        binding.rv.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if(ScreenSizeUtil.isKeyboardOpen()){
                    ScreenSizeUtil.forceHideSoftInput(view);
                }
                return false;
            }
        });
    }

    private void createRecyclerView() {
        LinearLayoutManager layoutManagerWait = new LinearLayoutManager(SearchUserActivity.this);
        binding.rv.setLayoutManager(layoutManagerWait);
        mAdapter = new SearchResultRvAdapter(SearchUserActivity.this);
        binding.rv.setAdapter(mAdapter);
        Context context = this;
        mAdapter.setOnRecyclerViewItemClickListener(new BaseQuickAdapter.OnRecyclerViewItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                AffirmDialog dialog = new AffirmDialog(context, "确认绑定该儿童？");
                dialog.setAffirmDialogListenner(new AffirmDialog.AffirmDialogListenner() {
                    @Override
                    public void onCancle() {}
                    @Override
                    public void onOK() {
                        int ticketId = getIntent().getIntExtra("ticketId", -1);
                        if(ticketId == -1) {
                            RxToast.error("ticketId不能为空");
                            return;
                        }
                        childPresenter.bindChild(ticketId, children.get(position).getChilNo(), null,workstation.getId());
                    }
                });
                dialog.show();
            }
        });
    }

    @Override
    public void onClick(View view) {
        switch(view.getId()){
            case R.id.cancelBtn:
                finish();
                break;
            case R.id.backIcon:
                ApplicationUtils.closeKeyboard(binding.editSearch);
                finish();
                break;
        }
    }

    @Override
    public void showChildren(List<Child> children) {
        this.children = children;
        if(null == children) {
            this.children = new ArrayList<>();
        }
        mAdapter.setNewData(this.children);
    }

    @Override
    public void showBindTicket(CallNumber callNumber) {
        Intent intent = new Intent(this,BaseWorkstationFragment.class);
        intent.putExtra("callNumber",callNumber);
        setResult(1000, intent);
        finish();
    }

    @Override
    public void stopScan(CallNumber data) {

    }
}
