package com.zinoview.githubrepositories.ui.users

import com.zinoview.githubrepositories.core.Abstract
import com.zinoview.githubrepositories.ui.view.AbstractView


/**
 * @author Zinoview on 19.08.2021
 * k.gig@list.ru
 */
data class UiGithubUser(
    private val name: String,
    private val bio: String,
    private val profileImageUrl: String
)  {

    fun map(mapper: List<AbstractView>)
        = mapper.forEach { view -> view.map(name,bio,profileImageUrl) }
}