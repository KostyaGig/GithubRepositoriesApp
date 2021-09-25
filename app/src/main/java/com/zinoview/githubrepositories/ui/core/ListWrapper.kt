package com.zinoview.githubrepositories.ui.core


/**
 * @author Zinoview on 21.08.2021
 * k.gig@list.ru
 */

interface ListWrapper<T> {

    fun wrap() : List<T>

    class TestModel : ListWrapper<TestModel> {

        override fun wrap(): List<TestModel>
            = listOf(this)
    }
}