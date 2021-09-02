package com.zinoview.githubrepositories.data.core.prefs

import com.zinoview.githubrepositories.core.Save
import com.zinoview.githubrepositories.core.SaveState
import com.zinoview.githubrepositories.ui.users.CollapseOrExpandState


/**
 * @author Zinoview on 29.08.2021
 * k.gig@list.ru
 */
interface CachedState : SaveState {

    fun currentState() : CollapseOrExpandState
}