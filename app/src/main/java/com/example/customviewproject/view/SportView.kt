package com.example.customviewproject.view

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Paint.FontMetrics
import android.graphics.Rect
import android.graphics.RectF
import android.util.AttributeSet
import android.view.View
import com.example.newproject.ui.utils.dp

private val circleColor = Color.parseColor("#aabbcc")
private val heightLightColor = Color.parseColor("#aa00bb")
class SportView(context: Context?, attrs: AttributeSet?) : View(context, attrs) {
    //声明画笔
    private val circlePaint = Paint(Paint.ANTI_ALIAS_FLAG)
    private val textPaint = Paint(Paint.ANTI_ALIAS_FLAG)
    private val linePaint = Paint(Paint.ANTI_ALIAS_FLAG)
    //圆环矩形区域
    private lateinit var circleBounds : RectF
    //圆环中心坐标
    private var circleX : Float = 0f
    private var circleY : Float = 0f
    //储存text的矩形区域
    private var textBounds = Rect()
    //储存text核心区域
    private var textFontMetrics = FontMetrics()

    private val text = "alcg"

    init {
        circlePaint.strokeWidth = 16.dp
        circlePaint.style = Paint.Style.STROKE

        textPaint.color = Color.parseColor("#aabb00")
        textPaint.textSize = 85.dp
        textPaint.textAlign = Paint.Align.CENTER

        linePaint.strokeWidth = 1f
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        circleBounds = RectF(50.dp, 50.dp, width - 50.dp, width - 50.dp)
        circleX = (width - 100.dp) / 2f + 50.dp
        circleY = (width - 100.dp) / 2f + 50.dp
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        //绘制圆环
        circlePaint.color = circleColor
        canvas.drawOval(circleBounds, circlePaint)

        //绘制进度
        circlePaint.color = heightLightColor
        circlePaint.strokeCap = Paint.Cap.ROUND
        canvas.drawArc(circleBounds, 50f, 230f, false, circlePaint)

        //绘制文字
        textPaint.getTextBounds(text, 0, text.length, textBounds)
        textPaint.getFontMetrics(textFontMetrics)
        canvas.drawText(text,  circleX, circleY - (textBounds.bottom + textBounds.top) / 2, textPaint)
//        canvas.drawText(text,  circleX, circleY - textBounds.centerY(), textPaint)
//        canvas.drawText(text,  circleX, circleY - (textFontMetrics.ascent + textFontMetrics.descent) / 2, textPaint)

        //绘制参考线
        canvas.drawLine(0f, circleY, width.toFloat(), circleY, linePaint)
        canvas.drawLine(circleX, 0f, circleX, height.toFloat(), linePaint)

        //以这条线的起点为初始点，绘制文字
        canvas.drawLine(0f, width.toFloat(), width.toFloat(), width.toFloat(), linePaint)
        textPaint.textAlign = Paint.Align.LEFT
        textPaint.textSize = 150.dp
        textPaint.getFontMetrics(textFontMetrics)
        textPaint.getTextBounds(text,0, text.length, textBounds)
        canvas.drawText(text, 0f - textBounds.left, width.toFloat() - (textFontMetrics.ascent + textFontMetrics.descent), textPaint)

        //绘制小字体
        textPaint.textSize = 25.dp
        canvas.drawText(text, 0f, width + 150.dp, textPaint)
    }
}