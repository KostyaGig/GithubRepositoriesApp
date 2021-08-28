package com.zinoview.githubrepositories.ui.users

import com.zinoview.githubrepositories.core.Abstract


/**
 * @author Zinoview on 21.08.2021
 * k.gig@list.ru
 */
class UiGithubUserStateMapper : Abstract.UserMapper<UiGithubUserState> {

    override fun map(name: String, bio: String, imageUrl: String,isCollapsed: Boolean): UiGithubUserState
        = UiGithubUserState.Base(
            UiGithubUser(name,bio,imageUrl,isCollapsed),
            isCollapsed)
}