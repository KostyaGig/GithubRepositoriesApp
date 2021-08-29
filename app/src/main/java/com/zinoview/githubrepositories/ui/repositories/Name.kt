package com.zinoview.githubrepositories.ui.repositories

import com.zinoview.githubrepositories.ui.core.message
import com.zinoview.githubrepositories.ui.users.CollapseOrExpandState


/**
 * @author Zinoview on 24.08.2021
 * k.gig@list.ru
 */
interface Name {

    fun addName(name: String?)

    fun searchRepository(repo: String,viewModel: GithubRepositoryViewModel<UiGithubRepositoryState>)

    fun repositoriesByState(state: CollapseOrExpandState, viewModel: GithubRepositoryViewModel<UiGithubRepositoryState>)

    class GithubUserName : Name {

        private var name: String? = null

        override fun addName(name: String?) {
            this.name = name
        }

        override fun searchRepository(repo: String,viewModel: GithubRepositoryViewModel<UiGithubRepositoryState>)
            = viewModel.repository(name.isNotNull(),repo)

        private fun String?.isNotNull() : String = this ?: throw IllegalStateException(" Name -> GithubUserName -> User name repository is null")

        override fun repositoriesByState(
            state: CollapseOrExpandState,
            viewModel: GithubRepositoryViewModel<UiGithubRepositoryState>
        ) {
            message("Name $name")
            viewModel.repositoriesByState(name.isNotNull(),state)
        }
    }
}