package com.zinoview.githubrepositories.sl.core

import android.content.Context
import com.zinoview.githubrepositories.core.Resource
import com.zinoview.githubrepositories.data.core.GithubAppDatabase
import com.zinoview.githubrepositories.data.core.GithubDao
import com.zinoview.githubrepositories.data.core.Text
import com.zinoview.githubrepositories.data.core.prefs.CollapseOrExpandStateFactory
import com.zinoview.githubrepositories.data.core.prefs.SavedValueStateFactory
import com.zinoview.githubrepositories.data.repositories.cache.prefs.RepositoryCachedState
import com.zinoview.githubrepositories.data.repositories.download.file.CachedFile
import com.zinoview.githubrepositories.data.repositories.download.file.FileWriter
import com.zinoview.githubrepositories.data.users.cache.prefs.UserCachedState
import com.zinoview.githubrepositories.ui.users.UiGithubExceptionMapper
import okhttp3.OkHttpClient
import okhttp3.ResponseBody
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
    lateinit var client: OkHttpClient
    lateinit var githubDao: GithubDao
    lateinit var exceptionMapper: UiGithubExceptionMapper
    lateinit var text: Text

    lateinit var userCachedState: UserCachedState
    lateinit var repositoryCachedState: RepositoryCachedState

    lateinit var fileWriter: FileWriter<ResponseBody>
    lateinit var cachedFile: CachedFile

    fun init(context: Context) {

        network()

        resource = Resource.Base(context)

        githubDao = GithubAppDatabase.database(context).dao()
        exceptionMapper = UiGithubExceptionMapper.Base(resource)

        text = Text.GithubName()

        fileWriter = FileWriter.Base()
        cachedFile = CachedFile.Base(fileWriter)

        val savedValueStateFactory = SavedValueStateFactory.Base()
        val collapseOrExpandStateFactory = CollapseOrExpandStateFactory.Base(resource)

        userCachedState = UserCachedState.Base(
            context,
            savedValueStateFactory,
            collapseOrExpandStateFactory
        )

        repositoryCachedState = RepositoryCachedState.Base(
            context,
            savedValueStateFactory,
            collapseOrExpandStateFactory
        )
    }

    private fun network() {
         client =
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

    fun <T> networkService(clazz: Class<T>) = retrofit.create(clazz)

    private companion object {
        const val BASE_URL = "https://api.github.com/"

    }
}