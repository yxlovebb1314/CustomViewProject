package com.example.customviewproject.utils

import android.content.res.Resources
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import com.example.customviewproject.R

object BitmapUtils {

    fun getAvatar(resources: Resources, width: Int, bitmapSrc : Int = R.drawable.touxiang2) : Bitmap {
        val options = BitmapFactory.Options()
        //预加载图片，只加载图片的参数
        options.inJustDecodeBounds = true
        BitmapFactory.decodeResource(resources, bitmapSrc, options)
        options.inJustDecodeBounds = false
        options.inDensity = options.outWidth
        options.inTargetDensity = width
        return BitmapFactory.decodeResource(resources, bitmapSrc, options)
    }

}