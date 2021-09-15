package com.zinoview.githubrepositories.ui.repositories

import com.zinoview.githubrepositories.core.Abstract


/**
 * @author Zinoview on 19.08.2021
 * k.gig@list.ru
 */

interface GithubRepositoryItemViewTypeFactory : Abstract.FactoryMapper<UiGithubRepositoryState, Int> {

    class Base : GithubRepositoryItemViewTypeFactory {

        override fun map(src: UiGithubRepositoryState): Int = when(src) {
            is UiGithubRepositoryState.Progress -> 1
            is UiGithubRepositoryState.Base -> 2
            is UiGithubRepositoryState.Empty -> 3
            else -> 4
        }
    }
}

