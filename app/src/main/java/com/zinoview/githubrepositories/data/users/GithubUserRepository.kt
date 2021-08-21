package com.zinoview.githubrepositories.data.users

import android.annotation.SuppressLint
import com.zinoview.githubrepositories.core.Abstract
import com.zinoview.githubrepositories.data.users.cache.CacheGithubUser
import com.zinoview.githubrepositories.data.users.cache.GithubCacheDataSource
import com.zinoview.githubrepositories.data.users.cloud.GithubCloudDataSource
import com.zinoview.githubrepositories.data.users.cloud.GithubService
import com.zinoview.githubrepositories.ui.message
import io.reactivex.Flowable
import io.reactivex.Single
import retrofit2.adapter.rxjava2.HttpException


/**
 * @author Zinoview on 19.08.2021
 * k.gig@list.ru
 */
interface GithubUserRepository {

    fun user(query: String): Single<DataGithubUser>

    fun users() : Single<List<DataGithubUser>>

    class Base(
        private val githubCloudDataSource: GithubCloudDataSource,
        private val githubCacheDataSource: GithubCacheDataSource,
        private val dataGithubUserMapper: Abstract.UserMapper<DataGithubUser>,
        private val cacheGithubUserMapper: Abstract.UserMapper<CacheGithubUser>
    ) : GithubUserRepository {

        override fun user(query: String): Single<DataGithubUser> {
            val cacheGithubUser = githubCacheDataSource.fetchData(query)
            return if (cacheGithubUser != null) {
                message("Return ")
                Single.just(cacheGithubUser.map(dataGithubUserMapper))
            } else {
                githubCloudDataSource.fetchData(query).flatMap { cloudGithubUser ->
                    githubCacheDataSource.saveUser( cloudGithubUser.map(cacheGithubUserMapper) )
                    Single.just(cloudGithubUser.map(dataGithubUserMapper))
                }
            }
        }

        override fun users(): Single<List<DataGithubUser>>
            = githubCacheDataSource.fetchUsers().flatMap { cacheGithubUsers ->
                Single.just(cacheGithubUsers.map { it.map(dataGithubUserMapper) })
        }
    }
}