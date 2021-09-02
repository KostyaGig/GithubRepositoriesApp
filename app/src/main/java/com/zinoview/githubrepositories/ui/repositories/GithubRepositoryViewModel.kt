package com.zinoview.githubrepositories.ui.repositories


import com.zinoview.githubrepositories.core.GithubDisposableStore
import com.zinoview.githubrepositories.core.SaveState
import com.zinoview.githubrepositories.ui.core.*
import com.zinoview.githubrepositories.ui.core.cache.SaveCache
import com.zinoview.githubrepositories.ui.repositories.cache.Local
import com.zinoview.githubrepositories.ui.users.CollapseOrExpandState


/**
 * @author Zinoview on 21.08.2021
 * k.gig@list.ru
 */

interface GithubRepositoryViewModel<T : CommunicationModel> : ViewModel<T>, SaveState {

    fun repository(userName: String,repo: String)

    class Base(
        private val githubRepositoryRemoteRequest: Remote,
        private val githubRepositoryLocalRequest: Local,
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

        override fun saveState(state: CollapseOrExpandState)
            = githubRepositoryLocalRequest.saveState(state)
    }
}