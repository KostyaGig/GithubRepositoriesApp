package com.zinoview.githubrepositories.core

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStoreOwner

import com.zinoview.githubrepositories.data.core.prefs.CachedState
import com.zinoview.githubrepositories.data.repositories.cache.prefs.RepositoryCachedState
import com.zinoview.githubrepositories.data.users.cache.prefs.UserCachedState

import com.zinoview.githubrepositories.sl.core.CoreModule
import com.zinoview.githubrepositories.sl.core.DependencyContainer
import com.zinoview.githubrepositories.sl.core.ViewModelFactory


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

    override fun onCreate() {
        super.onCreate()

        coreModule.init(this)
        userCachedState = coreModule.userCachedState
        repositoryCachedState = coreModule.repositoryCachedState

    }

    fun <T : ViewModel> viewModel(clazz: Class<T>,owner: ViewModelStoreOwner)
        = ViewModelProvider(owner,viewModelFactory).get(clazz)

    fun <T : CachedState> cachedState(clazz: Class<T>)
        = CachedStateFactory<T>(userCachedState, repositoryCachedState).map(clazz)

    fun resource() = coreModule.resource
}