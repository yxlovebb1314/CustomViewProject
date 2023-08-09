package com.example.customviewproject.view

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Path
import android.graphics.PathDashPathEffect
import android.graphics.PathMeasure
import android.util.AttributeSet
import android.view.View
import com.example.newproject.ui.utils.dp
import kotlin.math.cos
import kotlin.math.sin

//小刻度的长和宽
private val DASH_WIDTH = 2f.dp
private val DASH_LENGTH = 10f.dp
//圆弧半径
private val ARC_RADIUS = 150f.dp
//圆弧角度起始角度
private const val ARC_ANGLE = 150f
//圆弧划过的角度
private const val ARC_SWEEP_ANGLE = 360 - (ARC_ANGLE - 90f) * 2
//指针长度
private val POINTER_LENGTH = 100f.dp

/**
 * 仪表盘View
 */
class DashboardView(context: Context?, attrs: AttributeSet?) : View(context, attrs) {

    private val paint = Paint(Paint.ANTI_ALIAS_FLAG)
    private val dashPath = Path()   //小刻度path
    private val arcPath = Path()    //圆弧path
    private lateinit var dashEffect: PathDashPathEffect

    init {
        paint.style = Paint.Style.STROKE
        paint.strokeWidth = 3f.dp
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        arcPath.reset()
        dashPath.reset()
        //声明圆弧path
        arcPath.addArc(width / 2f - ARC_RADIUS, height / 2f - ARC_RADIUS, width / 2f + ARC_RADIUS, height / 2f + ARC_RADIUS,
            ARC_ANGLE, ARC_SWEEP_ANGLE)
        //声明小刻度的path
        dashPath.addRect(0f, 0f, DASH_WIDTH, DASH_LENGTH, Path.Direction.CCW)
//        paint.setPathEffect(PathDashPathEffect(dashPath, 50f, 0f,PathDashPathEffect.Style.MORPH))
        //通过PathMeasure计算出刻度之间的间隔
        val interval = (PathMeasure(arcPath, false).length - DASH_WIDTH) / 20
        //声明一个路径特效，以dashPath中的内容来作为绘制的每一个元素,第二个参数是元素间隔长度，第三个是提前量，表示第一个元素是否要隔这么多距离才开始画
        dashEffect = PathDashPathEffect(dashPath, interval, 0f, PathDashPathEffect.Style.MORPH)
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        /**
         * 画圆弧
         */
        //第五，第六个参数，表示起始角度和划过的角度,第七个参数，表示是否要使用圆心
        //要注意，算起始角度时，要注意象限
//        canvas.drawArc(
//            width / 2f - 150f.px, height / 2f - 150.px, width / 2f + 150.px, height / 2f + 150.px,
//            150f, 240f, false, paint
//        )
        canvas.drawPath(arcPath, paint)

        /**
         * 画刻度
         */
        //以同样的方式，再绘制一次刻度
        paint.pathEffect = dashEffect
//        canvas.drawArc(width / 2f - 150f.px, height / 2f - 150.px, width / 2f + 150.px, height / 2f + 150.px,
//            150f, 240f, false, paint)
        canvas.drawPath(arcPath, paint)
        //绘制完成后，需要将pathEffect置空
        paint.pathEffect = null

        /**
         * 画指针
         */
        val xLength = POINTER_LENGTH * cos(getRadius(5))
        val yLength = POINTER_LENGTH * sin(getRadius(5))
        canvas.drawLine(width / 2f, height / 2f, width / 2f + xLength.toFloat(), height / 2f + yLength.toFloat(), paint)
    }

    /**
     * 计算弧度
     * @param mark mark表示刻度值，从0开始
     * 注：指针的角度 = 圆弧起始角度 + 划过的弧度 / 20 * mark
     */
    private fun getRadius(mark : Int) : Double = Math.toRadians(ARC_SWEEP_ANGLE / 20.toDouble() * mark + ARC_ANGLE)

}
