package com.zinoview.githubrepositories.ui.repositories.cache

import com.zinoview.githubrepositories.core.*
import com.zinoview.githubrepositories.domain.repositories.GithubRepositoryInteractor
import com.zinoview.githubrepositories.ui.repositories.GithubRepositoryCommunication
import com.zinoview.githubrepositories.ui.repositories.UiGithubRepository
import com.zinoview.githubrepositories.ui.repositories.UiGithubRepositoryState
import com.zinoview.githubrepositories.ui.users.CollapseOrExpandState

interface Local : SaveState {

    class Base(
        private val githubRepositoryInteractor: GithubRepositoryInteractor
    ) : Local {

        override fun saveState(state: CollapseOrExpandState)
            = githubRepositoryInteractor.saveState(state)
    }
}