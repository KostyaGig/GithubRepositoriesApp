package com.zinoview.githubrepositories.domain.repositories

import com.zinoview.githubrepositories.core.Abstract
import com.zinoview.githubrepositories.core.Save
import com.zinoview.githubrepositories.core.SaveState
import com.zinoview.githubrepositories.data.repositories.GithubRepositoryRepository
import com.zinoview.githubrepositories.domain.core.GithubInteractor
import com.zinoview.githubrepositories.ui.users.CollapseOrExpandState
import io.reactivex.Single


/**
 * @author Zinoview on 19.08.2021
 * k.gig@list.ru
 */
interface GithubRepositoryInteractor : GithubInteractor<List<DomainGithubRepository>>,
    SaveState {

    fun repository(owner: String, repo: String) : Single<DomainGithubRepository>

    class Base(
        private val githubRepositoryRepository: GithubRepositoryRepository,
        private val domainGithubRepositoryMapper: Abstract.RepositoryMapper<DomainGithubRepository>
    ) : GithubRepositoryInteractor {

        override fun data(owner: String): Single<List<DomainGithubRepository>>
            = githubRepositoryRepository.repositories(owner).flatMap { dataGithubRepositories ->
                Single.just(dataGithubRepositories.map { it.map(domainGithubRepositoryMapper) })
            }

        override fun repository(owner: String, repo: String) : Single<DomainGithubRepository>
            = githubRepositoryRepository.repository(owner, repo).flatMap { dataGithubRepository ->
                Single.just(dataGithubRepository.map(domainGithubRepositoryMapper))
        }

        override fun saveState(state: CollapseOrExpandState)
            = githubRepositoryRepository.saveState(state)
    }
}