package com.zinoview.githubrepositories.ui.core


/**
 * @author Zinoview on 23.08.2021
 * k.gig@list.ru
 */

interface CommunicationModel {

    interface ItemCommunicationModel : CommunicationModel, Matcher.CommunicationMatcher<ItemCommunicationModel> {
        fun isBase() : Boolean

        fun isCollapsed() : Boolean
    }
}

