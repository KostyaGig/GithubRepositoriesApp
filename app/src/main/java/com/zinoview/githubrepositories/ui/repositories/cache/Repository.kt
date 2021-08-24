package com.zinoview.githubrepositories.ui.repositories.cache

import com.zinoview.githubrepositories.ui.core.GithubAdapter
import com.zinoview.githubrepositories.ui.core.UiTotalCache
import com.zinoview.githubrepositories.ui.repositories.UiGithubRepositoryState


/**
 * @author Zinoview on 24.08.2021
 * k.gig@list.ru
 */

class Repository(
    list: MutableList<UiGithubRepositoryState>
) : UiTotalCache.UiGithubTotalCache<UiGithubRepositoryState>(list) {

    override fun updateAdapterByDefault(adapter: GithubAdapter<UiGithubRepositoryState>?) {
        adapter?.update(UiGithubRepositoryState.Empty.wrap())
    }
}