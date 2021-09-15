package com.zinoview.githubrepositories.data.core

import com.zinoview.githubrepositories.core.Save
import io.reactivex.Single


/**
 * @author Zinoview on 20.08.2021
 * k.gig@list.ru
 */
interface MutableGithubDataSource<T,D> : Save<D> {

    fun fetchData(param: String) : Single<T>

}