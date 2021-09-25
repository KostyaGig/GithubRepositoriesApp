package com.zinoview.githubrepositories.ui.core.cache.list

import com.zinoview.githubrepositories.ui.core.CommunicationModel


interface ContainsItem<T> {

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

    class Test : ContainsItem<String> {

        override fun notContains(src: List<String>, newItems: List<String>): Boolean {
            return src.containsAll(newItems).not()
        }

        override fun notContains(src: List<String>, item: String): Boolean {
            return src.contains(item).not()
        }

        override fun contains(item1: String, item2: String): Boolean {
            return item1 == item2
        }

    }
}