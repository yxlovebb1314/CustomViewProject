package com.example.retrofittheory

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import com.example.retrofittheory.entity.Repo
import com.example.retrofittheory.eventbus.TestEvent
import com.example.retrofittheory.network.GithubService
import com.example.retrofittheory.network.HttpModel
import io.reactivex.rxjava3.core.SingleObserver
import io.reactivex.rxjava3.disposables.Disposable
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        EventBus.getDefault().register(this)
        EventBus.getDefault().post(TestEvent())

        val retrofit = Retrofit.Builder()
            .baseUrl("https://api.github.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val service: GithubService = retrofit.create(GithubService::class.java)

        val listRepos = service.listRepos("octocat")

        /*listRepos.enqueue(object : Callback<List<Repo>> {
            override fun onResponse(call: Call<List<Repo>>, response: Response<List<Repo>>) {
Log.e("TAG", "onResponse: ${response.body()?.get(0)?.name}")
            }

            override fun onFailure(call: Call<List<Repo>>, t: Throwable) {

            }
        })*/

//        val listReposRx = service.listReposRx("octocat")
//        listReposRx.subscribe({
//Log.e("TAG", "result: ${it[0].name}")
//        }, {
//Log.e("TAG", "onFault: ${it.message}")
//        })

        HttpModel.getReposListRx("octocat", object : SingleObserver<List<Repo>> {
            override fun onSubscribe(d: Disposable) {

            }

            override fun onError(e: Throwable) {

            }

            override fun onSuccess(t: List<Repo>) {
                Log.e("TAG", "onSuccess: >>> ${t[0].name} , ${t[0].id} ")
            }
        })



    }

    @Subscribe
    public fun getTestEvent(event: TestEvent) {

    }

    override fun onDestroy() {
        super.onDestroy()
        EventBus.getDefault().unregister(this)
    }
}
