package com.zinoview.githubrepositories.ui.core


/**
 * @author Zinoview on 31.08.2021
 * k.gig@list.ru
 */

interface Matcher<T> {

    fun matches(model: T) : Boolean

    interface CommunicationMatcher<T : CommunicationModel.ItemCommunicationModel> : Matcher<T>


    class TestMatcherModel(private val data: String) : Matcher<String> {
        override fun matches(model: String): Boolean
            = data == model
    }
}


