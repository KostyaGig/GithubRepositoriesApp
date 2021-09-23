package com.zinoview.githubrepositories.ui.core.cache.list

import androidx.room.Index
import com.zinoview.githubrepositories.ui.core.CommunicationModel

interface IndexItem<T : CommunicationModel.ItemCommunicationModel> {

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
}