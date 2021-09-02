package com.zinoview.githubrepositories.ui.core.adapter

import com.zinoview.githubrepositories.ui.core.CommunicationModel


/**
 * @author Zinoview on 31.08.2021
 * k.gig@list.ru
 */
interface CollapseOrExpandStateListener<T : CommunicationModel> {

    fun onChangeCollapseState(item: T,position: Int)
}