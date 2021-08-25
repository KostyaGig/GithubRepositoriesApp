package com.zinoview.githubrepositories.ui.core


import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import com.zinoview.githubrepositories.ui.users.GithubDisposableStore
import com.zinoview.githubrepositories.ui.users.GithubUserRequest


/**
 * @author Zinoview on 23.08.2021
 * k.gig@list.ru
 */
abstract class BaseViewModel<T : CommunicationModel> (
    private val communication: Communication.Base<T>,
    private val disposableStore: GithubDisposableStore,
    private val githubUserRequest: GithubUserRequest<String>
) : ViewModel(), Observe<T>, GithubUserRequest<String> {

    override fun observe(owner: LifecycleOwner, observer: Observer<List<T>>)
        = communication.observe(owner, observer)

    override fun onCleared() {
        disposableStore.dispose()
        super.onCleared()
    }

    override fun data(param: String)
        = githubUserRequest.data(param)
}