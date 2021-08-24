package com.zinoview.githubrepositories.ui.core


import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel


/**
 * @author Zinoview on 23.08.2021
 * k.gig@list.ru
 */
abstract class BaseViewModel<T : CommunicationModel> (
    private val communication: Communication.Base<T>
) : ViewModel(), Observe<T> {

    override fun observe(owner: LifecycleOwner, observer: Observer<List<T>>)
        = communication.observe(owner, observer)
}