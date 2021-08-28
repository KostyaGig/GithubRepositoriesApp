package com.zinoview.githubrepositories.ui.repositories


/**
 * @author Zinoview on 24.08.2021
 * k.gig@list.ru
 */
interface Name {

    fun addName(name: String?)

    fun searchRepository(repo: String,viewModel: GithubRepositoryViewModel<UiGithubRepositoryState>)

    class GithubUserName : Name {

        private var name: String? = null

        override fun addName(name: String?) {
            this.name = name
        }

        override fun searchRepository(repo: String,viewModel: GithubRepositoryViewModel<UiGithubRepositoryState>)
            = viewModel.repository(name.isNull(),repo)

        private fun String?.isNull() : String = this ?: throw IllegalStateException(" Name -> GithubUserName -> User name repository is null")
    }
}