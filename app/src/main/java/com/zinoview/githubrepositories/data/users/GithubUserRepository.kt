package com.zinoview.githubrepositories.data.users

import com.zinoview.githubrepositories.core.Abstract
import com.zinoview.githubrepositories.core.SaveState
import com.zinoview.githubrepositories.data.core.DataByNotFoundState
import com.zinoview.githubrepositories.data.repositories.DataByStateNotFoundException
import com.zinoview.githubrepositories.data.users.cache.CacheGithubUser
import com.zinoview.githubrepositories.data.users.cache.GithubUserCacheDataSource
import com.zinoview.githubrepositories.data.users.cache.prefs.UserCachedState
import com.zinoview.githubrepositories.data.users.cloud.CloudGithubUser
import com.zinoview.githubrepositories.data.users.cloud.GithubUserCloudDataSource
import com.zinoview.githubrepositories.ui.users.CollapseOrExpandState
import io.reactivex.Single


/**
 * @author Zinoview on 19.08.2021
 * k.gig@list.ru
 */

interface GithubUserRepository<T,E> : SaveState,DataByNotFoundState<DataGithubUser> {

    fun user(query: String): T

    fun users() : E

    class Base(
        private val githubUserCloudDataSource: GithubUserCloudDataSource<Single<CloudGithubUser>>,
        private val githubUserCacheDataSource: GithubUserCacheDataSource,
        private val dataGithubUserMapper: Abstract.UserMapper<DataGithubUser>,
        private val cacheGithubUserMapper: Abstract.UserMapper<CacheGithubUser>,
        private val userCachedState: UserCachedState
    ) : GithubUserRepository<Single<DataGithubUser>,Single<List<DataGithubUser>>> {

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

    class Test(
        private val testCloudDataSourceUser: GithubUserCloudDataSource<GithubUserCloudDataSource.Test.TestDataGithubUser>
    ) : GithubUserRepository<GithubUserCloudDataSource.Test.TestDataGithubUser,List<GithubUserCloudDataSource.Test.TestDataGithubUser>> {

        override fun user(query: String): GithubUserCloudDataSource.Test.TestDataGithubUser
            = testCloudDataSourceUser.fetchData(query)

        override fun users(): List<GithubUserCloudDataSource.Test.TestDataGithubUser>
            = listOf(
                GithubUserCloudDataSource.Test.TestDataGithubUser("Bib","This is short Bib bio"),
                GithubUserCloudDataSource.Test.TestDataGithubUser("Lob","This is short Lob bio"),
                GithubUserCloudDataSource.Test.TestDataGithubUser("Tip","This is short Tip bio")
            )

        override fun saveState(state: CollapseOrExpandState)
                = throw IllegalStateException("TestGithubUserRepository not use saveData() method")

        override fun dataByNotFoundState(): Single<List<DataGithubUser>>
                = throw IllegalStateException("TestGithubUserRepository not use saveData() method")
    }


}