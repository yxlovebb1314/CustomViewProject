package com.example.customviewproject.view

import android.content.Context
import android.util.AttributeSet
import android.util.Log
import android.view.View
import com.example.customviewproject.utils.TAG
import kotlin.math.min

class MeasureView(context: Context?, attrs: AttributeSet?) : View(context, attrs) {

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        val value = min(measuredWidth, measuredHeight)
//Log.e(TAG, "===============\nonMeasure:   measureWidth:${measuredWidth}, measureHei:${measuredHeight}" )
        setMeasuredDimension(value, value)
//Log.e(TAG, "onMeasure:   measureWidth:${measuredWidth}, measureHei:${measuredHeight} \n===============" )
    }
}