package com.zinoview.githubrepositories.ui.core.cache.list

import com.zinoview.githubrepositories.ui.core.CommunicationModel



interface Filter<T> {

    fun filter(src: List<T>) : List<T>

    class BaseElements<T : CommunicationModel.ItemCommunicationModel> : Filter<T> {

        override fun filter(src: List<T>): List<T> {
            return src.filter { it.isBase() }
        }
    }

    class CollapsedElements<T : CommunicationModel.ItemCommunicationModel> : Filter<T> {

        override fun filter(src: List<T>): List<T> {
            return src.filter { it.isCollapsed() }
        }
    }

    class ExpandedElements<T : CommunicationModel.ItemCommunicationModel> : Filter<T> {

        override fun filter(src: List<T>): List<T> {
            return src.filter { it.isCollapsed().not() }
        }
    }

    class TestLengthStringFilter : Filter<String> {

        override fun filter(src: List<String>): List<String> {
            return src.filter {
                it.length > MIN_LENGTH_STRING
            }
        }

        private companion object {
            const val MIN_LENGTH_STRING = 5
        }
    }
}