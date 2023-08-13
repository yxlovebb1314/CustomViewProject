package com.example.customviewproject.view

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Path
import android.graphics.PathDashPathEffect
import android.graphics.PathMeasure
import android.graphics.Rect
import android.graphics.RectF
import android.util.AttributeSet
import android.util.Log
import android.view.View
import com.example.customviewproject.utils.TAG
import com.example.newproject.ui.utils.dp
import kotlin.math.cos
import kotlin.math.sin

/**
 * 绘制一个时钟，由属性动画来控制时针的旋转
 */
class ClockView(context: Context?, attrs: AttributeSet?) : View(context, attrs) {

    private val RADIUS = 110.dp  //整个时钟的半径
    private val PADDING = 10.dp //时钟图形与左边和上边的间距值
    private val SMALL_POINT_LEN = 7.dp //小刻度长度
    private val NORMAL_POINT_LEN = 10.dp //小刻度长度

    private val pointLenH = RADIUS - 60.dp  //时针长度
    private val pointLenM = RADIUS - 45.dp  //分针长度
    private val pointLenS = RADIUS - 30.dp  //秒针长度

    private val paint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        color = Color.parseColor("#aa00bb")
        style = Paint.Style.STROKE
        strokeWidth = 4.dp
    }
    private val textPaint = Paint(Paint.ANTI_ALIAS_FLAG).also {
        it.textSize = 20.dp
        it.color = Color.parseColor("#aa00bb")
    }

    private var textBounds = Rect()

    private val circleRectF = RectF(PADDING, PADDING, 10.dp + RADIUS * 2, 10.dp + RADIUS * 2)

    private val circlePath = Path()
    private val smallPath = Path()
    private val normalPath = Path()
    private lateinit var effectSmall : PathDashPathEffect
    private lateinit var effectNormal : PathDashPathEffect
    private val pathMeasure = PathMeasure(circlePath, false)

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        Log.e(TAG, "ClockView --> onSizeChanged:")
        circlePath.reset()
        //添加圆环
        circlePath.addOval(circleRectF, Path.Direction.CCW)
        //添加刻度的小path
        smallPath.addRect(0f, 0.dp, 2.dp, SMALL_POINT_LEN, Path.Direction.CCW)
        //12,3,6,9点的长刻度
        normalPath.addRect(0f, 0.dp, 3.dp, NORMAL_POINT_LEN, Path.Direction.CCW)
        //计算刻度间的距离
        pathMeasure.setPath(circlePath, false)
        val len = pathMeasure.length / 60f
        //计算长刻度间的距离
        val len2 = pathMeasure.length / 12f
        //声明短刻度
        effectSmall = PathDashPathEffect(smallPath, len, 0f, PathDashPathEffect.Style.ROTATE)
        //声明长刻度
        effectNormal = PathDashPathEffect(normalPath, len2, 0f, PathDashPathEffect.Style.ROTATE)
        //得到text的矩形区域
        textPaint.getTextBounds("" + 12, 0, "12".length, textBounds)
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        //画圆环
        canvas.drawPath(circlePath, paint)
        paint.pathEffect = effectSmall
        //画刻度
        canvas.drawOval(circleRectF, paint)
        paint.pathEffect = effectNormal
        canvas.drawOval(circleRectF, paint)
        //画文字
        drawText(canvas)
        //画指针
        drawLine(canvas)
    }

    private fun drawLine(canvas: Canvas) {
        paint.pathEffect = null
        //temp--秒针在12点位置，分针在6点位置，时针在3点位置  即3点30分的时钟
        paint.color = Color.parseColor("#ff0000")
        canvas.drawLine(PADDING + RADIUS, PADDING + RADIUS,
            PADDING + RADIUS + pointLenS * sin(Math.toRadians((30f.toDouble() * 0))).toFloat(),
            PADDING + RADIUS - pointLenS * cos(Math.toRadians(30f.toDouble() * 0)).toFloat() ,
            paint)
        paint.color = Color.parseColor("#000000")
        canvas.drawLine(PADDING + RADIUS, PADDING + RADIUS,
            PADDING + RADIUS + pointLenM * sin(Math.toRadians((30f.toDouble() * 7))).toFloat(),
            PADDING + RADIUS - pointLenM * cos(Math.toRadians(30f.toDouble() * 7)).toFloat() ,
            paint)
        paint.strokeWidth = 6.dp
        canvas.drawLine(PADDING + RADIUS, PADDING + RADIUS,
            PADDING + RADIUS + pointLenH * sin(Math.toRadians((30f.toDouble() * (3 + 7f / 12)))).toFloat(),
            PADDING + RADIUS - pointLenH * cos(Math.toRadians(30f.toDouble() * (3 + 7f / 12))).toFloat() ,
            paint)
    }

    /**
     *  从12点刻度开始画起，每个整点时间间隔30度，通过等边三角形先算出底边，再算出当前刻度的x和y的偏移量
     *  等边三角形的边长 = 圆环半径 - 长刻度高度 - 文字bounds高度（以12点的text为基准） / 2
     */
    private fun drawText(canvas: Canvas) {
        var text = 12
        //算出12点位置的文字的bounds中心坐标，作为其他文字绘制时偏移的基点
        val baseX = PADDING + RADIUS
        val baseY = PADDING + NORMAL_POINT_LEN + (textBounds.bottom - textBounds.top) + 5.dp  //y值是长刻度的高度 + dounds / 2  其中5.dp是文字和刻度的距离

        //起始角度
        var angle = 0f
        while (angle < 360f) {
            //先算出等边三角形的边长
            val l1 = RADIUS - NORMAL_POINT_LEN - (textBounds.bottom - textBounds.top) / 2 - 5.dp
            //再计算出两个刻度之间的距离
            val tempAngle = angle / 2
            val l2 = 2 * l1 * sin(Math.toRadians(tempAngle.toDouble()))
            //再通过 (180度 - angle) / 2 的角度来计算得到下一个刻度的x和y值
            val tempAngle2 = (180f - angle) / 2
            val y = l2 * cos(Math.toRadians(tempAngle2.toDouble()))
            val x = l2 * sin(Math.toRadians(tempAngle2.toDouble()))
            //绘制文字
            textPaint.textAlign = Paint.Align.CENTER
            canvas.drawText("" + text, (baseX + x).toFloat(), (baseY + y).toFloat(), textPaint)

            if (text == 12) {
                text = 0
            }
            text++
            angle += 30f
        }
    }

}