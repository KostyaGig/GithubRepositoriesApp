package com.zinoview.githubrepositories.ui.repositories


import com.zinoview.githubrepositories.core.GithubDisposableStore
import com.zinoview.githubrepositories.data.core.Save
import com.zinoview.githubrepositories.data.repositories.DataGithubRepository
import com.zinoview.githubrepositories.ui.core.*
import com.zinoview.githubrepositories.ui.repositories.cache.Local
import com.zinoview.githubrepositories.ui.users.CollapseOrExpandState
import com.zinoview.githubrepositories.ui.users.UiGithubUserState
import io.reactivex.Single


/**
 * @author Zinoview on 21.08.2021
 * k.gig@list.ru
 */

interface GithubRepositoryViewModel<T : CommunicationModel> : ViewModel<T> {

    fun repository(userName: String,repo: String)

    fun repositoriesByState(owner: String,state: CollapseOrExpandState)


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

        override fun repositoriesByState(owner: String, state: CollapseOrExpandState)
            = githubRepositoryLocalRequest.repositoriesByState(owner,state)
    }
}