package com.example.customviewproject.view

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Paint.FontMetrics
import android.util.AttributeSet
import android.view.View
import com.example.customviewproject.R
import com.example.customviewproject.utils.BitmapUtils
import com.example.newproject.ui.utils.dp

class MultilineTextView(context: Context?, attrs: AttributeSet?) : View(context, attrs) {

    private var text = "The last day, the first boxer day after the beginning of autumn, is the last one; It also refers to a period of time from the first Boxer day after the beginning of Autumn to the day before the second Boxer day (a total of ten days).According to the traditional calculation method in China, starting from the summer solstice, according to the arrangement of Ganzhi Ji days, the third Boxer day is the beginning of the year, the fourth Boxer day is the middle of the year, and the first Boxer day after the beginning of autumn is the end of the year. There are 10 days between each boxer day, 10 days from the beginning to the middle and 10 days from the end."
    private val fontMetrics = FontMetrics()

    private val paint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        textSize = 16.dp
        color = Color.parseColor("#00bbaa")
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        paint.getFontMetrics(fontMetrics)
    }

    /*override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        canvas.drawBitmap(BitmapUtils.getAvatar(resources, 200.dp.toInt(), R.drawable.touxiang2), width - 200.dp, 60.dp,paint)
        val measuredWidth = floatArrayOf(0f)
        var count = paint.breakText(text, true, width.toFloat(), measuredWidth)
        canvas.drawText(text, 0, count, 0f, - fontMetrics.top + fontMetrics.bottom, paint)
        val text2 = text.subSequence(count, text.length).toString()
        count = paint.breakText(text2, true, width.toFloat(), measuredWidth)
        canvas.drawText(text2, 0, count, 0f, (- fontMetrics.top + fontMetrics.bottom) * 2, paint)
    }*/

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        val bitmap = BitmapUtils.getAvatar(resources, 200.dp.toInt(), R.drawable.touxiang2)
        canvas.drawBitmap(bitmap, width - 200.dp, 60.dp,paint)

        val measuredWidth = floatArrayOf(0f)
        //用来记录当前是第几行，用于计算绘制文字的纵向起点
        var lineNum = 1
        //用于记录每一行起点的文字是总text的第几个下标，这一行要从这个下标开始绘制文字
        var textIndex = 0
        do {
            //当前行顶部的纵坐标
            val lastLineHei = (- fontMetrics.top + fontMetrics.bottom) * (lineNum - 1)
            //当前行底部的纵坐标
            val currentLineHei = (- fontMetrics.top + fontMetrics.bottom) * lineNum
            //图片上下边距y左标(预留5个dp出来，避免文字和图片离得太近)
            val bitmapTopY = 60.dp
            val bitmapBottomY = 60.dp + 200.dp

            //文字绘制宽度
            var textWid = width.toFloat()

            if ((lastLineHei in bitmapTopY..bitmapBottomY)
                || (currentLineHei in bitmapTopY..bitmapBottomY)) {
                textWid = textWid - 200.dp - 8.dp
            }

            //要绘制的文字个数
            val count = paint.breakText(text, textIndex, text.length, true, textWid, measuredWidth)
            canvas.drawText(text, textIndex, count, 0f, currentLineHei, paint)
            lineNum++
            textIndex += count
        } while (textIndex <= (text.length - 1))
    }
}