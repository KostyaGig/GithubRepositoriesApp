package com.zinoview.githubrepositories.ui.core


/**
 * @author Zinoview on 31.08.2021
 * k.gig@list.ru
 */
interface CommunicationMatcher<T : CommunicationModel.ItemCommunicationModel> {

    fun matches(model: T) : Boolean
}