package com.cybermax.digitaloutpatient.adapter

import android.content.Context
import android.graphics.Color
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.cybermax.digitaloutpatient.R
import com.cybermax.digitaloutpatient.bean.StayObserveScreenBean

/**
 * 描述：RecyclerView的Adapter
 */
class SimpleAdapter(list: MutableList<StayObserveScreenBean>, mContext: Context) : RecyclerView.Adapter<SimpleAdapter.SimpleAdapterViewHolder>() {

    /**数据源*/
    private var list: MutableList<StayObserveScreenBean> = list
    /**上下文*/
    private var mContext: Context = mContext

    override fun onCreateViewHolder(p0: ViewGroup, p1: Int): SimpleAdapterViewHolder {
        val view=LayoutInflater.from(mContext).inflate(R.layout.rv_item_stay_observe,p0,false)
        val layoutParams = view.getLayoutParams()
        layoutParams.height = p0.height / 6
        view.setLayoutParams(layoutParams)
        return SimpleAdapterViewHolder(view)
    }

    /**
     * 为了实现无线循环，需要将数据的源的个数设置为比较大的值
     */
    override fun getItemCount(): Int {
        return Int.MAX_VALUE
    }

    override fun onBindViewHolder(p0: SimpleAdapterViewHolder, p1: Int) {
        p0.mTvData?.text=list!![p1%list!!.size].ticketNo
        var col=""
        var b = p1%2==0
        if(b){
            col="#2b6de7"
        }else{
            col="#5488eb"
        }
        p0.itemView.setBackgroundColor(Color.parseColor(col))
    }

    class SimpleAdapterViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        var mTvData: TextView? = null

        init {
            mTvData = itemView.findViewById(R.id.rowTitle0)
        }
    }
}