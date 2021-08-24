package com.zinoview.githubrepositories.data.repositories

import com.zinoview.githubrepositories.core.Abstract
import com.zinoview.githubrepositories.data.repositories.cache.CacheGithubRepositoryMapper
import com.zinoview.githubrepositories.data.repositories.cache.GithubRepositoryCacheDataSource
import com.zinoview.githubrepositories.data.repositories.cloud.GithubRepositoryCloudDataSource
import com.zinoview.githubrepositories.ui.core.message
import io.reactivex.Single


/**
 * @author Zinoview on 19.08.2021
 * k.gig@list.ru
 */
interface GithubRepositoryRepository {

    fun repository(userName: String, repo: String): Single<DataGithubRepository>

    fun repositories(userName: String): Single<List<DataGithubRepository>>

    class Base(
        private val githubRepositoryCacheDataSource: GithubRepositoryCacheDataSource,
        private val githubRepositoryCloudDataSource: GithubRepositoryCloudDataSource,
        private val cacheGithubRepositoryMapper: CacheGithubRepositoryMapper,
        private val dataGithubRepositoryMapper: Abstract.RepositoryMapper<DataGithubRepository>
    ) : GithubRepositoryRepository {

        override fun repository(userName: String, repo: String): Single<DataGithubRepository> {
            message("Repository repository")
            return githubRepositoryCacheDataSource.fetchRepository(userName, repo)
                .flatMap { cacheGithubRepository ->
                    message("repository repository cache repo by name")
                    Single.just(cacheGithubRepository.map(dataGithubRepositoryMapper))
                }.onErrorResumeNext {
                    message("repository repository null repo by name")
                val cloudGithubRepository =
                    githubRepositoryCloudDataSource.repository(userName, repo)
                cloudGithubRepository.flatMap { cloudRepo ->
                    val cacheRepo = cloudRepo.map(cacheGithubRepositoryMapper,userName)
                    githubRepositoryCacheDataSource.saveData(cacheRepo)
                    Single.just(cloudRepo.map(dataGithubRepositoryMapper))
                }
            }
        }

        override fun repositories(
            userName: String
        ): Single<List<DataGithubRepository>> {
            return githubRepositoryCacheDataSource.fetchData(userName)
                .flatMap { cacheGithubRepositories ->
                    if (cacheGithubRepositories.isEmpty()) {
                        val cloudGithubRepositories =
                            githubRepositoryCloudDataSource.fetchData(userName)
                        cloudGithubRepositories.flatMap { cloudRepos ->
                            val cacheRepos = cloudRepos.map {
                                cloudRepo -> cloudRepo.map(cacheGithubRepositoryMapper,userName)
                            }
                            githubRepositoryCacheDataSource.saveListData(cacheRepos)
                            Single.just(cloudRepos.map { it.map(dataGithubRepositoryMapper) })
                        }
                    } else {
                        val dataGithubRepositories =
                            cacheGithubRepositories.map { it.map(dataGithubRepositoryMapper) }
                        Single.just(dataGithubRepositories)
                    }
                }
        }
    }
}