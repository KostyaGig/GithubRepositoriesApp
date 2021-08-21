package com.zinoview.githubrepositories.ui.users

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import com.zinoview.githubrepositories.ui.users.cache.Local
import com.zinoview.githubrepositories.ui.users.cache.LocalGithubUserRequest


/**
 * @author Zinoview on 19.08.2021
 * k.gig@list.ru
 */
interface GithubUserViewModel {

    fun remoteUser(query: String)

    fun cachedUser(query: String)

    fun users()

    fun observe(owner: LifecycleOwner,observer: Observer<List<UiGithubUserState>>)

    class Base(
        private val githubUserRemoteRequest: BaseGithubUserRequest,
        private val githubUserLocalRequest: LocalGithubUserRequest,
        private val githubUserDisposableStore: GithubDisposableStore,
        private val communication: GithubUserCommunication
    ) : ViewModel(), GithubUserViewModel {

        override fun remoteUser(query: String)
            = githubUserRemoteRequest.request(query)

        override fun cachedUser(query: String)
            = githubUserLocalRequest.request(query)

        override fun users()
            = githubUserLocalRequest.request()

        override fun observe(owner: LifecycleOwner, observer: Observer<List<UiGithubUserState>>) {
            communication.observe(owner,observer)
        }

        override fun onCleared() {
            githubUserDisposableStore.dispose()
            super.onCleared()
        }
    }

}