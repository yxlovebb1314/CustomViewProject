package com.example.customviewproject.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.customviewproject.R
import com.example.customviewproject.databinding.ActivityMeterialEditTextBinding

class MeterialEditTextActivity : AppCompatActivity() {

    private lateinit var binding :ActivityMeterialEditTextBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMeterialEditTextBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setListener()
    }

    private fun setListener() {
        binding.btOpen.setOnClickListener {
            binding.meteralEt.isOpenFunction(true)
        }

        binding.btClose.setOnClickListener {
            binding.meteralEt.isOpenFunction(false)
        }
    }
}