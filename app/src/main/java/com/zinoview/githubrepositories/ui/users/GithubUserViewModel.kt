package com.zinoview.githubrepositories.ui.users

import com.zinoview.githubrepositories.core.GithubDisposableStore
import com.zinoview.githubrepositories.ui.core.*
import com.zinoview.githubrepositories.ui.repositories.UiGithubRepositoryState
import com.zinoview.githubrepositories.ui.users.cache.LocalGithubUserRequest


/**
 * @author Zinoview on 19.08.2021
 * k.gig@list.ru
 */
interface GithubUserViewModel<T : CommunicationModel> : ViewModel<T> {

    fun cachedUser(query: String)

    fun users()

    class Base(
        githubUserRemoteRequest: BaseGithubUserRequest,
        private val githubUserLocalRequest: LocalGithubUserRequest,
        githubUserDisposableStore: GithubDisposableStore,
        communication: GithubUserCommunication,
        saveCache: SaveCache<UiGithubUserState>
    ) : BaseViewModel<UiGithubUserState>(
        communication,
        githubUserDisposableStore,
        githubUserRemoteRequest,
        saveCache
    ), GithubUserViewModel<UiGithubUserState> {

        override fun cachedUser(query: String)
            = githubUserLocalRequest.data(query)

        override fun users()
            = githubUserLocalRequest.request()
    }

}