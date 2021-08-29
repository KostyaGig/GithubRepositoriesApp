package com.zinoview.githubrepositories.ui.users

import com.zinoview.githubrepositories.core.GithubDisposableStore
import com.zinoview.githubrepositories.data.users.DataGithubUser
import com.zinoview.githubrepositories.ui.core.*
import com.zinoview.githubrepositories.ui.repositories.UiGithubRepositoryState
import com.zinoview.githubrepositories.ui.users.cache.LocalGithubUserRequest
import io.reactivex.Single


/**
 * @author Zinoview on 19.08.2021
 * k.gig@list.ru
 */
interface GithubUserViewModel<T : CommunicationModel> : ViewModel<T> {

    fun cachedUser(query: String)

    fun users()

    //todo move this method to BaseViewModel later which i add same feature to repository
    fun usersByState(state: CollapseOrExpandState)

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

        override fun usersByState(state: CollapseOrExpandState)
            = githubUserLocalRequest.usersByState(state)
    }

}