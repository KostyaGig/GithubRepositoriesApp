package com.zinoview.githubrepositories.data.core

import io.reactivex.Single


/**
 * @author Zinoview on 20.08.2021
 * k.gig@list.ru
 */
interface GithubDataSource<T,D> : Save<D> {

    fun fetchData(param: String) : Single<T>

}