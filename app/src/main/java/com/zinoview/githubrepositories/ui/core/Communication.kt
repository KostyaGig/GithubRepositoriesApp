package com.zinoview.githubrepositories.ui.core

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer


/**
 * @author Zinoview on 23.08.2021
 * k.gig@list.ru
 */
interface Communication<T> {

    fun observe(owner: LifecycleOwner, observer: Observer<T>)
    fun changeValue(value: T)

    abstract class Base<T : CommunicationModel> : Communication<List<T>> {
        private val liveData = MutableLiveData<List<T>>()

        override fun observe(owner: LifecycleOwner, observer: Observer<List<T>>) {
            liveData.observe(owner,observer)
        }

        override fun changeValue(value: List<T>) {
            liveData.value = value
        }
    }

}