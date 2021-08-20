package com.zinoview.githubrepositories.domain.users

import com.zinoview.githubrepositories.core.Abstract
import com.zinoview.githubrepositories.ui.users.UiGithubUser


/**
 * @author Zinoview on 19.08.2021
 * k.gig@list.ru
 */
data class DomainGithubUser(
    private val name: String,
    private val bio: String,
    private val profileImageUrl: String
) : Abstract.Object.Ui {

    override fun map(mapper: Abstract.UserMapper<UiGithubUser>): UiGithubUser
        = mapper.map(name,bio,profileImageUrl)
}