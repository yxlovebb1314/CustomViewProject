package com.example.newproject.ui.utils

/**
 * 扩展属性，给Float新增一个属性px，重写get方法，调用dp2px来进行转换
 */
val Float.dp
    get() = SpUtils.dp2px(this)

val Int.dp
    get() = this.toFloat().dp