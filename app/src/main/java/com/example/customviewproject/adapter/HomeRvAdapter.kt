package com.example.customviewproject.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.View.OnClickListener
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatTextView
import androidx.recyclerview.widget.RecyclerView.Adapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.customviewproject.R

class HomeRvAdapter(val context: Context) : Adapter<HomeRvAdapter.Holder>() {

    private var listener: OnItemClickListener? = null

    fun setOnItemClickListener(listener: OnItemClickListener) {
        this.listener = listener
    }

    private val list = arrayListOf(
        "第一章和第二章、图形的位置和尺寸测量以及遮罩原理",
        "第三章、文字的测量",
        "第五章、属性动画和硬件加速",
        "第七章、手写MeteralEditText",
        "第九章、自定义布局之尺寸的自定义"
    )

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Holder {
        return Holder(
            LayoutInflater.from(context).inflate(R.layout.layout_home_list_item, parent, false)
        )
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun onBindViewHolder(holder: Holder, position: Int) {
        holder.tv.text = list[position]
        holder.tv.setOnClickListener {
            listener?.onClick(position)
        }
    }

    class Holder(itemView: View) : ViewHolder(itemView) {
        val tv: AppCompatTextView = itemView.findViewById(R.id.tv_layout_home_list_item_tv)
    }

    interface OnItemClickListener {
        fun onClick(position: Int)
    }
}
