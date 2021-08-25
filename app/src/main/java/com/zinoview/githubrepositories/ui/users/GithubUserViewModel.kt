package com.zinoview.githubrepositories.ui.users

import com.zinoview.githubrepositories.ui.core.BaseGithubUserRequest
import com.zinoview.githubrepositories.ui.core.BaseViewModel
import com.zinoview.githubrepositories.ui.core.Observe
import com.zinoview.githubrepositories.ui.users.cache.LocalGithubUserRequest


/**
 * @author Zinoview on 19.08.2021
 * k.gig@list.ru
 */
interface GithubUserViewModel : Observe<UiGithubUserState> {

    fun cachedUser(query: String)

    fun users()

    class Base(
        githubUserRemoteRequest: BaseGithubUserRequest,
        private val githubUserLocalRequest: LocalGithubUserRequest,
        githubUserDisposableStore: GithubDisposableStore,
        communication: GithubUserCommunication
    ) : BaseViewModel<UiGithubUserState>(
        communication,
        githubUserDisposableStore,
        githubUserRemoteRequest
    ), GithubUserViewModel {

        override fun cachedUser(query: String)
            = githubUserLocalRequest.data(query)

        override fun users()
            = githubUserLocalRequest.request()
    }

}