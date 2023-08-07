package com.example.newproject.ui.utils

import android.content.Context
import android.content.res.Resources
import android.util.TypedValue

object SpUtils {

    fun dp2px(value: Float): Float {
        return TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_DIP,
            value,
            Resources.getSystem().displayMetrics
        )
    }

    fun dp2px(value: Int) : Int {
        return TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_DIP,
            value.toFloat(),
            Resources.getSystem().displayMetrics
        ).toInt()
    }

}
