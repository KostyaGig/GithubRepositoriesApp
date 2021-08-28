package com.zinoview.githubrepositories.ui.users

import com.zinoview.githubrepositories.core.GithubDisposableStore
import io.reactivex.disposables.Disposable


/**
 * @author Zinoview on 21.08.2021
 * k.gig@list.ru
 */

interface CleanDisposable {

    fun Disposable.addToDisposableStore(store: GithubDisposableStore)
}