package com.example.customviewproject.view

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.util.AttributeSet
import android.util.Log
import android.view.View
import androidx.core.graphics.toColorInt
import com.example.customviewproject.utils.TAG
import com.example.newproject.ui.utils.dp

private val RADIUS = 100.dp
private val PADDING = 50.dp

class MeasureCircleView(context: Context?, attrs: AttributeSet?) : View(context, attrs) {

    private val circlePaint = Paint(Paint.ANTI_ALIAS_FLAG).also {
        it.color = "#889900".toColorInt()
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        val size = ((PADDING + RADIUS) * 2).toInt()
        val widMode = MeasureSpec.getMode(widthMeasureSpec)
        val widSize = MeasureSpec.getSize(widthMeasureSpec)

        val heiMode = MeasureSpec.getMode(heightMeasureSpec)
        val heiSize = MeasureSpec.getSize(heightMeasureSpec)

        Log.e(TAG, "widthMeasureSpec: $widthMeasureSpec ,widSize: $widSize")

        /*val resultWid = when (widMode) {
            //最大值不能超过测量值
            MeasureSpec.AT_MOST -> {
                Log.e(TAG, "AT_MOST")
                if (size > widSize) widSize else size
            }
            //直接使用测量值
            MeasureSpec.EXACTLY -> {
                Log.e(TAG, "MeasureSpec.EXACTLY")
                widSize
            }
            //无限制，直接使用预期值
            MeasureSpec.UNSPECIFIED -> {
                Log.e(TAG, "MeasureSpec.UNSPECIFIED")
                size
            }
            else -> {
                -1
            }
        }
        val resultHei = when (heiMode) {
            //最大值不能超过测量值
            MeasureSpec.AT_MOST -> {
                if (size > heiSize) heiSize else size
            }
            //直接使用测量值
            MeasureSpec.EXACTLY -> {
                heiSize
            }
            //无限制，直接使用预期值
            MeasureSpec.UNSPECIFIED -> {
                size
            }
            else -> {
                -1
            }
        }*/
        val resultWid = resolveSize(size, widthMeasureSpec)
        val resultHei = resolveSize(size, heightMeasureSpec)
        setMeasuredDimension(resultWid, resultHei)
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        Log.e(TAG, "onDraw: ")
        canvas.drawCircle(PADDING + RADIUS, PADDING + RADIUS, RADIUS, circlePaint)
    }
}