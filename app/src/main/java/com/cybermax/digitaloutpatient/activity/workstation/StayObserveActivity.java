package com.cybermax.digitaloutpatient.activity.workstation;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;

import com.cybermax.digitaloutpatient.R;
import com.cybermax.digitaloutpatient.bean.TabEntity;
import com.cybermax.digitaloutpatient.fragment.desk.InoculateDeskMainFragment;
import com.cybermax.digitaloutpatient.fragment.SetFragment;
import com.cybermax.digitaloutpatient.fragment.SimpleCardFragment;
import com.flyco.tablayout.CommonTabLayout;
import com.flyco.tablayout.listener.CustomTabEntity;

import java.util.ArrayList;

/**
 * 留观台（暂时不做）
 */
public class StayObserveActivity extends AppCompatActivity {
    private ArrayList<CustomTabEntity> mTabEntities = new ArrayList<>();
    private String[] mTitles = {"首页", "消耗", "统计", "设置"};
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
        setContentView(R.layout.activity_stay_observe);
        CommonTabLayout tabLayout = findViewById(R.id.tl_1);
        for (int i = 0; i < mTitles.length; i++) {
            mTabEntities.add(new TabEntity(mTitles[i], mIconSelectIds[i], mIconUnselectIds[i]));
        }
        for (String title : mTitles) {
            if(TextUtils.equals("首页", title)){
                mFragments.add(new InoculateDeskMainFragment());
            } else if (TextUtils.equals("设置", title)) {
                mFragments.add(new SetFragment());
            } else{
                mFragments.add(SimpleCardFragment.getInstance("Switch ViewPager " + title));
            }
        }
        tabLayout.setTabData(mTabEntities, this, R.id.fl_content, mFragments);
    }
}