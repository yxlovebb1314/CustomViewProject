package com.example.customviewproject.view

import android.animation.ObjectAnimator
import android.animation.TypeEvaluator
import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View
import android.view.animation.LinearInterpolator
import androidx.core.content.ContextCompat
import com.example.customviewproject.R
import com.example.customviewproject.utils.CITY
import com.example.newproject.ui.utils.dp

class TextView(context: Context, attrs: AttributeSet?) : View(context, attrs) {

    var text = ""
        set(value) {
            field = value
            invalidate()
        }

    private val objAnimator = ObjectAnimator.ofObject(this@TextView, "text", StringEvaluator(), CITY[CITY.size - 1]).also {
        it.duration = 6000
        it.interpolator = LinearInterpolator()
    }

    val paint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        strokeWidth = 3.dp
        color = ContextCompat.getColor(context, R.color.black)
        textAlign = Paint.Align.LEFT
        textSize = 20.dp
    }

    fun startAnimator() {
        objAnimator.start()
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        canvas.drawText(text, 10.dp, 40.dp, paint)
    }

    class StringEvaluator : TypeEvaluator<String> {
        override fun evaluate(fraction: Float, startValue: String?, endValue: String?): String {
            /**
             * 通过数组的下标作为计算的数值
             */
            //得到当前下标
            val currentIndex = (fraction * (CITY.size - 1)).toInt()
            return CITY[currentIndex]
        }
    }
}