package com.example.customviewproject.view

import android.animation.ObjectAnimator
import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Paint
import android.util.AttributeSet
import android.util.Log
import android.view.GestureDetector
import android.view.MotionEvent
import android.view.View
import androidx.core.view.GestureDetectorCompat
import com.example.customviewproject.utils.BitmapUtils
import com.example.customviewproject.utils.TAG
import com.example.newproject.ui.utils.dp

class ScalableImageView(context: Context, attrs: AttributeSet?) : View(context, attrs),
    GestureDetector.OnGestureListener, GestureDetector.OnDoubleTapListener {

    private var bitmap : Bitmap = BitmapUtils.getAvatar(resources, 250.dp.toInt())
    private val paint = Paint(Paint.ANTI_ALIAS_FLAG)
    private val gestureDetector = GestureDetectorCompat(context, this)

    private val VER_EXTER_PERCENT = 1.5f //垂直缩放比的额外值，要让垂直方向也超出屏幕

    //水平和垂直缩放比
    private var hScale = 0f
    private var vScale = 0f
    //手指移动时的偏移量
    private var offsetX = 0f
    private var offsetY = 0f

    private var isSetVScale = false

    private var canvasScaleValue = 0f
        set(value) {
            field = value
            invalidate()
        }

    private val scaleAnimator = ObjectAnimator.ofFloat(this, "canvasScaleValue", hScale).also {
        it.duration = 150
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        hScale = width.toFloat() / bitmap.width
        vScale = (height.toFloat() / bitmap.height)
        canvasScaleValue = if (hScale < vScale) {
            hScale
        } else {
            vScale *= VER_EXTER_PERCENT
            vScale
        }
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        canvas.save()
        canvas.translate(offsetX, offsetY)
        canvas.scale(canvasScaleValue, canvasScaleValue, width / 2f, height / 2f)
        canvas.drawBitmap(bitmap, (width - bitmap.width) / 2f, (height - bitmap.height) / 2f, paint)
        canvas.restore()
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
//        return super.onTouchEvent(event)
        return gestureDetector.onTouchEvent(event)
    }

    //设置给系统一个标记，表示需要监听手势，取消掉原有的view的onTouchEvent监听
    //并且需要在当前View的onTouchEvent中，返回gestureDetector.onTouchEvent(event)
    override fun onDown(e: MotionEvent): Boolean {
        return true
    }

    //延迟监听，即预点击的回调，有100毫秒延迟
    override fun onShowPress(e: MotionEvent) {
    }

    //没有设置双击监听时，通过这个回调监听单击
    override fun onSingleTapUp(e: MotionEvent): Boolean {
        return false
    }

    //滑动监听
    override fun onScroll(
        e1: MotionEvent,
        e2: MotionEvent,
        distanceX: Float,
        distanceY: Float
    ): Boolean {
        //放大时才能移动,distanceX表示移动的距离，但是需要注意的是，这个值是 上一个位置-下一个位置 的值
        //所以如果是正向移动，这个值是负值，所以需要减
        if (isSetVScale) {
            offsetX -= distanceX
            offsetY -= distanceY
            val xLine = (bitmap.width * canvasScaleValue - width) / 2
            val yLine = (bitmap.height * canvasScaleValue - height) / 2
            if (offsetX > xLine) {
                offsetX = xLine
            } else if (offsetX < -xLine) {
                offsetX = -xLine
            }
            if (offsetY > yLine) {
                offsetY = yLine
            } else if(offsetY < -yLine) {
                offsetY = -yLine
            }
            invalidate()
        }
        return false
    }

    //长按监听
    override fun onLongPress(e: MotionEvent) {

    }

    //快速滑动监听
    override fun onFling(
        e1: MotionEvent,
        e2: MotionEvent,
        velocityX: Float,
        velocityY: Float
    ): Boolean {
        return false
    }

    //设置了双击监听时，单击的监听
    override fun onSingleTapConfirmed(e: MotionEvent): Boolean {
        return false
    }

    //双击时的回调
    override fun onDoubleTap(e: MotionEvent): Boolean {
        isSetVScale = !isSetVScale
        canvasScaleValue = if (isSetVScale) {
            vScale *= VER_EXTER_PERCENT
            vScale
        } else {
            hScale
        }
        if (isSetVScale) {
            if (scaleAnimator.values.size == 1) {
                scaleAnimator.setFloatValues(hScale, vScale)
            }
            scaleAnimator.start()
        } else {
            //缩小的时候，让图片回到最初的位置，避免缩小后错位
            offsetX = 0f
            offsetY = 0f
            scaleAnimator.reverse()
        }
//        invalidate()
        return false
    }

    //这个方法在双击时，第二下手指不松开的时候回调(基本不用)
    override fun onDoubleTapEvent(e: MotionEvent): Boolean {
        return false
    }

}