package com.zinoview.githubrepositories.ui.users

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer


/**
 * @author Zinoview on 19.08.2021
 * k.gig@list.ru
 */
interface GithubUserCommunication {

    fun observe(owner: LifecycleOwner,observer: Observer<List<UiGithubUserState>>)
    fun changeValue(value: List<UiGithubUserState>)

    class Base : GithubUserCommunication {
        private val githubUserLiveData = MutableLiveData<List<UiGithubUserState>>()

        override fun observe(owner: LifecycleOwner, observer: Observer<List<UiGithubUserState>>) {
            githubUserLiveData.observe(owner,observer)
        }

        override fun changeValue(value: List<UiGithubUserState>) {
            githubUserLiveData.value = value
        }
    }
}