package com.example.customviewproject.view

import android.animation.Animator
import android.animation.Animator.AnimatorListener
import android.animation.ObjectAnimator
import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.util.AttributeSet
import android.util.Log
import androidx.core.graphics.toColorInt
import com.example.customviewproject.R
import com.example.customviewproject.utils.TAG
import com.example.newproject.ui.utils.dp

private val hintText : String = "请输入UserName"
private val TEXT_SIZE = 16.dp

class MeterialEditText(context: Context, attrs: AttributeSet?) : androidx.appcompat.widget.AppCompatEditText(context, attrs) {

    private var isOpenFunction = true //对外部提供的控制开关

    private val textPaint = Paint(Paint.ANTI_ALIAS_FLAG).also {
        it.textSize = TEXT_SIZE
        it.color = "#aabb00".toColorInt()
    }

    var animatorValue = 0f
        set(value) {
            field = value
            invalidate()
        }

    private val animator = ObjectAnimator.ofFloat(this, "animatorValue", 0f, 1f).also {
        it.duration = 250
    }

    private var oldPaddingTop = 0  //改变顶部padding值之前，记录一下初始值

    init {
        oldPaddingTop = paddingTop
        //读取xml中的自定义属性
        val styleArr = context.obtainStyledAttributes(attrs, R.styleable.MeterialEditText)
        isOpenFunction = styleArr.getBoolean(R.styleable.MeterialEditText_userFunction, false)
        styleArr.recycle()
    }

    fun isOpenFunction(isOpen : Boolean) {
        if (isOpen) {
            setNewPadding()
            showAnimator()
            isShowTitleText = true
            isOpenFunction = isOpen
        } else {
            hintAnimator {
                setOldPadding()
                isShowTitleText = false
                isOpenFunction = isOpen
            }
        }
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
//        Log.e(TAG, "onSizeChanged: ")

    }

    private var isShowTitleText = false  //内部判断是否显示title的标记

    override fun onTextChanged(
        text: CharSequence?,
        start: Int,
        lengthBefore: Int,
        lengthAfter: Int
    ) {
        super.onTextChanged(text, start, lengthBefore, lengthAfter)
        if (isOpenFunction) {
            if (text.isNullOrEmpty() && isShowTitleText) {
                //动画完成后再改变padding
                hintAnimator {
                    setOldPadding()
                    isShowTitleText = false
                }
            } else if(!text.isNullOrEmpty() && !isShowTitleText) {
                setNewPadding()
                showAnimator()
                isShowTitleText = true
            }
        }

        Log.e(TAG, "onTextChanged:     before:${lengthBefore}, after:${lengthAfter} , start:${start}")
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        if (isOpenFunction) {
            if (isShowTitleText) {
                Log.e(TAG, "onDraw: 透明度:${(animatorValue * 0xff).toInt()} , 绘制高度: ${(oldPaddingTop + TEXT_SIZE) * animatorValue}")
                textPaint.alpha = (animatorValue * 0xff).toInt()  //设置透明度
                canvas.drawText(hintText, paddingLeft.toFloat(), (oldPaddingTop + TEXT_SIZE) * animatorValue, textPaint)
            }
        }
    }

    private fun setOldPadding() {
        setPadding(paddingLeft, oldPaddingTop, paddingRight, paddingBottom)
    }

    private fun setNewPadding() {
        setPadding(paddingLeft, (oldPaddingTop + TEXT_SIZE + 10.dp).toInt(), paddingRight, paddingBottom)
    }

    private fun showAnimator() {
        animator.start()
    }

    private fun hintAnimator(listener : () -> Unit) {
        animator.addListener(object : AnimatorListener{
            override fun onAnimationStart(animation: Animator) {
            }

            override fun onAnimationEnd(animation: Animator) {
                animator.removeListener(this)
                listener()
//                setOldPadding()
//                isShowTitleText = false
            }

            override fun onAnimationCancel(animation: Animator) {
            }

            override fun onAnimationRepeat(animation: Animator) {
            }

        })
        animator.reverse()
    }
}