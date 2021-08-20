package com.zinoview.githubrepositories.ui.users

import androidx.annotation.LayoutRes
import com.zinoview.githubrepositories.R
import com.zinoview.githubrepositories.core.Abstract


/**
 * @author Zinoview on 19.08.2021
 * k.gig@list.ru
 */
class GithubUserItemViewTypeFactory : Abstract.Factory<UiGithubUserState, Int> {

    override fun fetch(src: UiGithubUserState): Int = when(src) {
        is UiGithubUserState.Progress -> 1
        is UiGithubUserState.Base -> 2
        else -> 3
    }
}