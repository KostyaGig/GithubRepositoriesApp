package com.zinoview.githubrepositories.ui.core.cache.list

import com.zinoview.githubrepositories.ui.core.CommunicationModel

interface StoreFilters<T : CommunicationModel.ItemCommunicationModel> {

    fun filterByBase(src: List<T>) : List<T>

    fun filterByCollapsed(src: List<T>) : List<T>

    fun filterByExpanded(src: List<T>) : List<T>

    class Base<T : CommunicationModel.ItemCommunicationModel>(
        private val baseFilter: Filter<T>,
        private val collapsedFilter: Filter<T>,
        private val expandedFilter: Filter<T>,
    ) : StoreFilters<T> {

        override fun filterByBase(src: List<T>): List<T> {
            return baseFilter.filter(src)
        }

        override fun filterByCollapsed(src: List<T>): List<T> {
            return collapsedFilter.filter(src)
        }

        override fun filterByExpanded(src: List<T>): List<T> {
            return expandedFilter.filter(src)
        }

    }
}