package com.zinoview.githubrepositories.domain.core

import io.reactivex.Single


/**
 * @author Zinoview on 24.08.2021
 * k.gig@list.ru
 */
interface GithubInteractor<T> {

    fun data(query: String) : Single<T>
}