package com.cybermax.digitaloutpatient.dialog;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.View;

import com.alibaba.fastjson.JSON;
import com.cybermax.digitaloutpatient.R;
import com.cybermax.digitaloutpatient.adapter.ChooseVaccineBatchRvAdapter;
import com.cybermax.digitaloutpatient.bean.BatchInfo;
import com.cybermax.digitaloutpatient.model.BacthNoModel;
import com.lib.http.HttpTaskCallBack;
import com.lib.tool.ScreenSizeUtil;
import com.lib.views.recyclerview.BaseQuickAdapter;

import org.json.JSONObject;

import java.util.List;

/**
 * 选择疫苗批次
 */
public class ChooseVaccineBatchDialog extends BaseDialog implements View.OnClickListener {

    private RecyclerView rv;
    private onCommitListener mOnCommitListener;
    private Context mContext;
    private String instId;
    private ChooseVaccineBatchRvAdapter mAdapter;

    public ChooseVaccineBatchDialog(Context context, String id) {
        super(context);
        mContext = context;
        instId = id;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dialog_choose_vaccine_batch);
        getWindow().getAttributes().width = ScreenSizeUtil.getScreenWidth() / 10 * 9;
        getWindow().getAttributes().height = ScreenSizeUtil.getScreenHeight() / 5 * 4;
        getWindow().getAttributes().gravity = Gravity.CENTER;
        setCanceledOnTouchOutside(false);
        initView();
        initData();
    }

    private void initView() {
        rv = findViewById(R.id.rv);
        findViewById(R.id.ivClose).setOnClickListener(this);
        findViewById(R.id.callBtn).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.ivClose) {
            dismiss();
        } else if (v.getId() == R.id.callBtn) {
            if (mOnCommitListener != null) {
                if (mAdapter != null && mAdapter.checkedPosition != -1)
                    mOnCommitListener.onCommit((BatchInfo) mAdapter.getData().get(mAdapter.checkedPosition));
            }
            dismiss();
        }
    }

    private void initData() {
        new BacthNoModel(mContext).getBatchNo(instId, new HttpTaskCallBack<List<BatchInfo>>() {
            @Override
            public void onSuccess(List<BatchInfo> beanList) {
                LinearLayoutManager layoutManager = new LinearLayoutManager(mContext, LinearLayoutManager.VERTICAL, false);
                rv.setLayoutManager(layoutManager);
                mAdapter = new ChooseVaccineBatchRvAdapter(mContext);
                rv.setAdapter(mAdapter);
                try {
                    Log.e("sad",beanList.toString());
                    mAdapter.setNewData(beanList);
                    mAdapter.setOnRecyclerViewItemClickListener(new BaseQuickAdapter.OnRecyclerViewItemClickListener() {
                        @Override
                        public void onItemClick(View view, int position) {
                            mAdapter.setOnCheckChanged(position);
                        }
                    });
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onFail(String str) {

            }
        });
    }

    public interface onCommitListener {
        void onCommit(BatchInfo checkedBean);
    }

    public void setOnCommitListener(onCommitListener listener) {
        this.mOnCommitListener = listener;
    }
}
