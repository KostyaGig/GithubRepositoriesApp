package com.zinoview.githubrepositories.ui.core

import com.zinoview.githubrepositories.data.core.prefs.CachedState
import com.zinoview.githubrepositories.ui.users.CollapseOrExpandState


/**
 * @author Zinoview on 30.08.2021
 * k.gig@list.ru
 */
interface ItemsState {

    fun updateState(state: CollapseOrExpandState)

    fun currentState() : CollapseOrExpandState

    class Base(
        cachedState: CachedState,
        private var currentItemsState: CollapseOrExpandState = CollapseOrExpandState.Empty
    ) : ItemsState {

        init {
            currentItemsState = cachedState.currentState()
        }

        override fun updateState(state: CollapseOrExpandState) {
            currentItemsState = state
        }

        override fun currentState() = currentItemsState

    }
}