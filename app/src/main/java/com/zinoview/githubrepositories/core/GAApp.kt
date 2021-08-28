package com.zinoview.githubrepositories.core

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStoreOwner
import com.zinoview.githubrepositories.data.core.GithubAppDatabase
import com.zinoview.githubrepositories.data.core.GithubDao
import com.zinoview.githubrepositories.data.users.GithubUserRepository
import com.zinoview.githubrepositories.data.users.cloud.GithubUserService

import com.zinoview.githubrepositories.sl.core.CoreModule
import com.zinoview.githubrepositories.sl.core.DependencyContainer
import com.zinoview.githubrepositories.sl.core.ViewModelFactory
import com.zinoview.githubrepositories.ui.core.SaveCache
import com.zinoview.githubrepositories.ui.repositories.CacheGithubRepositoryMapper
import com.zinoview.githubrepositories.ui.repositories.UiGithubRepositoryState
import com.zinoview.githubrepositories.ui.users.CacheGithubUserMapper
import com.zinoview.githubrepositories.ui.users.UiGithubUserState
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory


/**
 * @author Zinoview on 19.08.2021
 * k.gig@list.ru
 */
class GAApp : Application() {

    private val coreModule = CoreModule()

    private val viewModelFactory by lazy {
        ViewModelFactory(DependencyContainer.Base(
            coreModule
        ))
    }
    override fun onCreate() {
        super.onCreate()

        coreModule.init(this)
    }

    fun <T : ViewModel> viewModel(clazz: Class<T>,owner: ViewModelStoreOwner)
        = ViewModelProvider(owner,viewModelFactory).get(clazz)
}