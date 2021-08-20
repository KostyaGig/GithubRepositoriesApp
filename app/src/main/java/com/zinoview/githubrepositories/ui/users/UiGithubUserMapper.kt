package com.zinoview.githubrepositories.ui.users

import com.zinoview.githubrepositories.core.Abstract


/**
 * @author Zinoview on 19.08.2021
 * k.gig@list.ru
 */
class UiGithubUserMapper : Abstract.UserMapper<UiGithubUser> {

    override fun map(name: String, bio: String, imageUrl: String): UiGithubUser
        = UiGithubUser(name,bio,imageUrl)

}