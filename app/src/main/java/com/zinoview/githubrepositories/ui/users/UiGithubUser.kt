package com.zinoview.githubrepositories.ui.users

import com.zinoview.githubrepositories.core.Abstract
import com.zinoview.githubrepositories.core.Same
import com.zinoview.githubrepositories.data.users.cache.CacheGithubUser
import com.zinoview.githubrepositories.ui.core.view.AbstractView
import com.zinoview.githubrepositories.ui.core.adapter.AbstractListener
import com.zinoview.githubrepositories.ui.core.adapter.GithubOnItemClickListener


/**
 * @author Zinoview on 19.08.2021
 * k.gig@list.ru
 */

open class UiGithubUser(
    private val name: String,
    private val bio: String,
    private val profileImageUrl: String,
    private val isCollapsed: Boolean
) : Abstract.Object.Ui.GithubUser<UiGithubUserState>,
    Abstract.FactoryMapper<List<AbstractView>,Unit>,
    AbstractListener<GithubOnItemClickListener>,
    Abstract.UniqueMapper<CacheGithubUser,Boolean>,
    Same<UiGithubUser> {

    class Mock : UiGithubUser(
        "",
        "",
        "",
        true
    )

    override fun map(mapper: Abstract.UserMapper<UiGithubUserState>): UiGithubUserState
        = mapper.map(name,bio,profileImageUrl,isCollapsed)

    override fun map(src: List<AbstractView>)
        = src.forEach { view -> view.map(name,bio,profileImageUrl,isCollapsed) }

    override fun mapTo(isCollapsed: Boolean) : CacheGithubUser
        = CacheGithubUser(name, bio, profileImageUrl, isCollapsed)

    override fun notifyAboutItemClick(listener: GithubOnItemClickListener)
        = listener.onItemClick(name)

    override fun same(element: UiGithubUser) : Boolean
        = element.same(name, bio)

    private fun same(name: String,bio: String)
        = this.name == name && this.bio == bio

}