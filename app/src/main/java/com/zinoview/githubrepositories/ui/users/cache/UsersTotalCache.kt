package com.zinoview.githubrepositories.ui.users.cache

import com.zinoview.githubrepositories.ui.core.cache.UiTotalCache
import com.zinoview.githubrepositories.ui.core.ItemsState
import com.zinoview.githubrepositories.ui.core.adapter.GithubAdapter
import com.zinoview.githubrepositories.ui.core.cache.StoreListTotalCache
import com.zinoview.githubrepositories.ui.users.UiGithubUserState


/**
 * @author Zinoview on 24.08.2021
 * k.gig@list.ru
 */

class UsersTotalCache(
    itemsState: ItemsState,
    storeListTotalCache: StoreListTotalCache<UiGithubUserState>
) : UiTotalCache.BaseUiGithubTotalCache<UiGithubUserState>(
    itemsState,
    storeListTotalCache
) {

    override fun updateAdapterByDefault(adapter: GithubAdapter<UiGithubUserState>?) {
        adapter?.update(UiGithubUserState.Empty.wrap())
    }

}