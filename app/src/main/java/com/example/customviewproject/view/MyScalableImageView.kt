package com.example.customviewproject.view

import android.animation.Animator
import android.animation.Animator.AnimatorListener
import android.animation.ObjectAnimator
import android.content.Context
import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Paint
import android.util.AttributeSet
import android.util.Log
import android.view.GestureDetector
import android.view.GestureDetector.SimpleOnGestureListener
import android.view.MotionEvent
import android.view.ScaleGestureDetector
import android.view.ScaleGestureDetector.OnScaleGestureListener
import android.view.View
import android.widget.OverScroller
import androidx.core.animation.doOnEnd
import androidx.core.view.GestureDetectorCompat
import androidx.core.view.ViewCompat
import com.example.customviewproject.utils.BitmapUtils
import com.example.customviewproject.utils.TAG
import com.example.newproject.ui.utils.dp

class MyScalableImageView(context: Context, attrs: AttributeSet?) : View(context, attrs) {

    private var bitmap : Bitmap = BitmapUtils.getAvatar(resources, 250.dp.toInt())
    private val paint = Paint(Paint.ANTI_ALIAS_FLAG)
    private val myGestureListener = MyGestureListener()
    private val myScaleGestureListener = MyScaleGestureListener()
    private val gestureDetector = GestureDetectorCompat(context, myGestureListener)
    //这里尤其要注意，不能使用带Compat的那个类，因为带Compat那个类不是新版本的升级类，而是一个辅助工具类,跟上面的GestureDetectorCompat不一样GestureDetectorCompat是GestureDetector的升级版
    private val scaleGestureDetector = ScaleGestureDetector(context, myScaleGestureListener)
    private val scroller = OverScroller(context)
    private val flingRunnable = FlingRunnable()

    private val VER_EXTER_PERCENT = 1.5f //垂直缩放比的额外值，要让垂直方向也超出屏幕

    //水平和垂直缩放比
    private var hScale = 0f
    private var vScale = 0f
    //手指移动时的偏移量
    private var offsetX = 0f
    private var offsetY = 0f

    private var isSetVScale = false //是否是放大状态

    private var canvasScaleValue = 0f
        set(value) {
            field = value
            invalidate()
        }

    private val scaleAnimator = ObjectAnimator.ofFloat(this, "canvasScaleValue", hScale).also {
        it.duration = 150
        /*it.addListener(object : AnimatorListener {
            override fun onAnimationStart(animation: Animator) {

            }

            override fun onAnimationEnd(animation: Animator) {
                //在缩小时，将位移重新置为0，避免再次放大时，初始位置会停留在上一次放大后移动的位置
                if (!isSetVScale) {
                    offsetX = 0f
                    offsetY = 0f
                }
            }

            override fun onAnimationCancel(animation: Animator) {

            }

            override fun onAnimationRepeat(animation: Animator) {

            }
        })*/
        it.doOnEnd {
            //在缩小时，将位移重新置为0，避免再次放大时，初始位置会停留在上一次放大后移动的位置
            //ps:在添加了双击时，跟手的逻辑后，每次放大时，都会重新计算offset的值，所以这个地方的值就可以不要了
            /*if (!isSetVScale) {
                offsetX = 0f
                offsetY = 0f
            }*/
        }
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        hScale = width.toFloat() / bitmap.width
        vScale = (height.toFloat() / bitmap.height)
        canvasScaleValue = if (!isSetVScale) {
            hScale
        } else {
            vScale *= VER_EXTER_PERCENT
            vScale
        }
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        canvas.save()
//        canvas.translate(offsetX, offsetY)
//Log.e(TAG, "onDraw:   百分比: ($canvasScaleValue - $hScale) / ($vScale - $hScale) = ${((canvasScaleValue - hScale) / (vScale - hScale))}")
        canvas.translate(offsetX * ((canvasScaleValue - hScale) / (vScale - hScale)), offsetY * ((canvasScaleValue - hScale) / (vScale - hScale)))
        canvas.scale(canvasScaleValue, canvasScaleValue, width / 2f, height / 2f)
        canvas.drawBitmap(bitmap, (width - bitmap.width) / 2f, (height - bitmap.height) / 2f, paint)
        canvas.restore()
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
//        return super.onTouchEvent(event)
        /*if (scaleGestureDetector.isInProgress) {
            return scaleGestureDetector.onTouchEvent(event)
        }
        return gestureDetector.onTouchEvent(event)*/
        scaleGestureDetector.onTouchEvent(event)
        if (!scaleGestureDetector.isInProgress) {
            gestureDetector.onTouchEvent(event)
        }
        return true
    }

