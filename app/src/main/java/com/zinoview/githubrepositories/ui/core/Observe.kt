package com.zinoview.githubrepositories.ui.core

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer


/**
 * @author Zinoview on 23.08.2021
 * k.gig@list.ru
 */
interface Observe<T : CommunicationModel> {

    fun observe(owner: LifecycleOwner,observer: Observer<List<T>>)
}