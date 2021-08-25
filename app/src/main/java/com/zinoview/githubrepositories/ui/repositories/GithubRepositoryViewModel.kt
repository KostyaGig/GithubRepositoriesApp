package com.zinoview.githubrepositories.ui.repositories


import com.zinoview.githubrepositories.ui.core.BaseViewModel
import com.zinoview.githubrepositories.ui.core.Observe
import com.zinoview.githubrepositories.ui.core.message
import com.zinoview.githubrepositories.ui.users.GithubDisposableStore


/**
 * @author Zinoview on 21.08.2021
 * k.gig@list.ru
 */

interface GithubRepositoryViewModel : Observe<UiGithubRepositoryState> {

    fun repository(userName: String,repo: String)

    class Base(
        private val githubRepositoryRemoteRequest: Remote,
        communication: GithubRepositoryCommunication,
        githubRepositoryDisposableStore: GithubDisposableStore
    ) : BaseViewModel<UiGithubRepositoryState>(
        communication,
        githubRepositoryDisposableStore,
        githubRepositoryRemoteRequest
    ), GithubRepositoryViewModel {

        override fun repository(userName: String, repo: String)
            = githubRepositoryRemoteRequest.repository(userName, repo)
    }
}