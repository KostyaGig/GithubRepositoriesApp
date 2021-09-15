package com.zinoview.githubrepositories.ui.repositories


import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import com.zinoview.githubrepositories.core.DisposableStore
import com.zinoview.githubrepositories.core.SaveState
import com.zinoview.githubrepositories.ui.core.*
import com.zinoview.githubrepositories.ui.core.cache.SaveCache
import com.zinoview.githubrepositories.ui.repositories.cache.Local
import com.zinoview.githubrepositories.ui.repositories.download.DownloadRepositoryCommunication
import com.zinoview.githubrepositories.ui.repositories.download.UiGithubDownloadFileState
import com.zinoview.githubrepositories.ui.users.CollapseOrExpandState


/**
 * @author Zinoview on 21.08.2021
 * k.gig@list.ru
 */

interface GithubRepositoryViewModel<T : CommunicationModel> : ViewModel<T>, SaveState {

    fun repository(userName: String,repo: String)

    fun downloadRepository(owner: String, repo: String)

    fun downloadRepoObserve(owner: LifecycleOwner,observer: Observer<List<UiGithubDownloadFileState>>)
    class Base(
        private val githubRepositoryRemoteRequest: Remote,
        private val githubRepositoryLocalRequest: Local,
        repoCommunication: GithubRepositoryCommunication,
        private val repoDownloadCommunication: DownloadRepositoryCommunication,
        githubRepositoryDisposableStore: DisposableStore,
        saveCache: SaveCache<UiGithubRepositoryState>
    ) : BaseViewModel<UiGithubRepositoryState>(
        repoCommunication,
        githubRepositoryDisposableStore,
        githubRepositoryRemoteRequest,
        saveCache
    ), GithubRepositoryViewModel<UiGithubRepositoryState> {

        override fun repository(userName: String, repo: String)
            = githubRepositoryRemoteRequest.repository(userName, repo)

        override fun saveState(state: CollapseOrExpandState)
            = githubRepositoryLocalRequest.saveState(state)

        override fun downloadRepository(owner: String, repo: String)
            = githubRepositoryRemoteRequest.downloadRepository(owner, repo)

        override fun downloadRepoObserve(
            owner: LifecycleOwner,
            observer: Observer<List<UiGithubDownloadFileState>>
        ) = repoDownloadCommunication.observe(owner,observer)
    }
}