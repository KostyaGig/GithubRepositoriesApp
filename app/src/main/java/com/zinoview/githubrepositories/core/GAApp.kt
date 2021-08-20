package com.zinoview.githubrepositories.core

import android.app.Application
import com.zinoview.githubrepositories.data.users.DataGithubUserMapper
import com.zinoview.githubrepositories.data.users.GithubUserRepository
import com.zinoview.githubrepositories.data.users.cloud.GithubService
import com.zinoview.githubrepositories.domain.users.DomainGithubUserMapper
import com.zinoview.githubrepositories.domain.users.GithubUserInteractor
import com.zinoview.githubrepositories.ui.users.*
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

        val githubUserRepository = GithubUserRepository.Base(
            githubUserService,
            DataGithubUserMapper()
        )
        val githubUserInteractor = GithubUserInteractor.Base(
            githubUserRepository,
            DomainGithubUserMapper()
        )

        val githubUserCommunication = GithubUserCommunication.Base()
        val githubUserDisposableStore = GithubUserDisposableStore.Base(
            CompositeDisposable()
        )
        val githubUserRequest = GithubUserRequest.Base(
            githubUserInteractor,
            githubUserCommunication,
            githubUserDisposableStore,
            UiGithubUserMapper(),
            UiGithubExceptionMapper(resource)
        )
        githubUserViewModel = GithubUserViewModel.Base(
            githubUserRequest,
            githubUserDisposableStore,
            githubUserCommunication,
        )
    }
}