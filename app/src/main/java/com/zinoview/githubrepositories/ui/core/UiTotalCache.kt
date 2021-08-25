package com.zinoview.githubrepositories.ui.core

import com.zinoview.githubrepositories.ui.repositories.UiGithubRepositoryState


/**
 * @author Zinoview on 24.08.2021
 * k.gig@list.ru
 */

interface UiTotalCache<T : CommunicationModel> {

    fun add(items: List<T>)

    fun addAdapter(adapter: GithubAdapter<T>)

    fun updateAdapter()

    abstract class UiGithubTotalCache<T : CommunicationModel>(
        private val listTotalCache: MutableList<T>,
    ) : UiTotalCache<T> {

        private var adapter: GithubAdapter<T>? = null

        abstract fun updateAdapterByDefault(adapter: GithubAdapter<T>?)

        override fun add(items: List<T>) {
            //фильтруем до Base
            val communicationModelBase = items.onlyBase()

            //Если наш текущий кеш содержит в себе элементы пришдшего - не добавляем,чтобы избежать одинкавых обЪектов
            if (!listTotalCache.containsAll(communicationModelBase)) {
                listTotalCache.addAll(communicationModelBase)
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