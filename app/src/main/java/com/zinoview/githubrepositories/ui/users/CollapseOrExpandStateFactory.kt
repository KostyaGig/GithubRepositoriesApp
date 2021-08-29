package com.zinoview.githubrepositories.ui.users

import com.zinoview.githubrepositories.R
import com.zinoview.githubrepositories.core.Abstract
import java.lang.IllegalArgumentException

class CollapseOrExpandStateFactory : Abstract.FactoryMapper<Int,CollapseOrExpandState> {

    override fun map(src: Int): CollapseOrExpandState = when(src) {
        R.id.action_filter_by_collapse_state -> CollapseOrExpandState.Collapsed
        R.id.action_filter_by_expand_state -> CollapseOrExpandState.Expanded
        R.id.action_filter_any -> CollapseOrExpandState.Any
        else -> throw IllegalArgumentException("Item id $src not found")
    }
}