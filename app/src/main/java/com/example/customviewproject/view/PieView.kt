package com.example.customviewproject.view

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View
import com.example.newproject.ui.utils.px
import kotlin.math.cos
import kotlin.math.sin

private val RADIUS = 150f.px
private val TRANSLATE_LENGTH = 40f.px  //扇形要偏移的距离

/**
 * 饼图View
 */
class PieView(context: Context?, attrs: AttributeSet?) : View(context, attrs) {

    private val paint = Paint(Paint.ANTI_ALIAS_FLAG)

    private val ANGLE_ARR = floatArrayOf(35f, 100f, 60f, 55f, 40f, 70f)
    private val COLOR_ARR = listOf(
        Color.parseColor("#ff00cc"),
        Color.parseColor("#aa0011"),
        Color.parseColor("#00cc00"),
        Color.parseColor("#0000aa"),
        Color.parseColor("#aabbcc"),
        Color.parseColor("#8800ab")
    )
//    private val COLOR_ARR = arrayOf(R.color.black, R.color.white, R.color.yellow, R.color.blue2, R.color.red, R.color.green)

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        var startAngle = 0f
        for ((i, angle) in ANGLE_ARR.withIndex()) {
            paint.color = COLOR_ARR[i]
            if(i == 3) {
                canvas.save()
                /**
                 * 偏移的位置，不能直接偏移x和y都一样的距离，需要通过三角函数计算
                 */
                val currentAngle = startAngle + angle / 2
                val tX = TRANSLATE_LENGTH * cos(Math.toRadians(currentAngle.toDouble()))
                val tY = TRANSLATE_LENGTH * sin(Math.toRadians(currentAngle.toDouble()))
                canvas.translate(tX.toFloat(), tY.toFloat())
            }
            canvas.drawArc(width / 2f - RADIUS, height / 2f - RADIUS, width / 2f + RADIUS, height / 2 + RADIUS,
                startAngle, angle, true, paint)
            startAngle += angle
            if(i == 3) {
                canvas.restore()
            }
        }
    }
}