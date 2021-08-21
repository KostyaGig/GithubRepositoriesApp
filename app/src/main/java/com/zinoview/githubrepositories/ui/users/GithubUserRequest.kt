package com.zinoview.githubrepositories.ui.users

import io.reactivex.disposables.Disposable


/**
 * @author Zinoview on 20.08.2021
 * k.gig@list.ru
 */
interface GithubUserRequest<T> {

    fun request(param: T)
}