    private fun setBorder() {
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
    }


    inner class FlingRunnable : Runnable {
        override fun run() {
            if (scroller.computeScrollOffset()) {
                offsetX = scroller.currX.toFloat()
                offsetY = scroller.currY.toFloat()
                invalidate()
                ViewCompat.postOnAnimation(this@MyScalableImageView, this)
            }
        }
    }

    /**
     * 单击与双击辅助类
     */
    inner class MyGestureListener : SimpleOnGestureListener() {
        //设置给系统一个标记，表示需要监听手势，取消掉原有的view的onTouchEvent监听
        //并且需要在当前View的onTouchEvent中，返回gestureDetector.onTouchEvent(event)
        override fun onDown(e: MotionEvent): Boolean {
            return true
        }

        //延迟监听，即预点击的回调，有100毫秒延迟
        /*override fun onShowPress(e: MotionEvent) {
        }

        //没有设置双击监听时，通过这个回调监听单击
        override fun onSingleTapUp(e: MotionEvent): Boolean {
            return false
        }*/

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
                setBorder()
                invalidate()
            }
            return false
        }

        //长按监听
        /*override fun onLongPress(e: MotionEvent) {

        }*/

        //快速滑动监听
        override fun onFling(
            e1: MotionEvent,
            e2: MotionEvent,
            velocityX: Float,
            velocityY: Float
        ): Boolean {
            if (isSetVScale) {
                scroller.fling(offsetX.toInt(), offsetY.toInt(), velocityX.toInt(), velocityY.toInt(),
                    -(bitmap.width * canvasScaleValue - width).toInt() / 2, (bitmap.width * canvasScaleValue - width).toInt() / 2,
                    -(bitmap.height * canvasScaleValue - height).toInt() / 2, (bitmap.height * canvasScaleValue - height).toInt() / 2)
                ViewCompat.postOnAnimation(this@MyScalableImageView, flingRunnable)
            }
            return false
        }

        /*private fun refresh() {
            scroller.computeScrollOffset()
            offsetX = scroller.currX.toFloat()
            offsetY = scroller.currY.toFloat()
            invalidate()
        }*/

        //设置了双击监听时，单击的监听
        override fun onSingleTapConfirmed(e: MotionEvent): Boolean {
            return false
        }

        //双击时的回调
        override fun onDoubleTap(e: MotionEvent): Boolean {
            isSetVScale = !isSetVScale
            canvasScaleValue = if (isSetVScale) {
                vScale = (height.toFloat() / bitmap.height)
                vScale *= VER_EXTER_PERCENT
                vScale
            } else {
                hScale
            }
            if (isSetVScale) {
                //获取到双击的位置，以双击坐标处进行中心放大
//                offsetX = (e.x - width / 2) * (1 - vScale / hScale)
                offsetX = -((e.x - width / 2) * vScale / hScale - (e.x - width / 2))
//                offsetY = (e.y - height / 2) * (1 - vScale / hScale)
                offsetY = -((e.y - height / 2) * 1 - vScale / hScale - (e.y - height / 2))
                setBorder()
                if (scaleAnimator.values.size == 1) {
                    scaleAnimator.setFloatValues(hScale, canvasScaleValue)
                }
                scaleAnimator.start()
            } else {
                //缩小的时候，让图片回到最初的位置，避免缩小后错位--这种方式不可取，如果动画时间比较长，图片会突然位移
//            offsetX = 0f
//            offsetY = 0f
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

    /**
     * 双指缩放辅助类
     */
    inner class MyScaleGestureListener : OnScaleGestureListener {
        /**
         * 这个方法的返回值true表示从起点开始的变化
         * 返回false表示上一刻的变化
         */
        override fun onScale(detector: ScaleGestureDetector): Boolean {
            val tempCurrentScale = canvasScaleValue * detector.scaleFactor
            return if (tempCurrentScale < hScale || tempCurrentScale > vScale) {
                false
            } else {
                canvasScaleValue *= detector.scaleFactor
                true
            }
        }

        override fun onScaleBegin(detector: ScaleGestureDetector): Boolean {
            offsetX = (detector.focusX - width / 2f) * (1 - vScale / hScale)
            offsetY = (detector.focusY - height / 2f) * (1 - vScale / hScale)
            return true
        }

        override fun onScaleEnd(detector: ScaleGestureDetector) {

        }
    }

}