package com.zinoview.githubrepositories.ui.core.cache.list

import com.zinoview.githubrepositories.ui.core.CommunicationModel
import java.lang.IllegalStateException


interface IndexItem<T> {

    fun indexByItem(src: List<T>, item: T) : Int

    fun indexByItemState(src: List<T>, item: T) : Int

    class Base<T : CommunicationModel.ItemCommunicationModel> : IndexItem<T> {
        override fun indexByItem(src: List<T>, item: T): Int
            = src.indexOf(item)

        override fun indexByItemState(src: List<T>, item: T): Int {
            var index = 0
            src.forEachIndexed { i, element ->
                if (element.matches(item)) {
                    index = i
                }
            }
            return index
        }
    }

    class Test : IndexItem<String> {

        override fun indexByItem(src: List<String>, item: String): Int {
            return src.indexOf(item)
        }

        override fun indexByItemState(src: List<String>, item: String)
            = throw IllegalStateException("IndexItem.Test not use indexByItemState()")


    }
}