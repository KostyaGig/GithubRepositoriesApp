package com.zinoview.githubrepositories.domain.repositories

import com.zinoview.githubrepositories.core.Abstract
import com.zinoview.githubrepositories.core.SaveState
import com.zinoview.githubrepositories.data.repositories.GithubRepoRepository
import com.zinoview.githubrepositories.data.repositories.download.DownloadRepoRepository
import com.zinoview.githubrepositories.domain.core.GithubInteractor
import com.zinoview.githubrepositories.domain.repositories.download.DomainDownloadFile
import com.zinoview.githubrepositories.domain.repositories.download.DomainDownloadRepositoryMapper
import com.zinoview.githubrepositories.ui.users.CollapseOrExpandState
import io.reactivex.Single


/**
 * @author Zinoview on 19.08.2021
 * k.gig@list.ru
 */
interface GithubRepositoryInteractor : GithubInteractor<List<DomainGithubRepository>>,
    SaveState {

    fun repository(owner: String, repo: String) : Single<DomainGithubRepository>

    fun downloadRepository(owner: String, repo: String) : Single<DomainDownloadFile>

    class Base(
        private val githubRepositoryRepository: GithubRepoRepository,
        private val domainGithubRepositoryMapper: Abstract.RepositoryMapper<DomainGithubRepository>,
        private val downloadRepoRepository: DownloadRepoRepository,
        private val domainDownloadRepositoryMapper: DomainDownloadRepositoryMapper
    ) : GithubRepositoryInteractor {

        override fun data(owner: String): Single<List<DomainGithubRepository>>
            = githubRepositoryRepository.repositories(owner).flatMap { dataGithubRepositories ->
                Single.just(dataGithubRepositories.map { it.map(domainGithubRepositoryMapper) })
            }

        override fun repository(owner: String, repo: String) : Single<DomainGithubRepository>
            = githubRepositoryRepository.repository(owner, repo).flatMap { dataGithubRepository ->
                Single.just(dataGithubRepository.map(domainGithubRepositoryMapper))
        }

        override fun downloadRepository(
            owner: String,
            repo: String
        ): Single<DomainDownloadFile>
            = downloadRepoRepository.download(owner,repo).flatMap { dataDownloadRepo ->
                Single.just(dataDownloadRepo.map(domainDownloadRepositoryMapper))
        }

        override fun saveState(state: CollapseOrExpandState)
            = githubRepositoryRepository.saveState(state)
    }
}