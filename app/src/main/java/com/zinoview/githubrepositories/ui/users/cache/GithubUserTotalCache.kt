package com.zinoview.githubrepositories.ui.users.cache

import com.zinoview.githubrepositories.ui.message
import com.zinoview.githubrepositories.ui.users.GithubUserAdapter
import com.zinoview.githubrepositories.ui.users.UiGithubUserState
import java.lang.IllegalStateException


/**
 * @author Zinoview on 20.08.2021
 * k.gig@list.ru
 */
interface GithubUserTotalCache<T> {

    fun add(items: List<T>)

    fun addAdapter(adapter: GithubUserAdapter)

    fun updateAdapter()

    class Base(
        private val uiGithubUserStates: MutableList<UiGithubUserState>
    ) : GithubUserTotalCache<UiGithubUserState> {

        private var cachedAdapter: GithubUserAdapter? = null

        override fun add(items: List<UiGithubUserState>) {
            //фильтруем до Base
            val uiGithubUserBaseState = items.onlyBase()

            //Если наш текущий кеш содержит в себе элементы пришдшего - не добавляем,чтобы избежать одинкавых обЪектов
            if (!uiGithubUserStates.containsAll(uiGithubUserBaseState)) {
                uiGithubUserStates.addAll(uiGithubUserBaseState)
            }
        }

        override fun updateAdapter() {
            if (emptyTotalCache()) {
                updateAdapterByDefault()
            } else {
                cachedAdapter?.update(uiGithubUserStates)
            }
        }

        override fun addAdapter(adapter: GithubUserAdapter) {
            cachedAdapter = adapter
        }

        private fun updateAdapterByDefault()
            = cachedAdapter?.update(listOf(UiGithubUserState.Empty))

        private fun emptyTotalCache()
            = uiGithubUserStates.onlyBase().isEmpty()

        private fun List<UiGithubUserState>.onlyBase()
            = this.filterIsInstance<UiGithubUserState.Base>()

    }

    class Test(private val list: MutableList<String>) : GithubUserTotalCache<String> {

        override fun add(items: List<String>) {
            list.addAll(items)
        }

        fun actual() = list

        override fun updateAdapter()
            = throw IllegalStateException("Not use for test implements")

        override fun addAdapter(adapter: GithubUserAdapter)
            = throw IllegalStateException("Not use for test implements")

    }
}