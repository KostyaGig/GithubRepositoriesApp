package com.zinoview.githubrepositories.ui.core

import com.zinoview.githubrepositories.ui.repositories.UiGithubRepositoryState


/**
 * @author Zinoview on 24.08.2021
 * k.gig@list.ru
 */

interface UiTotalCache<T : CommunicationModel> {

    fun add(items: List<T>)

    fun add(item: T)

    fun saveTotalCache(viewModel: BaseViewModel<T>)

    fun addAdapter(adapter: GithubAdapter<T>)

    fun updateAdapter()

    abstract class UiGithubTotalCache2<T : CommunicationModel> (
        private val listTotalCache: MutableList<T>,
        private val replaceItem: (T) -> Unit
    ) : UiTotalCache<T> {

        private var adapter: GithubAdapter<T>? = null

        abstract fun updateAdapterByDefault(adapter: GithubAdapter<T>?)

        override fun add(items: List<T>) {
            //фильтруем до Base
            val communicationModelsBase = items.onlyBase()

            //Если наш текущий кеш содержит в себе элементы пришдшего - не добавляем,чтобы избежать одинкавых обЪектов

            if (!listTotalCache.containsAll(communicationModelsBase)) {
                message("Not contains")
                listTotalCache.addAll(communicationModelsBase)
            }

            message("AFTER ADD itemS size list ${listTotalCache.size}")
        }

        override fun add(item: T) {
            listTotalCache.forEachIndexed {index, element ->
                if (element.matches(item)) {
                    listTotalCache[index] = item
                    message("add by matches")
                    //if element was replaced call lambda
                    replaceItem.invoke(item)
                }
            }
        }

        override fun addAdapter(adapter: GithubAdapter<T>) {
            this.adapter = adapter
        }

        override fun updateAdapter() {
            if (emptyTotalCache()) {
                updateAdapterByDefault(adapter)
            } else {
                adapter?.update(listTotalCache)
            }
        }

        override fun saveTotalCache(viewModel: BaseViewModel<T>)
            = viewModel.saveData(listTotalCache)

        private fun repositoryStateEmpty() : List<UiGithubRepositoryState.Empty>
            = listOf(UiGithubRepositoryState.Empty)

        private fun userStateEmpty() : List<UiGithubRepositoryState.Empty>
            = listOf(UiGithubRepositoryState.Empty)

        private fun emptyTotalCache()
            = listTotalCache.onlyBase().isEmpty()

        private fun <T : CommunicationModel> List<T>.onlyBase() : List<T> {
            val totalListBase = mutableListOf<T>()
            this.map {
                if (it.isBase()) {
                    totalListBase.add(it)
                }
            }
            return totalListBase
        }
    }
}