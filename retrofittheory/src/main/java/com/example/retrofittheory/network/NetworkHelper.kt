package com.example.retrofittheory.network

import io.reactivex.rxjava3.plugins.RxJavaPlugins
import io.reactivex.rxjava3.schedulers.Schedulers
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class NetworkHelper private constructor() {

    companion object {

        private var instance: NetworkHelper? = null

        fun getInstance(): NetworkHelper {
            if (instance == null) {
                synchronized(NetworkHelper::class) {
                    if (instance == null) {
                        instance = NetworkHelper()
                    }
                }
            }
            return instance!!
        }
    }

    lateinit var mRetrofit: Retrofit
    lateinit var mService: GithubService

    private fun getRetrofit(): Retrofit {
        synchronized(NetworkHelper::class) {
            mRetrofit = Retrofit.Builder()
                .baseUrl("https://api.github.com/")
                .client(getClient())
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava3CallAdapterFactory.createWithScheduler(Schedulers.io()))
                .build()
        }
        return mRetrofit
    }

    private fun getClient(): OkHttpClient {
        val builder = OkHttpClient.Builder()
        return builder
            .callTimeout(15, TimeUnit.SECONDS)
            .connectTimeout(15, TimeUnit.SECONDS)
            .writeTimeout(15, TimeUnit.SECONDS)
            .build()
    }

    fun getService(): GithubService {
        mService = getRetrofit().create(GithubService::class.java)
        return mService
    }
}