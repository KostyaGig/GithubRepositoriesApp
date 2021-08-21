package com.zinoview.githubrepositories.data.users.cache

import com.zinoview.githubrepositories.data.users.GithubDataSource
import io.reactivex.Single


/**
 * @author Zinoview on 20.08.2021
 * k.gig@list.ru
 */
interface GithubCacheDataSource : GithubDataSource<CacheGithubUser?> {

    fun fetchUsers() : Single<List<CacheGithubUser>>

    fun saveUser(cacheGithubUser: CacheGithubUser)

    class Base(
        private val githubUsersDao: GithubUsersDao
    ) : GithubCacheDataSource {

        override fun fetchUsers(): Single<List<CacheGithubUser>>
            = githubUsersDao.users()

        override fun saveUser(cacheGithubUser: CacheGithubUser)
            = githubUsersDao.insertUser(cacheGithubUser)

        override fun fetchData(param: String): CacheGithubUser?
            = githubUsersDao.user(param)
    }
}