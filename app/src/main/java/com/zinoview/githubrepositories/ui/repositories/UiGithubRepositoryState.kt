package com.zinoview.githubrepositories.ui.repositories

import com.zinoview.githubrepositories.core.Abstract
import com.zinoview.githubrepositories.core.Same
import com.zinoview.githubrepositories.data.repositories.cache.CacheGithubRepository
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
sealed class UiGithubRepositoryState :
    Abstract.Object<CacheGithubRepository,CacheGithubRepositoryMapper>,
    ListWrapper<UiGithubRepositoryState>,
    Abstract.FactoryMapper<List<AbstractView>,Unit>,
    CommunicationModel,
    AbstractListener.CollapseOrExpandListener<
            GithubOnItemClickListener<Pair<String,String>>, CollapseOrExpandStateListener<UiGithubRepositoryState>
            >,
    Same<UiGithubRepository>,
    ModelState<UiGithubRepositoryState> {

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

    override fun notifyAboutItemClick(listener: GithubOnItemClickListener<Pair<String,String>>)
        = Unit

    override fun notifyAboutCollapseOrExpand(
        listener: CollapseOrExpandStateListener<UiGithubRepositoryState>,
        position: Int
    ) = Unit

    override fun isCollapsed(): Boolean = true

    override fun modelWithChangedState(): UiGithubRepositoryState
        = Default

    open fun mapCollapseOrExpandState(src: List<CollapseView>) = Unit

    open fun sameCollapsed(isCollapsed: Boolean) : Boolean = false

    object Default : UiGithubRepositoryState()

    object Progress : UiGithubRepositoryState() {

        override fun wrap(): List<UiGithubRepositoryState>
            = listOf(this)
    }

    data class Base(
        private val uiGithubRepository: UiGithubRepository,
        private val isCollapsedState: Boolean
    ) : UiGithubRepositoryState() {

        override fun isBase(): Boolean = true

        override fun wrap(): List<UiGithubRepositoryState>
            = listOf(this)

        override fun map(src: List<AbstractView>)
            = uiGithubRepository.map(src)

        override fun mapCollapseOrExpandState(src: List<CollapseView>) {
            src.forEach {
                it.map(isCollapsedState)
            }
        }

        override fun map(mapper: CacheGithubRepositoryMapper): CacheGithubRepository
            = mapper.map(isCollapsedState,uiGithubRepository)

        override fun notifyAboutItemClick(listener: GithubOnItemClickListener<Pair<String,String>>)
            = uiGithubRepository.notifyAboutItemClick(listener)

        override fun notifyAboutCollapseOrExpand(
            listener: CollapseOrExpandStateListener<UiGithubRepositoryState>,
            position: Int
        ) = listener.onChangeCollapseState(modelWithChangedState(),position)

        override fun isCollapsed(): Boolean = isCollapsedState

        override fun modelWithChangedState(): UiGithubRepositoryState {
            val uiGithubRepositoryWithNewState = uiGithubRepository.modelWithChangedState()
            return Base(uiGithubRepositoryWithNewState, isCollapsedState.not())
        }

        override fun matches(model: CommunicationModel): Boolean  =
            if (model is UiGithubRepositoryState)
                model.same(uiGithubRepository)
             else
                false

        override fun same(element: UiGithubRepository): Boolean
            = uiGithubRepository.same(element)

        override fun sameCollapsed(isCollapsed: Boolean): Boolean
            = this.isCollapsedState == isCollapsed

        override fun hashCode(): Int {
            return super.hashCode()
        }

        override fun equals(other: Any?): Boolean {
            val oth = other as Base
            return oth.same(uiGithubRepository) && oth.sameCollapsed(isCollapsedState)
        }
    }

    object Empty : UiGithubRepositoryState() {

        override fun map(src: List<AbstractView>)
            = src.forEach { it.map("EmptyData","","",true) }

        override fun wrap(): List<UiGithubRepositoryState>
            = listOf(this)
    }

    class Fail(private val message: String) : UiGithubRepositoryState() {

        override fun wrap(): List<UiGithubRepositoryState>
            = listOf(this)

        override fun map(src: List<AbstractView>)
            = src.first().map(message,"","",true)
    }

}

