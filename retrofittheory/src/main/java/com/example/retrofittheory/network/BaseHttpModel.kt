package com.example.retrofittheory.network

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.core.SingleObserver
import io.reactivex.rxjava3.schedulers.Schedulers

abstract class BaseHttpModel {

    fun <T> setMainOb(single : Single<T>, observer : SingleObserver<T>) {
        single
//            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(observer)
    }

    fun <T> setWorkThreadOb(single : Single<T>, observer : SingleObserver<T>) {
        single
//            .subscribeOn(Schedulers.io())
            .observeOn(Schedulers.newThread())
            .subscribe(observer)
    }

}