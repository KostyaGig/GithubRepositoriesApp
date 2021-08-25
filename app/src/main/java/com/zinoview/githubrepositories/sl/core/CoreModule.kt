package com.zinoview.githubrepositories.sl.core

import android.content.Context
import com.zinoview.githubrepositories.core.Abstract
import com.zinoview.githubrepositories.core.Resource
import com.zinoview.githubrepositories.data.core.GithubAppDatabase
import com.zinoview.githubrepositories.data.core.GithubDao
import com.zinoview.githubrepositories.data.core.Text
import com.zinoview.githubrepositories.ui.core.Communication
import com.zinoview.githubrepositories.ui.core.CommunicationModel
import com.zinoview.githubrepositories.ui.users.UiGithubExceptionMapper
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory


/**
 * @author Zinoview on 25.08.2021
 * k.gig@list.ru
 */
class CoreModule {

    lateinit var retrofit: Retrofit
    lateinit var resource: Resource
    lateinit var githubDao: GithubDao
    lateinit var exceptionMapper: Abstract.FactoryMapper<Throwable,String>
    lateinit var text: Text

    fun init(context: Context) {

        network()

        resource = Resource.Base(context)

        githubDao = GithubAppDatabase.database(context).dao()
        exceptionMapper = UiGithubExceptionMapper(resource)

        text = Text.GithubName()
    }

    private fun network() {
        val client =
            OkHttpClient.Builder()
                .addInterceptor(HttpLoggingInterceptor().apply {
                    level = HttpLoggingInterceptor.Level.BODY
                })
                .build()

        retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .client(client)
            .build()
    }

    fun <T> service(clazz: Class<T>) = retrofit.create(clazz)

    private companion object {
        const val BASE_URL = "https://api.github.com"
    }
}