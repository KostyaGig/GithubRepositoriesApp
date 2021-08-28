package com.zinoview.githubrepositories.ui.repositories


import com.zinoview.githubrepositories.core.GithubDisposableStore
import com.zinoview.githubrepositories.data.core.Save
import com.zinoview.githubrepositories.ui.core.*
import com.zinoview.githubrepositories.ui.users.UiGithubUserState


/**
 * @author Zinoview on 21.08.2021
 * k.gig@list.ru
 */

interface GithubRepositoryViewModel<T : CommunicationModel> : ViewModel<T> {

    fun repository(userName: String,repo: String)

    class Base(
        private val githubRepositoryRemoteRequest: Remote,
        communication: GithubRepositoryCommunication,
        githubRepositoryDisposableStore: GithubDisposableStore,
        saveCache: SaveCache<UiGithubRepositoryState>
    ) : BaseViewModel<UiGithubRepositoryState>(
        communication,
        githubRepositoryDisposableStore,
        githubRepositoryRemoteRequest,
        saveCache
    ), GithubRepositoryViewModel<UiGithubRepositoryState> {

        override fun repository(userName: String, repo: String)
            = githubRepositoryRemoteRequest.repository(userName, repo)
    }
}