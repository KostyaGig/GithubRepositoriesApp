package com.zinoview.githubrepositories.data.core.prefs

import com.zinoview.githubrepositories.core.Abstract
import com.zinoview.githubrepositories.core.Resource
import com.zinoview.githubrepositories.ui.users.CollapseOrExpandState


/**
 * @author Zinoview on 29.08.2021
 * k.gig@list.ru
 */
class CollapseOrExpandStateFactory(
    private val resource: Resource
) : Abstract.FactoryMapper<String,CollapseOrExpandState>,
    com.zinoview.githubrepositories.data.core.prefs.AbstractStateFactory() {

    override fun map(src: String): CollapseOrExpandState = when(src) {
        COLLAPSED -> CollapseOrExpandState.Collapsed(resource)
        EXPANDED -> CollapseOrExpandState.Expanded(resource)
        else -> CollapseOrExpandState.Any(resource)
    }

}