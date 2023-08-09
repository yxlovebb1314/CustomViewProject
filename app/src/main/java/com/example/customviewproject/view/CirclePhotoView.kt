package com.example.customviewproject.view

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.PorterDuff
import android.graphics.PorterDuffXfermode
import android.graphics.RectF
import android.util.AttributeSet
import android.view.View
import com.example.customviewproject.R
import com.example.newproject.ui.utils.dp

private val IMAGE_WIDTH = 300f.dp
private val IMAGE_PADDING = 0f
private val XFERMODE = PorterDuffXfermode(PorterDuff.Mode.SRC_IN)  //设置遮罩模式

class CirclePhotoView(context: Context?, attrs: AttributeSet?) : View(context, attrs) {

    private val paint = Paint(Paint.ANTI_ALIAS_FLAG)
    private val bounds = RectF(IMAGE_PADDING, IMAGE_PADDING, IMAGE_PADDING + IMAGE_WIDTH, IMAGE_PADDING + IMAGE_WIDTH)


    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        //设置离屏缓冲区域
        val count = canvas.saveLayer(bounds, null)
        canvas.drawOval(IMAGE_PADDING, IMAGE_PADDING, IMAGE_PADDING + IMAGE_WIDTH, IMAGE_PADDING + IMAGE_WIDTH, paint)
        paint.xfermode = XFERMODE
        canvas.drawBitmap(getAvatar(IMAGE_WIDTH.toInt()), IMAGE_PADDING, IMAGE_PADDING, paint)
        paint.xfermode = null
        canvas.restoreToCount(count)
    }

    private fun getAvatar(width: Int) : Bitmap {
        val options = BitmapFactory.Options()
        //预加载图片，只加载图片的参数
        options.inJustDecodeBounds = true
        BitmapFactory.decodeResource(resources, R.mipmap.messi, options)
        options.inJustDecodeBounds = false
        options.inDensity = options.outWidth
        options.inTargetDensity = width
        return BitmapFactory.decodeResource(resources, R.mipmap.messi, options)
    }

}