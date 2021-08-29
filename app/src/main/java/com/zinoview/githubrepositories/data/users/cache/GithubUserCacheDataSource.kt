package com.zinoview.githubrepositories.data.users.cache

import com.zinoview.githubrepositories.data.core.GithubDao
import com.zinoview.githubrepositories.data.core.GithubDataSource
import com.zinoview.githubrepositories.ui.users.CollapseOrExpandState
import io.reactivex.Single


/**
 * @author Zinoview on 20.08.2021
 * k.gig@list.ru
 */
interface GithubUserCacheDataSource : GithubDataSource<CacheGithubUser?,CacheGithubUser> {

    fun fetchUsers() : Single<List<CacheGithubUser>>

    //todo move this method and repo method to interface
    fun usersByState(state: CollapseOrExpandState) : Single<List<CacheGithubUser>>

    class Base(
        private val githubDao: GithubDao
    ) : GithubUserCacheDataSource {

        override fun fetchUsers(): Single<List<CacheGithubUser>>
            = githubDao.users()

        override fun usersByState(state: CollapseOrExpandState): Single<List<CacheGithubUser>>
            = githubDao.usersByState(state)

        override fun saveData(data: CacheGithubUser)
            = githubDao.insertUser(data)

        override fun fetchData(param: String): Single<CacheGithubUser?>
            = githubDao.user(param)
    }
}