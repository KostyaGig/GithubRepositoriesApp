package com.zinoview.githubrepositories.ui.repositories.cache

import com.zinoview.githubrepositories.ui.core.UiTotalCache
import com.zinoview.githubrepositories.ui.core.GithubAdapter
import com.zinoview.githubrepositories.ui.repositories.UiGithubRepositoryState


/**
 * @author Zinoview on 24.08.2021
 * k.gig@list.ru
 */

class RepositoriesTotalCache(
    list: MutableList<UiGithubRepositoryState>,
    replaceItem: (UiGithubRepositoryState) -> Unit
) : UiTotalCache.UiGithubTotalCache2<UiGithubRepositoryState>(list,replaceItem) {

    override fun updateAdapterByDefault(adapter: GithubAdapter<UiGithubRepositoryState>?) {
        adapter?.update(UiGithubRepositoryState.Empty.wrap())
    }
}