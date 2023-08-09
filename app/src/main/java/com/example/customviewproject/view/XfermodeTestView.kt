package com.example.customviewproject.view

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.PorterDuff
import android.graphics.PorterDuffXfermode
import android.graphics.RectF
import android.util.AttributeSet
import android.view.View
import com.example.newproject.ui.utils.dp

class XfermodeTestView(context: Context?, attrs: AttributeSet?) : View(context, attrs) {

    private val paint = Paint(Paint.ANTI_ALIAS_FLAG)
    private val xfermode = PorterDuffXfermode(PorterDuff.Mode.SRC_IN)

    //离屏缓冲区域
    private val bufferRect = RectF(50f.dp, 0f, 200f.dp, 150f.dp)

    private val circleBitmap =
        Bitmap.createBitmap(150f.dp.toInt(), 200f.dp.toInt(), Bitmap.Config.ARGB_8888)
    private val rectBitmap =
        Bitmap.createBitmap(150f.dp.toInt(), 200f.dp.toInt(), Bitmap.Config.ARGB_8888)

    init {
        val canvas = Canvas()
        //以圆形bitmap为画布，画出圆形
        canvas.setBitmap(circleBitmap)
        paint.color = Color.parseColor("#aa00cc")
        canvas.drawOval(100f.dp, 0f, 200f.dp, 100f.dp, paint)
        //再以方形bitmap作为画布，画出方形
        canvas.setBitmap(rectBitmap)
        paint.color = Color.parseColor("#00aabb")
        canvas.drawRect(50f.dp, 50f.dp, 150f.dp, 150f.dp, paint)
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        //设置离屏缓冲，这个方式是比较耗费资源的，所以能设置多小，就设置多小
        canvas.saveLayer(bufferRect, null)
//        canvas.drawOval(100f.px, 0f, 200f.px, 100f.px, paint)
        canvas.drawBitmap(circleBitmap,50f.dp, 0f, paint)
        paint.xfermode = xfermode
//        canvas.drawRect(50f.px, 50f.px, 150f.px, 150f.px, paint)
        canvas.drawBitmap(rectBitmap, 50f.dp, 0f, paint)
        paint.xfermode = null
        canvas.restore()
    }
}