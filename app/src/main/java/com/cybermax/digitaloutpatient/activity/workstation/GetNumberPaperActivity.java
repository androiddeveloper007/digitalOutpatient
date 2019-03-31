package com.cybermax.digitaloutpatient.activity.workstation;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.cybermax.digitaloutpatient.databinding.ActivityGetnumberpaperBinding;
import com.cybermax.digitaloutpatient.R;

/**
 * 取号台（暂时不做）
 */
public class GetNumberPaperActivity extends AppCompatActivity {
    ActivityGetnumberpaperBinding binding;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_getnumberpaper);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_getnumberpaper);
        binding.appointCardText.setText("12");
        binding.inoculateCardText.setText("12");
        binding.physicalExamCardText.setText("12");
        binding.fiveSensesDepartmentCardText.setText("12");
    }
}
