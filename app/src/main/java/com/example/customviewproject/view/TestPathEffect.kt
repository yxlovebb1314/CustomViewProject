package com.example.customviewproject.view

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Path
import android.graphics.PathDashPathEffect
import android.util.AttributeSet
import android.view.View

/**
 * 绘制虚线
 */
class TestPathEffect(context: Context?, attrs: AttributeSet?) : View(context, attrs) {

    val paint = Paint(Paint.ANTI_ALIAS_FLAG)
    val effectPath = Path()
    val effect:PathDashPathEffect

    init {
        effectPath.addRect(0f,0f, 10f, 5f, Path.Direction.CCW)
        effect = PathDashPathEffect(effectPath, 20f, 0f, PathDashPathEffect.Style.ROTATE)
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        paint.pathEffect = effect
        canvas.drawLine(0f, 0f, 500f, 500f, paint)
    }

}