package com.example.retrofittheory.network

import com.example.retrofittheory.entity.Repo
import io.reactivex.rxjava3.core.Single
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path

interface GithubService {

    @GET("users/{user}/repos")
    fun listRepos(@Path("user") user: String?): Call<List<Repo>>

    @GET("users/{user}/repos")
    fun listReposRx(@Path("user") user: String?): Single<List<Repo>>


}