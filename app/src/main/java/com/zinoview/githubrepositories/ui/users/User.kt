package com.zinoview.githubrepositories.ui.users

import com.zinoview.githubrepositories.ui.core.GithubAdapter
import com.zinoview.githubrepositories.ui.core.UiTotalCache


/**
 * @author Zinoview on 24.08.2021
 * k.gig@list.ru
 */

class User(
    list: MutableList<UiGithubUserState>
) : UiTotalCache.UiGithubTotalCache<UiGithubUserState>(list) {

    override fun updateAdapterByDefault(adapter: GithubAdapter<UiGithubUserState>?) {
        adapter?.update(UiGithubUserState.Empty.wrap())
    }
}