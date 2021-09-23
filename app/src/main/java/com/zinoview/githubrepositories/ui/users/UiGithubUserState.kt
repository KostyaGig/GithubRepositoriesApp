package com.zinoview.githubrepositories.ui.users

import com.zinoview.githubrepositories.core.Abstract
import com.zinoview.githubrepositories.core.Same
import com.zinoview.githubrepositories.data.users.cache.CacheGithubUser
import com.zinoview.githubrepositories.ui.core.*
import com.zinoview.githubrepositories.ui.core.adapter.AbstractListener
import com.zinoview.githubrepositories.ui.core.adapter.CollapseOrExpandStateListener
import com.zinoview.githubrepositories.ui.core.adapter.GithubOnItemClickListener
import com.zinoview.githubrepositories.ui.core.view.AbstractView
import com.zinoview.githubrepositories.ui.core.view.CollapseView


/**
 * @author Zinoview on 19.08.2021
 * k.gig@list.ru
 */
sealed class UiGithubUserState :
    Abstract.Object<CacheGithubUser,CacheGithubUserMapper>,
    ListWrapper<UiGithubUserState>,
    Abstract.FactoryMapper<List<AbstractView>,Unit>,
    CommunicationModel.ItemCommunicationModel,
    AbstractListener.CollapseOrExpandListener<
            GithubOnItemClickListener<String>,
            CollapseOrExpandStateListener<UiGithubUserState>
            >,
    Same<UiGithubUser> {

    override fun isBase(): Boolean = false

    override fun wrap(): List<UiGithubUserState>
        = listOf(Default)

    override fun map(src: List<AbstractView>) = Unit

    override fun map(mapper: CacheGithubUserMapper): CacheGithubUser
        = mapper.map(
        true,
        UiGithubUser.Mock()
    )

    open fun mapCollapseOrExpandState(src: List<CollapseView>) = Unit

    override fun notifyAboutItemClick(listener: GithubOnItemClickListener<String>)
        = Unit

    override fun notifyAboutCollapseOrExpand(
        listener: CollapseOrExpandStateListener<UiGithubUserState>,
        position: Int
    ) = Unit

    override fun isCollapsed(): Boolean = true

    override fun matches(model: CommunicationModel.ItemCommunicationModel): Boolean = false

    override fun same(element: UiGithubUser): Boolean = false

    open fun sameCollapsed(isCollapsed: Boolean) : Boolean = false

    object Default : UiGithubUserState()

    object Progress : UiGithubUserState() {

        override fun wrap(): List<UiGithubUserState>
            = listOf(this)
    }

    data class Base(
         private val uiGithubUser: UiGithubUser,
         private val isCollapsedState: Boolean
    ) : UiGithubUserState() {

        override fun isBase(): Boolean = true

        override fun wrap(): List<UiGithubUserState> = listOf(this)

        override fun map(mapper: CacheGithubUserMapper): CacheGithubUser
            = mapper.map(isCollapsedState,uiGithubUser)

        override fun map(src: List<AbstractView>) = uiGithubUser.map(src)

        override fun mapCollapseOrExpandState(src: List<CollapseView>) {
            src.map {
                it.map(isCollapsedState)
            }
        }

        override fun notifyAboutItemClick(listener: GithubOnItemClickListener<String>)
            = uiGithubUser.notifyAboutItemClick(listener)

        override fun notifyAboutCollapseOrExpand(
            listener: CollapseOrExpandStateListener<UiGithubUserState>,
            position: Int
        ) = listener.onChangeCollapseState(newState(), position)

        override fun isCollapsed(): Boolean = isCollapsedState

        private fun newState(): Base = Base(uiGithubUser, isCollapsedState.not())

        override fun matches(model: CommunicationModel.ItemCommunicationModel): Boolean =
            if (model is UiGithubUserState)
                model.same(uiGithubUser)
             else
                false

        override fun same(element: UiGithubUser): Boolean {
            return uiGithubUser.same(element)
        }

        override fun sameCollapsed(isCollapsed: Boolean): Boolean
            = this.isCollapsedState == isCollapsed


        override fun hashCode(): Int {
            return super.hashCode()
        }

        override fun equals(other: Any?): Boolean {
            val oth = other as Base
            val result = oth.same(uiGithubUser) && oth.sameCollapsed(isCollapsedState)
            message("result equals $result")
            return result
        }
    }

    //if our local data empty
    object Empty : UiGithubUserState() {

        override fun map(src: List<AbstractView>)
            = src.forEach { view -> view.map("Empty data", "", "",true) }

        override fun wrap(): List<UiGithubUserState>
            = listOf(this)
    }

    class Fail(private val message: String) : UiGithubUserState() {

        override fun map(src: List<AbstractView>) =
            src.forEach { view-> view.map(message,"","",true) }

        override fun wrap(): List<UiGithubUserState>
            = listOf(this)
    }
}
