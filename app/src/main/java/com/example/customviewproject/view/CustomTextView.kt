package com.example.customviewproject.view

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Rect
import android.text.TextUtils
import android.util.AttributeSet
import android.view.View
import androidx.core.graphics.toColorInt
import com.example.customviewproject.R
import com.example.newproject.ui.utils.dp
import kotlin.random.Random

class CustomTextView : View {

    private val colorArr = arrayListOf("#6600aa00".toColorInt(), "#77a00a00".toColorInt(),"#88aabb00".toColorInt(),"#99aabbcc".toColorInt(),"#aaaa00bb".toColorInt())
    private val paddingVerArray = arrayListOf(10.dp, 20.dp, 15.dp)


    private val TEXT_SIZE = 20.dp
    private val VER_PADDING = paddingVerArray[Random.nextInt(0, paddingVerArray.size)]
    private val HOR_PADDING = 25.dp

    private var widSize = 0f
    private var heiSize = 0f

    private val paint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        val index = Random.nextInt(0, colorArr.size)
        color = colorArr[index]
    }

    private val textPaint = Paint(Paint.ANTI_ALIAS_FLAG).also {
        it.color = Color.BLACK
        it.textSize = TEXT_SIZE
    }

    private var text = ""

    private val textBounds = Rect()

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        val ob = context.obtainStyledAttributes(attrs, R.styleable.CustomTextView)
        val t = ob.getString(R.styleable.CustomTextView_text)
        text = if (TextUtils.isEmpty(t)) "" else t!!
        ob.recycle()
        initSize()
    }

    private fun initSize() {
        textPaint.getTextBounds(text,0, text.length, textBounds)
        widSize = HOR_PADDING * 2 + textBounds.width()
        heiSize = VER_PADDING * 2 + TEXT_SIZE
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val resultWid = resolveSize(widSize.toInt(), widthMeasureSpec)
        val resultHei = resolveSize(heiSize.toInt(), heightMeasureSpec)
        setMeasuredDimension(resultWid, resultHei)
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        canvas.drawRoundRect(0f, 0f, measuredWidth.toFloat(), measuredHeight.toFloat(), 20f, 20f, paint)
        textPaint.textAlign = Paint.Align.CENTER
        canvas.drawText(text, (measuredWidth / 2).toFloat(), ((measuredHeight / 2) - (textBounds.top + textBounds.bottom)/2).toFloat(), textPaint)
    }
}