package com.zinoview.githubrepositories.ui.core.cache.list

import android.text.TextUtils.indexOf
import com.zinoview.githubrepositories.ui.core.CommunicationModel

interface ListOperation<T : CommunicationModel.ItemCommunicationModel> {

    fun addUniqueItems(src: MutableList<T>, newItems: List<T>)

    fun removeItemByState(src: MutableList<T>,item: T)

    fun add(src: MutableList<T>,item: T)

    fun replace(src: MutableList<T>,item: T,wasReplaced: (T) -> Unit)

    class Base<T : CommunicationModel.ItemCommunicationModel>(
        private val containsElement: ContainsItem<T>,
        private val indexItem: IndexItem<T>
    ) : ListOperation<T> {

        override fun addUniqueItems(src: MutableList<T>, newItems: List<T>) {
            newItems.forEach { item ->
                if (containsElement.notContains(src,newItems)) {
                    src.add(item)
                } else {
                    val indexByItem = indexItem.indexByItem(src,item)
                    src.replace(indexByItem,item)
                }
            }
        }

        private fun <T : CommunicationModel.ItemCommunicationModel> MutableList<T>.replace(index: Int,item: T) {
            this[index] = item
        }

        override fun removeItemByState(src: MutableList<T>, item: T) {
            val indexByItemState = indexItem.indexByItemState(src, item)
            src.removeAt(indexByItemState)
        }

        override fun add(src: MutableList<T>,item: T) {
            src.add(item)
        }

        override fun replace(src: MutableList<T>, item: T,wasReplaced: (T) -> Unit) {
            src.forEachIndexed { index, element ->
                if (containsElement.contains(item,element)) {
                    src[index] = item
                    wasReplaced(item)
                }
            }
        }

    }
}