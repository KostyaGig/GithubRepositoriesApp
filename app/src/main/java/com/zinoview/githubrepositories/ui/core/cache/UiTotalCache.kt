package com.zinoview.githubrepositories.ui.core.cache

import com.zinoview.githubrepositories.ui.core.CommunicationModel
import com.zinoview.githubrepositories.ui.core.ItemsState
import com.zinoview.githubrepositories.ui.core.adapter.GithubAdapter
import com.zinoview.githubrepositories.ui.core.message
import com.zinoview.githubrepositories.ui.repositories.UiGithubRepositoryState
import com.zinoview.githubrepositories.ui.users.CollapseOrExpandState


/**
 * @author Zinoview on 24.08.2021
 * k.gig@list.ru
 */

interface UiTotalCache<T : CommunicationModel> {

    fun add(items: List<T>)

    fun add(item: T)

    fun addAdapter(adapter: GithubAdapter<T>)

    fun updateAdapter()

    fun updateCurrentItemsState(state: CollapseOrExpandState)

    abstract class BaseUiGithubTotalCache<T : CommunicationModel>(
        private val itemsState: ItemsState,
        private val storeListTotalCache: StoreListTotalCache<T>
    ) : UiTotalCache<T> {

        private var adapter: GithubAdapter<T>? = null

        abstract fun updateAdapterByDefault(adapter: GithubAdapter<T>?)

        override fun add(items: List<T>) = storeListTotalCache.updateLists(items)

        //todo make update adapter by removed item
        override fun add(item: T) = storeListTotalCache.updateLists(item)

        override fun addAdapter(adapter: GithubAdapter<T>) {
            this.adapter = adapter
        }

        override fun updateAdapter() {
            if (isEmptyTotalCache()) {
                updateAdapterByDefault(adapter)
            } else {
                storeListTotalCache.updateAdapterByState(adapter,itemsState.currentState())
            }
        }

        override fun updateCurrentItemsState(state: CollapseOrExpandState)
            = itemsState.updateState(state)

        private fun repositoryStateEmpty() : List<UiGithubRepositoryState.Empty>
            = listOf(UiGithubRepositoryState.Empty)

        private fun userStateEmpty() : List<UiGithubRepositoryState.Empty>
            = listOf(UiGithubRepositoryState.Empty)

        private fun isEmptyTotalCache()
            = storeListTotalCache.isEmptyTotalCache(itemsState.currentState())
    }
}