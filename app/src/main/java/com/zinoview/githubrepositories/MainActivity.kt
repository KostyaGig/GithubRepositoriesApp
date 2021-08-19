package com.zinoview.githubrepositories

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import io.reactivex.Scheduler
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

//todo remove it
fun Any.message(message: String) {
    Log.d("GithubTest",message)
}

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val client =
            OkHttpClient.Builder()
                .addInterceptor(HttpLoggingInterceptor().apply {
                    level = HttpLoggingInterceptor.Level.BODY
                })
                .build()

        val retrofit = Retrofit.Builder()
            .baseUrl("https://api.github.com")
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .client(client)
            .build()

        val service = retrofit.create(GithubService::class.java)
        val repoDao = RepoDatabase.database(this).dao()

        val repositories = service
            .reposUser()
            .subscribeOn(Schedulers.io())
            .map {repositories ->
                val cacheRepositories = repositories.map { it.map() }
                repoDao.insertRepositories(cacheRepositories)
                repositories
            }
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { repo ->
                    message("Single -> onSuccess -> currentThread: ${Thread.currentThread().name}")
                    repo.forEach {
                        it.print()
                    }
                }, { error ->
                    message("Single -> onError -> ${error.message} ")
                }
            )


        findViewById<TextView>(R.id.tv).setOnClickListener {
            repoDao.repositories()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ repo ->
                    message("Single -> onSuccess -> currentThread: ${Thread.currentThread().name}")
                    repo.forEach {
                        it.print()
                    }
                },{error ->
                    message("Single -> onError -> ${error.message} ")
                })
        }
    }
}
