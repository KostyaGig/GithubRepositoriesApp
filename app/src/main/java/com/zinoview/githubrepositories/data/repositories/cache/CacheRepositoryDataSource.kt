package com.zinoview.githubrepositories.data.repositories.cache

import com.zinoview.githubrepositories.data.core.GithubDataSource


/**
 * @author Zinoview on 24.08.2021
 * k.gig@list.ru
 */
interface CacheRepositoryDataSource<T,D> : GithubDataSource<T,D> {

    fun saveListData(listData: List<D>)
}