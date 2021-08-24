package com.zinoview.githubrepositories.data.users

import com.zinoview.githubrepositories.core.Abstract
import com.zinoview.githubrepositories.data.users.cache.CacheGithubUser
import com.zinoview.githubrepositories.data.users.cache.GithubUserCacheDataSource
import com.zinoview.githubrepositories.data.users.cloud.GithubUserCloudDataSource
import com.zinoview.githubrepositories.ui.core.message
import io.reactivex.Single


/**
 * @author Zinoview on 19.08.2021
 * k.gig@list.ru
 */
interface GithubUserRepository {

    fun user(query: String): Single<DataGithubUser>

    fun users() : Single<List<DataGithubUser>>

    class Base(
        private val githubUserCloudDataSource: GithubUserCloudDataSource,
        private val githubUserCacheDataSource: GithubUserCacheDataSource,
        private val dataGithubUserMapper: Abstract.UserMapper<DataGithubUser>,
        private val cacheGithubUserMapper: Abstract.UserMapper<CacheGithubUser>
    ) : GithubUserRepository {

        override fun user(query: String): Single<DataGithubUser> {
           return githubUserCacheDataSource.fetchData(query).flatMap {cacheGithubUser ->
               Single.just(cacheGithubUser.map(dataGithubUserMapper))
            }.onErrorResumeNext {
               message("onErrorResumeNext current thread ${Thread.currentThread().name}")
               githubUserCloudDataSource.fetchData(query).flatMap { cloudGithubUser ->
                   githubUserCacheDataSource.saveData( cloudGithubUser.map(cacheGithubUserMapper) )
                   Single.just(cloudGithubUser.map(dataGithubUserMapper))
               }
           }
        }

        override fun users(): Single<List<DataGithubUser>>
            = githubUserCacheDataSource.fetchUsers().flatMap { cacheGithubUsers ->
                Single.just(cacheGithubUsers.map { it.map(dataGithubUserMapper) })
        }
    }
}