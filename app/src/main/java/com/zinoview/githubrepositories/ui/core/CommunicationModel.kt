package com.zinoview.githubrepositories.ui.core



/**
 * @author Zinoview on 23.08.2021
 * k.gig@list.ru
 */

interface Matcher<T : CommunicationModel> {

    fun matches(model: T) : Boolean
}

interface CommunicationModel : Matcher<CommunicationModel> {

    fun isBase() : Boolean

}

