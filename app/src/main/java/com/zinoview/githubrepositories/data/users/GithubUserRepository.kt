package com.zinoview.githubrepositories.data.users

import com.zinoview.githubrepositories.core.Abstract
import com.zinoview.githubrepositories.core.SaveState
import com.zinoview.githubrepositories.data.core.DataByNotFoundState
import com.zinoview.githubrepositories.data.repositories.DataByStateNotFoundException
import com.zinoview.githubrepositories.data.users.cache.CacheGithubUser
import com.zinoview.githubrepositories.data.users.cache.GithubUserCacheDataSource
import com.zinoview.githubrepositories.data.users.cache.prefs.UserCachedState
import com.zinoview.githubrepositories.data.users.cloud.GithubUserCloudDataSource
import com.zinoview.githubrepositories.ui.users.CollapseOrExpandState
import io.reactivex.Single


/**
 * @author Zinoview on 19.08.2021
 * k.gig@list.ru
 */

interface GithubUserRepository : SaveState,DataByNotFoundState<DataGithubUser> {

    fun user(query: String): Single<DataGithubUser>

    fun users() : Single<List<DataGithubUser>>

    class Base(
        private val githubUserCloudDataSource: GithubUserCloudDataSource,
        private val githubUserCacheDataSource: GithubUserCacheDataSource,
        private val dataGithubUserMapper: Abstract.UserMapper<DataGithubUser>,
        private val cacheGithubUserMapper: Abstract.UserMapper<CacheGithubUser>,
        private val userCachedState: UserCachedState
    ) : GithubUserRepository {

        override fun user(query: String): Single<DataGithubUser> {
           val userByQuery = githubUserCacheDataSource.commonUser(query)
            return userCachedState.user(query,githubUserCacheDataSource)
               .flatMap {cacheGithubUser ->
                    Single.just(cacheGithubUser.map(dataGithubUserMapper))
                }.onErrorResumeNext {
                    //if user by query and state not found, but found by query: throw DataByStateNotFoundException()
                    if (userByQuery != null) {
                        dataByNotFoundState()
                    }
                    userByQueryFromCloud(query)
                }
        }

        private fun userByQueryFromCloud(query: String) : Single<DataGithubUser> {
            return try {
                githubUserCloudDataSource.fetchData(query)
                    .flatMap { cloudGithubUser -> githubUserCacheDataSource.saveData( cloudGithubUser.map(cacheGithubUserMapper) )
                        Single.just(cloudGithubUser.map(dataGithubUserMapper))
                    }
            } catch (e: Exception) {
                throw e
            }
        }

        override fun users(): Single<List<DataGithubUser>>
            = userCachedState.users(githubUserCacheDataSource).flatMap { cacheGithubUsers ->
                Single.just(cacheGithubUsers.map { it.map(dataGithubUserMapper) })
        }


        override fun dataByNotFoundState(): Single<List<DataGithubUser>>
            = throw DataByStateNotFoundException()

        override fun saveState(state: CollapseOrExpandState)
            = userCachedState.saveState(state)

    }
}