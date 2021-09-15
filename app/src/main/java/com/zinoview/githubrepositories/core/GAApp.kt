package com.zinoview.githubrepositories.core

import android.app.Application
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStoreOwner
import androidx.work.Configuration
import androidx.work.WorkManager
import com.zinoview.githubrepositories.data.core.prefs.CachedState
import com.zinoview.githubrepositories.data.repositories.cache.prefs.RepositoryCachedState
import com.zinoview.githubrepositories.data.users.cache.prefs.UserCachedState

import com.zinoview.githubrepositories.sl.core.CoreModule
import com.zinoview.githubrepositories.sl.core.DependencyContainer
import com.zinoview.githubrepositories.sl.core.ViewModelFactory
import com.zinoview.githubrepositories.ui.repositories.download.async.FileWorkerFactory


/**
 * @author Zinoview on 19.08.2021
 * k.gig@list.ru
 */
class GAApp : Application() {

    private lateinit var userCachedState: UserCachedState
    private lateinit var repositoryCachedState: RepositoryCachedState

    private val coreModule = CoreModule()

    private val viewModelFactory by lazy {
        ViewModelFactory(DependencyContainer.Base(
            coreModule
        ))
    }

    private val workManagerFactory by lazy {
        FileWorkerFactory(coreModule.cachedFile)
    }
    override fun onCreate() {
        super.onCreate()

        coreModule.init(this)
        userCachedState = coreModule.userCachedState
        repositoryCachedState = coreModule.repositoryCachedState

        val workManagerConfiguration = Configuration.Builder()
            .setWorkerFactory(workManagerFactory)
            .setMinimumLoggingLevel(Log.INFO)
            .build()

        WorkManager.initialize(this,workManagerConfiguration)

    }

    fun <T : ViewModel> viewModel(clazz: Class<T>,owner: ViewModelStoreOwner)
        = ViewModelProvider(owner,viewModelFactory).get(clazz)

    fun <T : CachedState> cachedState(clazz: Class<T>)
        = CachedStateFactory<T>(userCachedState, repositoryCachedState).map(clazz)

    fun resource() = coreModule.resource

}