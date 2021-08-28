package com.zinoview.githubrepositories.ui.repositories

import com.zinoview.githubrepositories.core.Abstract


/**
 * @author Zinoview on 22.08.2021
 * k.gig@list.ru
 */
class UiGithubRepositoryStateMapper : Abstract.RepositoryMapper<UiGithubRepositoryState> {

    override fun map(
        name: String,
        private: Boolean,
        language: String,
        owner: String,
        urlRepository: String,
        defaultBranch: String,
        isCollapsed: Boolean
    ): UiGithubRepositoryState
        = UiGithubRepositoryState.Base(
            UiGithubRepository(name, private, language, owner, urlRepository, defaultBranch, isCollapsed),
            isCollapsed
        )
}