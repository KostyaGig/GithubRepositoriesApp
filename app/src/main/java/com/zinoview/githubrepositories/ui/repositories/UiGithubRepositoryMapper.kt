package com.zinoview.githubrepositories.ui.repositories

import com.zinoview.githubrepositories.core.Abstract


/**
 * @author Zinoview on 21.08.2021
 * k.gig@list.ru
 */
class UiGithubRepositoryMapper : Abstract.RepositoryMapper<UiGithubRepository> {

    override fun map(
        name: String,
        private: Boolean,
        language: String,
        owner: String,
        urlRepository: String,
        defaultBranch: String,
        isCollapsed: Boolean
    ): UiGithubRepository
        = UiGithubRepository(name, private, language, owner, urlRepository, defaultBranch, isCollapsed)
}