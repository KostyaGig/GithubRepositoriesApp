package com.zinoview.githubrepositories.core

import com.zinoview.githubrepositories.ui.users.CollapseOrExpandState


/**
 * @author Zinoview on 29.08.2021
 * k.gig@list.ru
 */
interface SaveState {

    fun saveState(state: CollapseOrExpandState)
}