package com.zinoview.githubrepositories.ui.core


/**
 * @author Zinoview on 25.08.2021
 * k.gig@list.ru
 */

interface Listener<T> {

    fun notify(listener: T)
}