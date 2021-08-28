package com.zinoview.githubrepositories.ui.users.cache

import com.zinoview.githubrepositories.ui.core.UiTotalCache
import com.zinoview.githubrepositories.ui.core.GithubAdapter
import com.zinoview.githubrepositories.ui.repositories.UiGithubRepositoryState
import com.zinoview.githubrepositories.ui.users.UiGithubUserState


/**
 * @author Zinoview on 24.08.2021
 * k.gig@list.ru
 */

class UsersTotalCache(
    list: MutableList<UiGithubUserState>,
    replaceItem: (UiGithubUserState) -> Unit
) : UiTotalCache.UiGithubTotalCache2<UiGithubUserState>(list,replaceItem) {

    override fun updateAdapterByDefault(adapter: GithubAdapter<UiGithubUserState>?) {
        adapter?.update(listOf(UiGithubUserState.Empty))
    }

}