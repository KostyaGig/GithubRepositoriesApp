package com.zinoview.githubrepositories.data.core.prefs

import com.zinoview.githubrepositories.core.Abstract
import com.zinoview.githubrepositories.ui.users.CollapseOrExpandState


/**
 * @author Zinoview on 29.08.2021
 * k.gig@list.ru
 */
class SavedValueStateFactory : Abstract.FactoryMapper<CollapseOrExpandState,String>,
com.zinoview.githubrepositories.data.core.prefs.AbstractStateFactory() {

    override fun map(src: CollapseOrExpandState): String = when(src) {
        is CollapseOrExpandState.Collapsed -> COLLAPSED
        is CollapseOrExpandState.Expanded -> EXPANDED
        else -> ANY
    }
}