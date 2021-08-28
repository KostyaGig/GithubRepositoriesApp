package com.zinoview.githubrepositories.ui.repositories

import com.zinoview.githubrepositories.core.Abstract
import com.zinoview.githubrepositories.core.Same
import com.zinoview.githubrepositories.data.repositories.cache.CacheGithubRepository
import com.zinoview.githubrepositories.ui.core.*
import com.zinoview.githubrepositories.ui.users.UiGithubUserState
import java.lang.IllegalStateException


/**
 * @author Zinoview on 19.08.2021
 * k.gig@list.ru
 */
sealed class UiGithubRepositoryState :
    Abstract.Object<CacheGithubRepository,CacheGithubRepositoryMapper>,
    Wrapper<UiGithubRepositoryState>,
    Abstract.FactoryMapper<List<AbstractView>,Unit>,
    CommunicationModel,
    AbstractListener.CollapseOrExpandListener<
            GithubOnItemClickListener, CollapseOrExpandListener<UiGithubRepositoryState>
            >,
    Same<UiGithubRepository> {

    override fun wrap(): List<UiGithubRepositoryState>
        = listOf(Default)

    override fun isBase(): Boolean = false

    override fun map(src: List<AbstractView>) = Unit

    override fun map(mapper: CacheGithubRepositoryMapper): CacheGithubRepository
            = mapper.map(
        true,
        UiGithubRepository.Mock()
    )

    override fun matches(model: CommunicationModel): Boolean = false

    override fun same(element: UiGithubRepository): Boolean = false

    override fun notifyAboutItemClick(listener: GithubOnItemClickListener)
        = throw IllegalStateException("UiGithubRepoState notifyAboutItemClick not use")

    override fun notifyAboutCollapseOrExpand(
        listener: CollapseOrExpandListener<UiGithubRepositoryState>,
        position: Int
    ) = Unit

    open fun mapCollapseOrExpandState(src: List<CollapseView>) = Unit

    open fun sameCollapsed(isCollapsed: Boolean) : Boolean = false

    object Default : UiGithubRepositoryState()

    object Progress : UiGithubRepositoryState() {

        override fun wrap(): List<UiGithubRepositoryState>
            = listOf(this)
    }

    data class Base(
        private val uiGithubRepository: UiGithubRepository,
        private val isCollapsed: Boolean
    ) : UiGithubRepositoryState() {

        override fun isBase(): Boolean = true

        override fun wrap(): List<UiGithubRepositoryState>
            = listOf(this)

        override fun map(src: List<AbstractView>)
            = uiGithubRepository.map(src)

        override fun mapCollapseOrExpandState(src: List<CollapseView>) {
            src.forEach {
                it.map(isCollapsed)
            }
        }

        override fun map(mapper: CacheGithubRepositoryMapper): CacheGithubRepository
            = mapper.map(isCollapsed,uiGithubRepository)

        override fun notifyAboutCollapseOrExpand(
            listener: CollapseOrExpandListener<UiGithubRepositoryState>,
            position: Int
        ) = listener.onChangeCollapseState(newState(),position)

        private fun newState(): Base =
            Base(uiGithubRepository, isCollapsed.not())

        override fun matches(model: CommunicationModel): Boolean  =
            if (model is UiGithubRepositoryState)
                model.same(uiGithubRepository)
             else
                false

        override fun same(element: UiGithubRepository): Boolean
            = uiGithubRepository.same(element)

        override fun sameCollapsed(isCollapsed: Boolean): Boolean
            = this.isCollapsed == isCollapsed

        override fun hashCode(): Int {
            return super.hashCode()
        }

        override fun equals(other: Any?): Boolean {
            val oth = other as Base
            return oth.same(uiGithubRepository) && oth.sameCollapsed(isCollapsed)
        }
    }

    object Empty : UiGithubRepositoryState() {

        override fun map(src: List<AbstractView>)
                = src.forEach { it.map("EmptyData","","",true) }

        fun list() = listOf(this)
    }

    class Fail(private val message: String) : UiGithubRepositoryState() {

        override fun wrap(): List<UiGithubRepositoryState>
            = listOf(this)

        override fun map(src: List<AbstractView>)
            = src.first().map(message,"","",true)
    }

}

