package com.zinoview.githubrepositories.ui.users

import com.zinoview.githubrepositories.core.Abstract


/**
 * @author Zinoview on 19.08.2021
 * k.gig@list.ru
 */
class GithubUserItemViewTypeFactory : Abstract.FactoryMapper<UiGithubUserState, Int> {

    override fun map(src: UiGithubUserState): Int = when(src) {
        is UiGithubUserState.Progress -> 1
        is UiGithubUserState.Base -> 2
        is UiGithubUserState.Empty -> 3
        else -> 4
    }
}