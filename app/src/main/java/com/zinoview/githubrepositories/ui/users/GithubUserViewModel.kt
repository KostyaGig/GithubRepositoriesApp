package com.zinoview.githubrepositories.ui.users

import com.zinoview.githubrepositories.core.DisposableStore
import com.zinoview.githubrepositories.core.SaveState
import com.zinoview.githubrepositories.ui.core.*
import com.zinoview.githubrepositories.ui.core.cache.SaveCache
import com.zinoview.githubrepositories.ui.users.cache.LocalGithubUserRequest


/**
 * @author Zinoview on 19.08.2021
 * k.gig@list.ru
 */
interface GithubUserViewModel<T : CommunicationModel> : ViewModel<T>, SaveState {

    fun user(query: String)

    fun users()

    class Base(
        githubUserRemoteRequest: BaseGithubUserRequest,
        private val githubUserLocalRequest: LocalGithubUserRequest,
        githubUserDisposableStore: DisposableStore,
        communication: GithubUserCommunication,
        saveCache: SaveCache<UiGithubUserState>
    ) : BaseViewModel<UiGithubUserState>(
        communication,
        githubUserDisposableStore,
        githubUserRemoteRequest,
        saveCache
    ), GithubUserViewModel<UiGithubUserState> {

        override fun user(query: String)
            = githubUserLocalRequest.data(query)

        override fun users()
            = githubUserLocalRequest.request()

        override fun saveState(state: CollapseOrExpandState)
            = githubUserLocalRequest.saveState(state)
    }

}