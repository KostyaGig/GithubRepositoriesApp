package com.zinoview.githubrepositories.ui.repositories


import com.zinoview.githubrepositories.ui.core.BaseViewModel
import com.zinoview.githubrepositories.ui.core.Observe
import com.zinoview.githubrepositories.ui.core.message
import com.zinoview.githubrepositories.ui.users.GithubDisposableStore


/**
 * @author Zinoview on 21.08.2021
 * k.gig@list.ru
 */

//todo compare this viewmodel with GithubUserViewModel
interface GithubRepositoryViewModel : Observe<UiGithubRepositoryState> {

    fun repositories(userName: String)

    fun repository(userName: String,repo: String)

    class Base(
        private val githubRepositoryRemoteRequest: Remote,
        communication: GithubRepositoryCommunication,
        private val githubRepositoryDisposableStore: GithubDisposableStore
    ) : BaseViewModel<UiGithubRepositoryState>(communication), GithubRepositoryViewModel {

        override fun repositories(userName: String)
            = githubRepositoryRemoteRequest.repositories(userName)

        override fun repository(userName: String, repo: String)
            = githubRepositoryRemoteRequest.repository(userName, repo)

        override fun onCleared() {
            message("githubRepositoryViewModel onCleared")
            githubRepositoryDisposableStore.dispose()
            super.onCleared()
        }
    }
}