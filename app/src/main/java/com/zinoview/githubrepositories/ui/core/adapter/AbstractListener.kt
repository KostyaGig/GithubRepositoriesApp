package com.zinoview.githubrepositories.ui.core.adapter



/**
 * @author Zinoview on 25.08.2021
 * k.gig@list.ru
 */

interface AbstractListener<T> {

    fun notifyAboutItemClick(listener: T)

    interface CollapseOrExpandListener<T,C> : AbstractListener<T> {
       fun notifyAboutCollapseOrExpand(listener: C, position: Int)
    }
}