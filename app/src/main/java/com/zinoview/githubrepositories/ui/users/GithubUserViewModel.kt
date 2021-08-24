package com.zinoview.githubrepositories.ui.users

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import com.zinoview.githubrepositories.ui.core.BaseGithubUserRequest
import com.zinoview.githubrepositories.ui.core.BaseViewModel
import com.zinoview.githubrepositories.ui.core.Observe
import com.zinoview.githubrepositories.ui.core.message
import com.zinoview.githubrepositories.ui.users.cache.LocalGithubUserRequest


/**
 * @author Zinoview on 19.08.2021
 * k.gig@list.ru
 */
interface GithubUserViewModel : Observe<UiGithubUserState> {

    fun remoteUser(query: String)

    fun cachedUser(query: String)

    fun users()

    class Base(
        private val githubUserRemoteRequest: BaseGithubUserRequest,
        private val githubUserLocalRequest: LocalGithubUserRequest,
        private val githubUserDisposableStore: GithubDisposableStore,
        communication: GithubUserCommunication
    ) : BaseViewModel<UiGithubUserState>(communication), GithubUserViewModel {

        override fun remoteUser(query: String)
            = githubUserRemoteRequest.request(query)

        override fun cachedUser(query: String)
            = githubUserLocalRequest.request(query)

        override fun users()
            = githubUserLocalRequest.request()

        override fun onCleared() {
            message("githubUserViewModel onCleared")
            githubUserDisposableStore.dispose()
            super.onCleared()
        }
    }

}