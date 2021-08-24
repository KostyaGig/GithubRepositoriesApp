package com.zinoview.githubrepositories.core

import android.app.Application
import com.zinoview.githubrepositories.data.core.Text
import com.zinoview.githubrepositories.data.core.WrapDatabase
import com.zinoview.githubrepositories.data.repositories.GithubRepositoryRepository
import com.zinoview.githubrepositories.data.repositories.DataGithubRepositoryMapper
import com.zinoview.githubrepositories.data.repositories.cache.CacheGithubRepositoryMapper
import com.zinoview.githubrepositories.data.repositories.cache.GithubRepositoryCacheDataSource
import com.zinoview.githubrepositories.data.repositories.cloud.GithubRepositoryCloudDataSource
import com.zinoview.githubrepositories.data.repositories.cloud.GithubRepositoryService
import com.zinoview.githubrepositories.data.users.DataGithubUserMapper
import com.zinoview.githubrepositories.data.users.GithubUserRepository
import com.zinoview.githubrepositories.data.users.cache.*
import com.zinoview.githubrepositories.data.users.cloud.GithubUserCloudDataSource
import com.zinoview.githubrepositories.data.users.cloud.GithubUserService
import com.zinoview.githubrepositories.domain.repositories.DomainGithubRepositoryMapper
import com.zinoview.githubrepositories.domain.repositories.GithubRepositoryInteractor
import com.zinoview.githubrepositories.domain.users.DomainGithubUserMapper
import com.zinoview.githubrepositories.domain.users.GithubUserInteractor
import com.zinoview.githubrepositories.ui.repositories.*
import com.zinoview.githubrepositories.ui.users.*
import com.zinoview.githubrepositories.ui.users.Remote
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
    lateinit var githubRepositoryViewModel: GithubRepositoryViewModel

    lateinit var githubRepositoryRepository: GithubRepositoryRepository

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

        val githubUserService = retrofit.create(GithubUserService::class.java)

        val githubDao = WrapDatabase.Base(this).daoDatabase()

        val text = Text.GithubName()
        val githubUserRepository = GithubUserRepository.Base(
            GithubUserCloudDataSource.Base(githubUserService),
            GithubUserCacheDataSource.Base(githubDao),
            DataGithubUserMapper(text),
            CacheGithubUserMapper()
        )

        val githubUserInteractor = GithubUserInteractor.Base(
            githubUserRepository,
            DomainGithubUserMapper()
        )

        val githubUserCommunication = GithubUserCommunication()
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

        val githubRepositoryService = retrofit.create(GithubRepositoryService::class.java)

        githubRepositoryRepository = GithubRepositoryRepository.Base(
            GithubRepositoryCacheDataSource.Base(githubDao),
            GithubRepositoryCloudDataSource.Base(
                githubRepositoryService
            ),
            CacheGithubRepositoryMapper.Base(),
            DataGithubRepositoryMapper(text)
        )
        val githubRepositoryInteractor = GithubRepositoryInteractor.Base(
            githubRepositoryRepository,
            DomainGithubRepositoryMapper()
        )

        val githubRepositoryDisposableStore = GithubDisposableStore.Base(CompositeDisposable())
        val githubRepositoryCommunication = GithubRepositoryCommunication()
        val githubRepositoryRemoteRequest = com.zinoview.githubrepositories.ui.repositories.Remote.Base(
            githubRepositoryInteractor,
            githubRepositoryCommunication,
            githubRepositoryDisposableStore,
            UiGithubRepositoryMapper(),
            UiGithubRepositoryStateMapper(),
            mappers.third
        )
        githubRepositoryViewModel = GithubRepositoryViewModel.Base(
            githubRepositoryRemoteRequest,
            githubRepositoryCommunication,
            githubRepositoryDisposableStore
        )
    }
}