package com.zinoview.githubrepositories.ui.users

import com.zinoview.githubrepositories.R
import com.zinoview.githubrepositories.core.Abstract
import com.zinoview.githubrepositories.core.Resource
import java.lang.IllegalArgumentException

interface CollapseOrExpandStateFactory : Abstract.FactoryMapper<Int,CollapseOrExpandState> {

    class Base(
        private val resource: Resource
    ) : CollapseOrExpandStateFactory {

        override fun map(src: Int): CollapseOrExpandState = when(src) {
            R.id.action_filter_by_collapse_state -> CollapseOrExpandState.Collapsed(resource)
            R.id.action_filter_by_expand_state -> CollapseOrExpandState.Expanded(resource)
            R.id.action_filter_any -> CollapseOrExpandState.Any(resource)
            else -> throw IllegalArgumentException("Item id $src not found")
        }
    }
}

