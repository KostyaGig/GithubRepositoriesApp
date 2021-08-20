package com.zinoview.githubrepositories.ui.users

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import com.zinoview.githubrepositories.core.Abstract
import com.zinoview.githubrepositories.domain.users.GithubUserInteractor
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers


/**
 * @author Zinoview on 19.08.2021
 * k.gig@list.ru
 */
interface GithubUserViewModel {

    fun user(query: String)

    fun observe(owner: LifecycleOwner,observer: Observer<List<UiGithubUserState>>)

    class Base(
        private val githubUserRequest: GithubUserRequest,
        private val githubUserDisposableStore: GithubUserDisposableStore,
        private val communication: GithubUserCommunication
    ) : ViewModel(), GithubUserViewModel {

        override fun user(query: String)
            = githubUserRequest.request(query)

        override fun observe(owner: LifecycleOwner, observer: Observer<List<UiGithubUserState>>) {
            communication.observe(owner,observer)
        }

        override fun onCleared() {
            githubUserDisposableStore.dispose()
            super.onCleared()
        }
    }

}