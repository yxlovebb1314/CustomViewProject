package com.example.customviewproject.activity

import android.animation.AnimatorSet
import android.animation.Keyframe
import android.animation.ObjectAnimator
import android.animation.PropertyValuesHolder
import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.animation.LinearInterpolator
import com.example.customviewproject.databinding.ActivityAnimatorBinding
import com.example.newproject.ui.utils.dp

class AnimatorActivity : AppCompatActivity() {

    private lateinit var binding : ActivityAnimatorBinding

    private lateinit var objAnimator : ObjectAnimator

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAnimatorBinding.inflate(layoutInflater)
        setContentView(binding.root)
        objAnimator = ObjectAnimator.ofFloat(binding.clockView, "angleM", 30f * 25)
        setListener()
    }

    @SuppressLint("Recycle")
    private fun setListener() {
        //ViewPropertyAnimator的简单使用
        binding.btStart1.setOnClickListener {
            binding.imgActAnimatorImageView.animate()
                .translationX(100.dp)
                .translationY(100.dp)
                .rotation(90f)
                .scaleX(2f)
                .scaleY(2f)
                .setDuration(200)
                .setStartDelay(1000)
                .start()
        }

        //ObjectAnimator的简单使用
        binding.btStart2.setOnClickListener {
            //将angleM的值不断增加到30f * 25的动画过程
            objAnimator.duration = 2000
            objAnimator.start()
        }

        binding.resetClock1.setOnClickListener {
            binding.clockView.resetClock()
        }

        //AnimatorSet的使用
        binding.btStart3.setOnClickListener {
            //先让分针转动，再让秒针转动
            val a1 = ObjectAnimator.ofFloat(binding.clockView2, "angleM", 30f * 25)
            a1.duration = 2000
            a1.interpolator = LinearInterpolator()
            val a2 = ObjectAnimator.ofFloat(binding.clockView2, "angleS", 30f * 12)
            a2.duration = 1000
            val set = AnimatorSet()
            set.playSequentially(a1, a2)
            set.start()
        }

        binding.resetClock2.setOnClickListener {
            binding.clockView2.resetClock()
        }

        binding.btStart32.setOnClickListener {
            val h1 = PropertyValuesHolder.ofFloat("angleM", 30f * 25)
            val h2 = PropertyValuesHolder.ofFloat( "angleS", 30f * 24)
            val obj = ObjectAnimator.ofPropertyValuesHolder(binding.clockView2,h1, h2)
            obj.duration = 1000
            obj.start()
        }

        binding.btStart4.setOnClickListener {
            //关键帧KeyFrame可以将一个动画更细致的拆分成多个可操控的小动画
            //其中第一个参数，表示阶段的百分比，第二个参数表示这个阶段所要变化的值
            val k1 = Keyframe.ofFloat(0f, 30f * 7)
            val k2 = Keyframe.ofFloat(0.2f, 30f * 10)
            val k3 = Keyframe.ofFloat(0.5f, 30f * 20)
            val k4 = Keyframe.ofFloat(1f, 30f * 25)
            val keyHolder = PropertyValuesHolder.ofKeyframe("angleM", k1,k2,k3,k4)
            val objAnimator = ObjectAnimator.ofPropertyValuesHolder(binding.clockView3, keyHolder)
            objAnimator.duration = 2000
            objAnimator.start()
        }

        binding.resetClock3.setOnClickListener {
            binding.clockView3.resetClock()
        }

        binding.btStart5.setOnClickListener {
            binding.customTextView.startAnimator()
        }
    }
}