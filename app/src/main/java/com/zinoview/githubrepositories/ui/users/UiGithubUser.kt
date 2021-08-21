package com.zinoview.githubrepositories.ui.users

import com.zinoview.githubrepositories.core.Abstract
import com.zinoview.githubrepositories.ui.core.AbstractView


/**
 * @author Zinoview on 19.08.2021
 * k.gig@list.ru
 */
data class UiGithubUser(
    private val name: String,
    private val bio: String,
    private val profileImageUrl: String
) : Abstract.Object.Ui<UiGithubUserState>,
    Abstract.FactoryMapper<List<AbstractView>,Unit> {

    override fun map(mapper: Abstract.UserMapper<UiGithubUserState>): UiGithubUserState
        = mapper.map(name,bio,profileImageUrl)

    override fun map(src: List<AbstractView>)
        = src.forEach { view -> view.map(name,bio,profileImageUrl) }

}