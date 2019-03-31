package com.cybermax.digitaloutpatient.activity.workstation;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.widget.Toast;

import com.cybermax.digitaloutpatient.R;
import com.cybermax.digitaloutpatient.bean.TabEntity;
import com.cybermax.digitaloutpatient.fragment.PretestHistoryFragment;
import com.cybermax.digitaloutpatient.fragment.desk.PretestDeskFragment;
import com.cybermax.digitaloutpatient.fragment.SetFragment;
import com.cybermax.digitaloutpatient.fragment.SimpleCardFragment;
import com.cybermax.digitaloutpatient.netty.MessageDTO;
import com.cybermax.digitaloutpatient.netty.MessageLisener;
import com.cybermax.digitaloutpatient.netty.NettyClientHandler;
import com.flyco.tablayout.CommonTabLayout;
import com.flyco.tablayout.listener.CustomTabEntity;

import java.util.ArrayList;

/**
 * 预检台
 */
public class PretestDeskActivity extends AppCompatActivity {
    private ArrayList<CustomTabEntity> mTabEntities = new ArrayList<>();
    private String[] mTitles = {"预检台", "预检历史", "统计", "设置"};
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
            if(TextUtils.equals("预检台", title)){
                mFragments.add(new PretestDeskFragment());
            }
            else if (TextUtils.equals("预检历史", title)) {
                mFragments.add(new PretestHistoryFragment());
            } else if (TextUtils.equals("设置", title)) {
                mFragments.add(new SetFragment());
            } else{
                mFragments.add(SimpleCardFragment.getInstance("Switch ViewPager " + title));
            }
        }
        tabLayout.setTabData(mTabEntities, this, R.id.fl_content, mFragments);

        NettyClientHandler.registLisener(new MessageLisener() {
            @Override
            public void onMessage(MessageDTO message) {
                Toast.makeText(PretestDeskActivity.this, "收到服务端消息:"+message.toString(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}