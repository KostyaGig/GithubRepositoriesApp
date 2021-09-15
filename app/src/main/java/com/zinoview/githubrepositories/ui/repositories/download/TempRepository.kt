package com.zinoview.githubrepositories.ui.repositories.download

import com.zinoview.githubrepositories.ui.core.message
import com.zinoview.githubrepositories.ui.repositories.GithubRepositoryViewModel
import com.zinoview.githubrepositories.ui.repositories.UiGithubRepositoryState


/**
 * @author Zinoview on 06.09.2021
 * k.gig@list.ru
 */

interface TempRepository {

    fun download(viewModel: GithubRepositoryViewModel<UiGithubRepositoryState>)

    fun newTempRepository(owner: String, repo: String) : Base

    data class Base(
        private val owner: String,
        private val repo: String
    ) : TempRepository{

        override fun download(viewModel: GithubRepositoryViewModel<UiGithubRepositoryState>) {
            message("Download by temp repo owner - $owner repo - $repo")
            if (isNotEmpty()) {
                viewModel.downloadRepository(owner, repo)
            }
        }

        override fun newTempRepository(owner: String, repo: String): Base
            = Base(owner, repo)

        private fun isNotEmpty() = owner.isNotEmpty() && repo.isNotEmpty()
    }
}
