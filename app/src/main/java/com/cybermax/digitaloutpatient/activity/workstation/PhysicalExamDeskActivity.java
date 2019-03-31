package com.cybermax.digitaloutpatient.activity.workstation;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;

import com.cybermax.digitaloutpatient.R;
import com.cybermax.digitaloutpatient.bean.TabEntity;
import com.cybermax.digitaloutpatient.fragment.PhysicalExamHistoryFragment;
import com.cybermax.digitaloutpatient.fragment.SetFragment;
import com.cybermax.digitaloutpatient.fragment.SimpleCardFragment;
import com.cybermax.digitaloutpatient.fragment.desk.PhysicalExamDeskFragment;
import com.flyco.tablayout.CommonTabLayout;
import com.flyco.tablayout.listener.CustomTabEntity;

import java.util.ArrayList;

/**
 * 体检台
 */
public class PhysicalExamDeskActivity extends AppCompatActivity {
    private ArrayList<CustomTabEntity> mTabEntities = new ArrayList<>();
    private String[] mTitles = {"体检台", "体检历史", "统计", "设置"};
    private ArrayList<Fragment> mFragments = new ArrayList<>();
    private int[] mIconUnselectIds = {
            R.mipmap.tab_home_unselect, R.mipmap.tab_speech_unselect,
            R.mipmap.tab_contact_unselect, R.mipmap.tab_more_unselect};
    private int[] mIconSelectIds = {
            R.mipmap.tab_home_select, R.mipmap.tab_speech_select,
            R.mipmap.tab_contact_select, R.mipmap.tab_more_select};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pretest_desk);
        CommonTabLayout tabLayout = findViewById(R.id.tl_1);
        for (int i = 0; i < mTitles.length; i++) {
            mTabEntities.add(new TabEntity(mTitles[i], mIconSelectIds[i], mIconUnselectIds[i]));
        }
        for (String title : mTitles) {
            if(TextUtils.equals("体检台", title)){
                mFragments.add(new PhysicalExamDeskFragment());
            } else if (TextUtils.equals("设置", title)) {
                mFragments.add(new SetFragment());
            } else if(TextUtils.equals("体检历史", title)){
                mFragments.add(new PhysicalExamHistoryFragment());
            }else{
                mFragments.add(SimpleCardFragment.getInstance("Switch ViewPager " + title));
            }
        }
        tabLayout.setTabData(mTabEntities, this, R.id.fl_content, mFragments);
    }
}