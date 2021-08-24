package com.zinoview.githubrepositories.data.repositories.cache

import com.zinoview.githubrepositories.data.core.GithubDao
import io.reactivex.Single


/**
 * @author Zinoview on 23.08.2021
 * k.gig@list.ru
 */

interface  GithubRepositoryCacheDataSource
    : CacheRepositoryDataSource<List<CacheGithubRepository>,CacheGithubRepository> {

    fun fetchRepository(param: String,repo: String): Single<CacheGithubRepository?>

    class Base (
        private val githubDao: GithubDao
    ) : GithubRepositoryCacheDataSource {

        override fun fetchData(param: String): Single<List<CacheGithubRepository>>
            = githubDao.repositories(param)

        override fun fetchRepository(param: String,repo: String): Single<CacheGithubRepository?>
            = githubDao.repository(param,repo)

        override fun saveListData(listData: List<CacheGithubRepository>)
            = githubDao.insertRepositories(listData)

        override fun saveData(data: CacheGithubRepository)
            = githubDao.insertRepository(data)
    }
}