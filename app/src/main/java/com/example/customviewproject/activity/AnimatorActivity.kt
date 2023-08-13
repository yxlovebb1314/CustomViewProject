package com.example.customviewproject.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.customviewproject.R
import com.example.customviewproject.databinding.ActivityAnimatorBinding
import com.example.newproject.ui.utils.dp

class AnimatorActivity : AppCompatActivity() {

    private lateinit var binding : ActivityAnimatorBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAnimatorBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setListener()
    }

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

    }
}