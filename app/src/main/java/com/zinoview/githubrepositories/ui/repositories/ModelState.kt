package com.zinoview.githubrepositories.ui.repositories


/**
 * @author Zinoview on 06.09.2021
 * k.gig@list.ru
 */
interface ModelState<T> {

    fun modelWithChangedState() : T
}