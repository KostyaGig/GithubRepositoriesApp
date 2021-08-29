package com.zinoview.githubrepositories.data.repositories

import com.zinoview.githubrepositories.core.Abstract
import com.zinoview.githubrepositories.data.repositories.cache.CacheGithubRepository
import com.zinoview.githubrepositories.data.repositories.cache.CacheGithubRepositoryMapper
import com.zinoview.githubrepositories.data.repositories.cache.GithubRepositoryCacheDataSource
import com.zinoview.githubrepositories.data.repositories.cloud.GithubRepositoryCloudDataSource
import com.zinoview.githubrepositories.ui.users.CollapseOrExpandState
import io.reactivex.Single


/**
 * @author Zinoview on 19.08.2021
 * k.gig@list.ru
 */
interface GithubRepositoryRepository {

    fun repository(userName: String, repo: String): Single<DataGithubRepository>

    fun repositories(userName: String): Single<List<DataGithubRepository>>

    fun repositoriesByState(owner: String,state: CollapseOrExpandState) : Single<List<DataGithubRepository>>

    class Base(
        private val githubRepositoryCacheDataSource: GithubRepositoryCacheDataSource,
        private val githubRepositoryCloudDataSource: GithubRepositoryCloudDataSource,
        private val cacheGithubRepositoryMapper: CacheGithubRepositoryMapper,
        private val dataGithubRepositoryMapper: Abstract.RepositoryMapper<DataGithubRepository>
    ) : GithubRepositoryRepository {

        override fun repository(owner: String, repo: String): Single<DataGithubRepository> {
            return githubRepositoryCacheDataSource.fetchRepository(owner, repo)
                .flatMap { cacheGithubRepository ->
                    Single.just(cacheGithubRepository.map(dataGithubRepositoryMapper))
                }.onErrorResumeNext {
                val cloudGithubRepository =
                    githubRepositoryCloudDataSource.repository(owner, repo)
                cloudGithubRepository.flatMap { cloudRepo ->
                    val cacheRepo = cloudRepo.map(cacheGithubRepositoryMapper,owner)
                    githubRepositoryCacheDataSource.saveData(cacheRepo)
                    Single.just(cloudRepo.map(dataGithubRepositoryMapper,owner))
                }
            }
        }

        override fun repositories(
            owner: String
        ): Single<List<DataGithubRepository>> {
            return githubRepositoryCacheDataSource.fetchData(owner)
                .flatMap { cacheGithubRepositories ->
                    if (cacheGithubRepositories.isEmpty()) {
                        val cloudGithubRepositories =
                            githubRepositoryCloudDataSource.fetchData(owner)
                        cloudGithubRepositories.flatMap { cloudRepos ->
                            val cacheRepos = cloudRepos.map {
                                cloudRepo -> cloudRepo.map(cacheGithubRepositoryMapper,owner)
                            }
                            githubRepositoryCacheDataSource.saveListData(cacheRepos)
                            Single.just(cloudRepos.map { it.map(dataGithubRepositoryMapper,owner) })
                        }
                    } else {
                        val dataGithubRepositories =
                            cacheGithubRepositories.map { it.map(dataGithubRepositoryMapper) }
                        Single.just(dataGithubRepositories)
                    }
                }
        }

        override fun repositoriesByState(
            owner: String,
            state: CollapseOrExpandState
        ): Single<List<DataGithubRepository>>
            = githubRepositoryCacheDataSource
            .repositoriesByState(owner, state).flatMap { cacheRepos ->
                Single.just(cacheRepos.map { it.map(dataGithubRepositoryMapper) })
        }
    }
}