package com.example.retrofittheory.network

import com.example.retrofittheory.entity.Repo
import io.reactivex.rxjava3.core.SingleObserver

object HttpModel : BaseHttpModel() {

    private val service = NetworkHelper.getInstance().getService()

    fun getReposListRx(params : String, observer : SingleObserver<List<Repo>>) {
//        service.listReposRx(params)
//            .subscribeOn(Schedulers.io())
//            .observeOn(AndroidSchedulers.mainThread())
//            .subscribe(observer)
        setMainOb(service.listReposRx(params), observer)
    }

}