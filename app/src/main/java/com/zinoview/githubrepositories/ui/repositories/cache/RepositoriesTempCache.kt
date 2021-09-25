package com.zinoview.githubrepositories.ui.repositories.cache

import com.zinoview.githubrepositories.ui.core.cache.UiTempCache
import com.zinoview.githubrepositories.ui.core.ItemsState
import com.zinoview.githubrepositories.ui.core.adapter.GithubAdapter
import com.zinoview.githubrepositories.ui.core.cache.StoreListTotalCache
import com.zinoview.githubrepositories.ui.repositories.UiGithubRepositoryState
import com.zinoview.githubrepositories.ui.users.CollapseOrExpandState


/**
 * @author Zinoview on 24.08.2021
 * k.gig@list.ru
 */

class RepositoriesTempCache(
    itemsState: ItemsState<CollapseOrExpandState>,
    storeListTotalCache: StoreListTotalCache<UiGithubRepositoryState>
) : UiTempCache.BaseUiGithubTotalCache<UiGithubRepositoryState>(
    itemsState,
    storeListTotalCache,
) {
    override fun updateAdapterByDefault(adapter: GithubAdapter<UiGithubRepositoryState>?) {
        adapter?.update(UiGithubRepositoryState.Empty.wrap())
    }
}