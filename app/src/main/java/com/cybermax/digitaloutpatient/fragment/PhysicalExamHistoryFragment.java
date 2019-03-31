package com.cybermax.digitaloutpatient.fragment;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.cybermax.digitaloutpatient.R;
import com.cybermax.digitaloutpatient.databinding.FragmentPhysicalExamHistoryBinding;

/**
 * 体检历史记录fragment
 */
public class PhysicalExamHistoryFragment extends Fragment implements View.OnClickListener {
    FragmentPhysicalExamHistoryBinding binding;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_physical_exam_history, container, false);
        initViews();
        return binding.getRoot();
    }

    private void initViews() {

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {

        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }
}