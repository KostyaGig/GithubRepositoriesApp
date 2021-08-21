package com.zinoview.githubrepositories.ui.users

import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable


/**
 * @author Zinoview on 19.08.2021
 * k.gig@list.ru
 */
interface GithubDisposableStore {

    fun add(disposable: Disposable)
    fun dispose()

    class Base(
        private val compositeDisposable: CompositeDisposable
    ): GithubDisposableStore {

        override fun add(disposable: Disposable) {
            compositeDisposable.add(disposable)
        }

        override fun dispose() = compositeDisposable.dispose()
    }
}