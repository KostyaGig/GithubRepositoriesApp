package com.zinoview.githubrepositories.ui.core.cache

import com.zinoview.githubrepositories.ui.core.CommunicationModel
import com.zinoview.githubrepositories.ui.core.adapter.GithubAdapter
import com.zinoview.githubrepositories.ui.core.message
import com.zinoview.githubrepositories.ui.users.CollapseOrExpandState


/**
 * @author Zinoview on 30.08.2021
 * k.gig@list.ru
 */

interface StoreListTotalCache<T : CommunicationModel>  {

    fun updateLists(items: List<T>)

    fun updateLists(item: T)

    fun updateAdapterByState(adapter: GithubAdapter<T>?, state: CollapseOrExpandState)

    fun isEmptyTotalCache(state: CollapseOrExpandState) : Boolean

    class Base<T : CommunicationModel>(
        private val listAnyStateTotalCache: MutableList<T>,
        private val listCollapsedTotalCache: MutableList<T>,
        private val listExpandedTotalCache: MutableList<T>,
        private val replaceItem: (T) -> Unit
    ) : StoreListTotalCache<T>, ListTotalCacheFunctions<T>() {

        override fun updateLists(items: List<T>) {
            items.forEach {
                message("class ${it::class.java}")
            }
            message("SIze items ${items.size}")
            val baseItems = items.filter { item -> item.isBase() }
            message("after filter by base ${baseItems.size}")
            baseItems.forEach {
                message("class base items ${it::class.java}")
            }

            //Если наш текущий кеш содержит в себе элементы пришдшего - не добавляем,чтобы избежать одинкавых обЪектов
            if (!listAnyStateTotalCache.containsAll(baseItems)) {
                listAnyStateTotalCache.addAll(baseItems)
            }

            val onlyCollapsedModels = baseItems.filter { it.isCollapsed() }
            listCollapsedTotalCache.uniqueItems(onlyCollapsedModels)

            val onlyExpandedModels = baseItems.filterNot { it.isCollapsed() }
            listExpandedTotalCache.uniqueItems(onlyExpandedModels)

            message("Update lists by items size cachedlists listAnyTotalCache ${listAnyStateTotalCache.size} listCollapsedTotalCache ${listCollapsedTotalCache.size} listExpandedTotalCache ${listExpandedTotalCache.size} ")
        }

        override fun updateLists(item: T) {

            if (item.isCollapsed()) {
                listExpandedTotalCache.updateListByCollapsed(item)
                listCollapsedTotalCache.add(item)
            } else {
                listCollapsedTotalCache.updateListByCollapsed(item)
                listExpandedTotalCache.add(item)
            }

            listAnyStateTotalCache.forEachIndexed { index, element ->
                if (element.matches(item)) {
                    listAnyStateTotalCache[index] = item
                    //if element was replaced: call lambda
                    message("was replaced")
                    replaceItem.invoke(item)
                }
            }

            message("Update lists by ITEM size cachedlists listAnyTotalCache ${listAnyStateTotalCache.size} listCollapsedTotalCache ${listCollapsedTotalCache.size} listExpandedTotalCache ${listExpandedTotalCache.size} ")
        }

        override fun updateAdapterByState(adapter: GithubAdapter<T>?, state: CollapseOrExpandState) {
            adapter?.update(listTotalCacheByState(state))
        }

        private fun listTotalCacheByState(state: CollapseOrExpandState): List<T> = when (state) {
            is CollapseOrExpandState.Expanded -> listExpandedTotalCache
            is CollapseOrExpandState.Collapsed -> listCollapsedTotalCache
            else -> listAnyStateTotalCache
        }

        override fun isEmptyTotalCache(state: CollapseOrExpandState) = when (state) {
            is CollapseOrExpandState.Expanded -> listExpandedTotalCache.isEmpty()
            is CollapseOrExpandState.Collapsed -> listCollapsedTotalCache.isEmpty()
            else -> listAnyStateTotalCache.isEmpty()
        }
    }
}
