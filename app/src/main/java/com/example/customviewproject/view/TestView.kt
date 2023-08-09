package com.example.newproject.ui.view

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Path
import android.util.AttributeSet
import android.view.View
import com.example.newproject.ui.utils.dp

private val RADIUS = 100f.dp

class TestView(context: Context?, attrs: AttributeSet?) : View(context, attrs) {

    private val paint = Paint(Paint.ANTI_ALIAS_FLAG)

    private val path = Path()

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        path.reset()
        path.addCircle(width / 2f, height / 2f, RADIUS, Path.Direction.CCW)
        path.addRect(width / 2f - RADIUS, height / 2f, width / 2f + RADIUS,
            height / 2f + 2 * RADIUS, Path.Direction.CW)
        path.fillType = Path.FillType.EVEN_ODD
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
//        canvas.drawLine(100f.px, 100f.px, 200f.px, 200f.px, paint)
//        canvas.drawCircle(width / 2f, height / 2f, RADIUS, paint)
        canvas.drawPath(path, paint)
    }

}