package com.zinoview.githubrepositories.ui.core

import com.zinoview.githubrepositories.core.DisposableStore
import io.reactivex.disposables.Disposable


/**
 * @author Zinoview on 21.08.2021
 * k.gig@list.ru
 */

interface CleanDisposable {

    fun Disposable.addToDisposableStore(store: DisposableStore)
}