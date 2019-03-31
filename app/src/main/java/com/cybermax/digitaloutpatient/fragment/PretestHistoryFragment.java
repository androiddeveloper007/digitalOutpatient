package com.cybermax.digitaloutpatient.fragment;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cybermax.digitaloutpatient.R;
import com.cybermax.digitaloutpatient.bean.PretestHistory;
import com.cybermax.digitaloutpatient.bean.share.Workstation;
import com.cybermax.digitaloutpatient.constract.PretestHistoryContract;
import com.cybermax.digitaloutpatient.databinding.FragmentPhysicalExamHistoryBinding;
import com.cybermax.digitaloutpatient.presenter.PretestHistoryPresenter;
import com.cybermax.digitaloutpatient.presenter.TicketPresenter;

/**
 * 预检历史记录fragment
 */
public class PretestHistoryFragment extends Fragment implements View.OnClickListener, PretestHistoryContract.View {
    FragmentPhysicalExamHistoryBinding binding;
    TicketPresenter ticketPresenter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_physical_exam_history, container, false);
        initViews();
//        ticketPresenter = new TicketPresenter(getActivity());
        PretestHistoryPresenter mPresenter = new PretestHistoryPresenter(getActivity(),this);
//        mPresenter.pretestHistory(0,0,"",0);
        return binding.getRoot();
    }

    private void initViews() {

    }

    @Override
    public void onClick(View view) {
//        switch (view.getId()) {
//
//        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onLoadSuccess(PretestHistory bean) {

    }

    @Override
    public void makeShortToast(String text) {

    }
}