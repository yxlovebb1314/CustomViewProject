package com.example.customviewproject.view

import android.content.Context
import android.graphics.RectF
import android.util.AttributeSet
import android.view.ViewGroup
import androidx.core.view.children

class CustomLayout(context: Context?, attrs: AttributeSet?) : ViewGroup(context, attrs) {

    //保存测量后的子view位置
    private var boundsList = arrayListOf<RectF>()

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        /**
         * 1.遍历子控件
         * 2.先测量一个每个子控件的大小
         * 3.每遍历一个，就记录当前最大宽高，当宽度超出了最大宽度时，换行
         * 4.保存每一个子控件的位置
         * 5.得到最终的自身的宽高，进行测量
         */
        //记录当前控件的实际宽高
        var selfWid = 0
        var selfHei = 0

        //记录临时宽高，即当前行最宽的位置，和当前行最高的位置
        var currentWid = 0
        var currentHei = 0

        //获取到当前控件的测量模式和具体值
        val specModeWid = MeasureSpec.getMode(widthMeasureSpec)
        val specSizeWid = MeasureSpec.getSize(widthMeasureSpec)

        for((index, child) in children.withIndex()) {
            //测量子控件
            measureChildWithMargins(child, widthMeasureSpec, 0,heightMeasureSpec, selfHei)

            //如果测量模式不是无限制，并且当前宽度已经大于最大宽度，则需要换行，并重新测量
            if (specModeWid != MeasureSpec.UNSPECIFIED && (currentWid + child.measuredWidth) > specSizeWid) {
//                lineNum++
                currentWid = 0
                selfHei += currentHei
                currentHei = 0
                measureChildWithMargins(child, widthMeasureSpec, 0,heightMeasureSpec, selfHei)
            }

            //实例化RectF
            if (index >= boundsList.size) {
                boundsList.add(RectF())
            }

            //测量子控件后，获取到测量到的子控件实际宽高
            val childWid = child.measuredWidth
            val childHei = child.measuredHeight

            //保存当前子控件位置
            boundsList[index].apply {
                left = currentWid.toFloat()
                top = selfHei.toFloat()
                right = (currentWid + childWid).toFloat()
                bottom = (childHei + selfHei).toFloat()
            }

            //增加宽度
            currentWid += childWid
            //记录当前行的最大宽度
            if (currentHei < childHei) {
                currentHei = childHei
            }
            //记录最宽的宽度
            if (selfWid < currentWid) {
                selfWid = currentWid
            }
        }

        //由于selfHei是记录的上一行的高度，所以在做自身测量时，需要加上最后一行的最高高度
        setMeasuredDimension(selfWid, selfHei + currentHei)
    }

    override fun onLayout(changed: Boolean, l: Int, t: Int, r: Int, b: Int) {
        for((index, value) in boundsList.withIndex()) {
            val rectF = boundsList[index]
            getChildAt(index).layout(rectF.left.toInt(), rectF.top.toInt(), rectF.right.toInt(), rectF.bottom.toInt())
        }
    }

    override fun generateLayoutParams(attrs: AttributeSet?): LayoutParams {
        return MarginLayoutParams(context, attrs)
    }
}