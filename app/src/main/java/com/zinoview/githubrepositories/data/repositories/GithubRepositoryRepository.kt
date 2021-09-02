package com.zinoview.githubrepositories.data.repositories

import com.zinoview.githubrepositories.core.Abstract
import com.zinoview.githubrepositories.core.SaveState
import com.zinoview.githubrepositories.data.core.DataByNotFoundState
import com.zinoview.githubrepositories.data.repositories.cache.CacheGithubRepositoryMapper
import com.zinoview.githubrepositories.data.repositories.cache.GithubRepositoryCacheDataSource
import com.zinoview.githubrepositories.data.repositories.cache.prefs.RepositoryCachedState
import com.zinoview.githubrepositories.data.repositories.cloud.GithubRepositoryCloudDataSource
import com.zinoview.githubrepositories.ui.users.CollapseOrExpandState
import io.reactivex.Single


/**
 * @author Zinoview on 19.08.2021
 * k.gig@list.ru
 */
interface GithubRepositoryRepository : SaveState,
    DataByNotFoundState<DataGithubRepository> {

    fun repository(userName: String, repo: String): Single<DataGithubRepository>

    fun repositories(userName: String): Single<List<DataGithubRepository>>

    class Base(
        private val githubRepositoryCacheDataSource: GithubRepositoryCacheDataSource,
        private val githubRepositoryCloudDataSource: GithubRepositoryCloudDataSource,
        private val cacheGithubRepositoryMapper: CacheGithubRepositoryMapper,
        private val dataGithubRepositoryMapper: Abstract.RepositoryMapper<DataGithubRepository>,
        private val repositoryCachedState: RepositoryCachedState
    ) : GithubRepositoryRepository {

        override fun repository(owner: String, repo: String): Single<DataGithubRepository> {
            val commonRepository = githubRepositoryCacheDataSource.commonRepository(owner,repo)
            return repositoryCachedState.repository(owner, repo,githubRepositoryCacheDataSource)
                .flatMap { cacheGithubRepository ->
                    Single.just(cacheGithubRepository.map(dataGithubRepositoryMapper))
                }.onErrorResumeNext {
                    if (commonRepository != null) {
                        dataByNotFoundState()
                    }
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
            val allCachedRepositories = githubRepositoryCacheDataSource.commonListRepository(owner)
            return repositoryCachedState.repositories(owner,githubRepositoryCacheDataSource)
                .flatMap { cacheGithubRepositories ->
                    if (cacheGithubRepositories.isEmpty()) {
                        if (allCachedRepositories.isEmpty()) {
                            cloudRepositoriesByOwner(owner)
                        } else {
                            dataByNotFoundState()
                        }
                    } else {
                        val dataGithubRepositories =
                            cacheGithubRepositories.map { it.map(dataGithubRepositoryMapper) }
                        Single.just(dataGithubRepositories)
                    }
                }
        }

        private fun cloudRepositoriesByOwner(owner: String) : Single<List<DataGithubRepository>> {
            val cloudGithubRepositories =
                githubRepositoryCloudDataSource.fetchData(owner)
            return cloudGithubRepositories.flatMap { cloudRepos ->
                val cacheRepos = cloudRepos.map {
                        cloudRepo -> cloudRepo.map(cacheGithubRepositoryMapper,owner)
                }
                githubRepositoryCacheDataSource.saveListData(cacheRepos)
                Single.just(cloudRepos.map { it.map(dataGithubRepositoryMapper,owner) })
            }
        }

        override fun dataByNotFoundState(): Single<List<DataGithubRepository>>
            = throw DataByStateNotFoundException()

        override fun saveState(state: CollapseOrExpandState)
            = repositoryCachedState.saveState(state)
    }
}