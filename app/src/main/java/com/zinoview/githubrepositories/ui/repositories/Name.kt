package com.zinoview.githubrepositories.ui.repositories

import com.zinoview.githubrepositories.ui.core.BaseFragment
import com.zinoview.githubrepositories.ui.core.BaseViewModel
import com.zinoview.githubrepositories.ui.core.message
import com.zinoview.githubrepositories.ui.repositories.fragment.GithubRepositoriesFragment
import com.zinoview.githubrepositories.ui.users.CollapseOrExpandState


/**
 * @author Zinoview on 24.08.2021
 * k.gig@list.ru
 */
interface Name {

    fun addName(name: String?)

    fun searchRepository(repo: String,viewModel: GithubRepositoryViewModel<UiGithubRepositoryState>)

    fun repositories(viewModel: BaseViewModel<UiGithubRepositoryState>)

    fun currentName() : String

    class GithubUserName : Name {

        private var name: String? = null

        override fun addName(name: String?) {
            this.name = name
        }

        override fun searchRepository(repo: String,viewModel: GithubRepositoryViewModel<UiGithubRepositoryState>)
            = viewModel.repository(name.isNotNull(),repo)

        override fun repositories(viewModel: BaseViewModel<UiGithubRepositoryState>)
            = viewModel.data(name.isNotNull())

        override fun currentName(): String
            = name.isNotNull()

        private fun String?.isNotNull() : String
            = this ?: throw IllegalStateException(" Name -> GithubUserName -> User name repository is null")
    }
}