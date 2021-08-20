package com.zinoview.githubrepositories.ui.users

import com.zinoview.githubrepositories.core.Abstract
import com.zinoview.githubrepositories.domain.users.GithubUserInteractor
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers


/**
 * @author Zinoview on 19.08.2021
 * k.gig@list.ru
 */

interface GithubUserRequest {

    fun request(query: String)

    class Base(
        private val githubUserInteractor: GithubUserInteractor,
        private val communication: GithubUserCommunication,
        private val githubUserDisposableStore: GithubUserDisposableStore,
        private val mapper: Abstract.UserMapper<UiGithubUser>,
        private val exceptionMapper: Abstract.Factory<Throwable,String>
    ) : GithubUserRequest {

        override fun request(query: String) {
            communication.changeValue(UiGithubUserState.Progress.wrapToList())
            githubUserInteractor
                .user(query)
                .subscribeOn(Schedulers.io())
                .flatMap { domainGithubUser ->
                    Single.just(domainGithubUser.map(mapper))
                }
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({ uiGithubUser ->
                    uiGithubUser?.let { user ->
                        communication.changeValue(UiGithubUserState.Base(user).wrapToList())
                    }
                }, { error ->
                    error.let { throwable ->
                        val messageError = exceptionMapper.fetch(throwable)
                        communication.changeValue(UiGithubUserState.Fail(messageError).wrapToList())
                    }
                }).addToDisposableStore(githubUserDisposableStore)
        }

        private fun Disposable.addToDisposableStore(store: GithubUserDisposableStore)
            = store.add(this)
    }
}
