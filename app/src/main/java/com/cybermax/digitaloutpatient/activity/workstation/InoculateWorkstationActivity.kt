package com.cybermax.digitaloutpatient.activity.workstation

import android.content.ComponentName
import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.text.TextUtils
import android.widget.Toast
import com.cybermax.digitaloutpatient.R
import com.cybermax.digitaloutpatient.bean.TabEntity
import com.cybermax.digitaloutpatient.fragment.InoculateHistoryFragment
import com.cybermax.digitaloutpatient.fragment.SetFragment
import com.cybermax.digitaloutpatient.fragment.SimpleCardFragment
import com.cybermax.digitaloutpatient.fragment.desk.InoculateDeskMainFragment
import com.flyco.tablayout.CommonTabLayout
import com.flyco.tablayout.listener.CustomTabEntity
import com.flyco.tablayout.listener.OnTabSelectListener
import org.apache.commons.lang3.StringUtils
import java.util.*

/**
 * 接种台
 */
class InoculateWorkstationActivity : AppCompatActivity() {
    private val mTabEntities = ArrayList<CustomTabEntity>()
    private var mTitles = arrayOf("接种台", "接种历史", "统计", "设置")
    private val mFragments = ArrayList<Fragment>()
    private var mIconUnselectIds = arrayOf(R.mipmap.tab_home_unselect, R.mipmap.tab_speech_unselect, R.mipmap.tab_contact_unselect, R.mipmap.tab_more_unselect)
    private var mIconSelectIds = arrayOf(R.mipmap.tab_home_select, R.mipmap.tab_speech_select, R.mipmap.tab_contact_select, R.mipmap.tab_more_select)

    override fun onCreate(bundle: Bundle?) {
        super.onCreate(bundle)
        setContentView(R.layout.activity_main)
        val tabLayout = findViewById<CommonTabLayout>(R.id.tl_1)
        //从海尔app跳转过来时，增加底部按钮并且点击时跳回海尔app
        if (intent != null && intent.hasExtra("iceBoxNm")) {
            val titleList = ArrayList(Arrays.asList(*mTitles))
            titleList.add(0, "返回首页")
            mTitles = titleList.toTypedArray()
            val unSelectedIds = ArrayList(Arrays.asList(*mIconUnselectIds))
            unSelectedIds.add(0, R.mipmap.tab_home_unselect)
            mIconUnselectIds = unSelectedIds.toTypedArray()
            val selectedIds = ArrayList(Arrays.asList(*mIconSelectIds))
            selectedIds.add(0, R.mipmap.tab_home_select)
            mIconSelectIds = selectedIds.toTypedArray()
            tabLayout.setOnTabSelectListener(object : OnTabSelectListener {
                override fun onTabSelect(position: Int) {
                    if (position == 0) {
                        val componetName = ComponentName(
                                "com.bainuosoft.vims.activitys",
                                "com.bainuosoft.vims.activitys.VimsMainActivity")
                        try {
                            val intent = Intent()
                            intent.component = componetName
                            startActivity(intent)
                        } catch (e: Exception) {
                            Toast.makeText(applicationContext, "未找到对应的程序，请检查设备。",
                                    Toast.LENGTH_LONG).show()
                        }
                    }
                }

                override fun onTabReselect(position: Int) {}
            })
        }
        for (i in mTitles.indices) {
            mTabEntities.add(TabEntity(mTitles[i], mIconSelectIds[i], mIconUnselectIds[i]))
        }
        for (title in mTitles) {
            if (TextUtils.equals("接种台", title)) {
                // FridgeAdapter
                mFragments.add(InoculateDeskMainFragment())
            } else if (TextUtils.equals("设置", title)) {
                mFragments.add(SetFragment())
            } else if (TextUtils.equals("接种历史", title)) {
                mFragments.add(InoculateHistoryFragment())
            } else if (TextUtils.equals("返回首页", title)) {
                mFragments.add(SimpleCardFragment.getInstance("正在跳转。"))
            } else {
                mFragments.add(SimpleCardFragment())
            }
        }
        tabLayout.setTabData(mTabEntities, this, R.id.fl_content, mFragments)
        tabLayout.setCurrentTab(getIndexOf("接种台"), intent.extras)
    }

    private fun getIndexOf(title: String): Int {
        for (i in mTitles.indices) {
            if (StringUtils.equals(mTitles[i], title)) {
                return i
            }
        }
        return 0
    }
}