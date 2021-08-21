package com.zinoview.githubrepositories.core

import android.app.Application
import com.zinoview.githubrepositories.data.users.DataGithubUserMapper
import com.zinoview.githubrepositories.data.users.GithubUserRepository
import com.zinoview.githubrepositories.data.users.cache.*
import com.zinoview.githubrepositories.data.users.cloud.GithubCloudDataSource
import com.zinoview.githubrepositories.data.users.cloud.GithubService
import com.zinoview.githubrepositories.domain.users.DomainGithubUserMapper
import com.zinoview.githubrepositories.domain.users.GithubUserInteractor
import com.zinoview.githubrepositories.ui.users.*
import com.zinoview.githubrepositories.ui.users.cache.Local
import io.reactivex.disposables.CompositeDisposable
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory


/**
 * @author Zinoview on 19.08.2021
 * k.gig@list.ru
 */
class GAApp : Application() {

    lateinit var githubUserViewModel: GithubUserViewModel
    lateinit var githubUserInteractor: GithubUserInteractor

    override fun onCreate() {
        super.onCreate()

        val resource = Resource.Base(this)

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

        val githubUserService = retrofit.create(GithubService::class.java)

        val githubUsersDao = WrapDatabase.GithubUser(this).daoDatabase()

        val githubUserRepository = GithubUserRepository.Base(
            GithubCloudDataSource.Base(githubUserService),
            GithubCacheDataSource.Base(githubUsersDao),
            DataGithubUserMapper(),
            CacheGithubUserMapper()
        )

        githubUserInteractor = GithubUserInteractor.Base(
            githubUserRepository,
            DomainGithubUserMapper()
        )

        val githubUserCommunication = GithubUserCommunication.Base()
        val githubUserDisposableStore = GithubDisposableStore.Base(
            CompositeDisposable()
        )

        val uiGithubExceptionMapper = UiGithubExceptionMapper(resource)

        val githubUserRemoteRequest = Remote(
            githubUserInteractor,
            githubUserCommunication,
            githubUserDisposableStore,
            UiGithubUserMapper(),
            uiGithubExceptionMapper
        )

        val mappers = Triple(
            UiGithubUserMapper(),
            UiGithubUserStateMapper(),
            uiGithubExceptionMapper
        )

        val githubUserLocalRequest = Local(
            githubUserInteractor,
            githubUserCommunication,
            githubUserDisposableStore,
            resource,
            mappers
        )

        githubUserViewModel = GithubUserViewModel.Base(
            githubUserRemoteRequest,
            githubUserLocalRequest,
            githubUserDisposableStore,
            githubUserCommunication,
        )
    }
}