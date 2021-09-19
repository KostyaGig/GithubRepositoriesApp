package com.zinoview.githubrepositories.ui.core.cache

import com.zinoview.githubrepositories.ui.core.CommunicationModel


/**
 * @author Zinoview on 30.08.2021
 * k.gig@list.ru
 */
abstract class  ListTotalCacheFunctions<T : CommunicationModel.ItemCommunicationModel> {

    protected fun <T : CommunicationModel.ItemCommunicationModel> List<T>.isEmpty()
        = this.filter { it.isBase() }.isEmpty()

    protected fun <T : CommunicationModel.ItemCommunicationModel> MutableList<T>.uniqueItems(modelsByCollapsed: List<T>) {
        modelsByCollapsed.forEach { element ->
            if (this.contains(element).not()) {
                this.add(element)
            } else {
                val indexAlreadyContainsElement = indexOf(element)
                this[indexAlreadyContainsElement] = element
            }
        }
    }

    protected fun <T : CommunicationModel.ItemCommunicationModel> MutableList<T>.updateListByCollapsed(item: T) {
        val indexContainsElement = this.indexAlreadyContainsElement(item)
        this.removeAt(indexContainsElement)
    }

    private fun <T : CommunicationModel.ItemCommunicationModel> List<T>.indexAlreadyContainsElement(item: T) : Int {
        var index = 0
        this.forEachIndexed { i, e ->
            if (e.matches(item)) {
                index = i
            }
        }
        return index
    }

    protected fun <T : CommunicationModel.ItemCommunicationModel> MutableList<T>.replaceExistItem(item: T) {
        this.forEachIndexed { index, element ->
            if (element.matches(item)) {
                if (item.isCollapsed()) {
                    this[index] = item
                } else {
                    this[index] = item
                }
            }
        }
    }

}