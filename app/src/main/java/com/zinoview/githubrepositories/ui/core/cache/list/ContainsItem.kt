package com.zinoview.githubrepositories.ui.core.cache.list

import com.zinoview.githubrepositories.ui.core.CommunicationModel

interface ContainsItem<T : CommunicationModel.ItemCommunicationModel> {

    fun notContains(src: List<T>,newItems: List<T>) : Boolean

    fun notContains(src: List<T>,item: T) : Boolean

    fun contains(item1: T,item2: T) : Boolean

    class Base<T : CommunicationModel.ItemCommunicationModel> : ContainsItem<T> {

        override fun notContains(src: List<T>, newItems: List<T>): Boolean {
            return src.containsAll(newItems).not()
        }

        override fun notContains(src: List<T>,item: T) : Boolean {
            return src.contains(item).not()
        }

        override fun contains(item1: T,item2: T) : Boolean {
            return item1.matches(item2)
        }
    }
}